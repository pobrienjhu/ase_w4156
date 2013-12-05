package edu.columbia.ase.teamproject.persistence.dao.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class InputValidatorTruncator.
 */
public class InputValidatorTruncator {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(InputValidatorTruncator.class);

    /**
     * Truncate.
     * 
     * @param <T>
     *            the generic type
     * @param object
     *            the object
     * @return the t
     * @throws Exception
     *             the exception
     */
    public <T> T truncate(T object) throws Exception {
        Class<?> aClass = object.getClass();
        while (aClass != null) {
            handleFieldsForClass(object, aClass);
            aClass = aClass.getSuperclass();
        }
        return object;
    }

    /**
     * Handle fields for class.
     * 
     * @param object
     *            the object
     * @param aClass
     *            the a class
     * @throws Exception
     *             the exception
     */
    private void handleFieldsForClass(Object object, Class<?> aClass) throws Exception {

        if (object == null || aClass == null) {
            logger.error(" (" + (object != null ? object.toString() : "null") + ",class="
                    + (aClass != null ? aClass.getSimpleName() : "null") + ") "
                    + "Error with parameters");
            return;
        }

        Field[] declaredFields = aClass.getDeclaredFields();

        for (Field field : declaredFields) {
            boolean isStringField = field.getType().equals(String.class);
            if (isStringField) {
                handleStringField(object, field);
                continue;
            }

            boolean truncateFields = field.isAnnotationPresent(TruncateFields.class);
            if (truncateFields) {
                handleTruncateFields(object, field);
            }
        }
    }

    /*
     * Used if using inheritance in the domain objects
     */
    /**
     * Handle sub object.
     * 
     * @param object
     *            the object
     * @param field
     *            the field
     * @throws Exception
     *             the exception
     */
    @SuppressWarnings("unused")
    private void handleSubObject(Object object, Field field) throws Exception {

        if (object == null || field == null) {
            logger.error(" (" + (object != null ? object.toString() : "null") + ",field="
                    + (field != null ? field.getName() : "null") + ") " + "Error with parameters");
            return;
        }
        Object obj;
        try {
            obj = PropertyUtils.getProperty(object, field.getName());
            if (obj != null) {
                handleFieldsForClass(obj, obj.getClass());
            } else {
                logger.warn(" (" + (object != null ? object.toString() : "null") + ",field="
                        + (field != null ? field.getName() : "null") + ") " + "SubObject "
                        + field.getName() + " was fetched as null");
            }
        } catch (Exception e) {
            logger.error(" (" + (object != null ? object.toString() : "null") + ",field="
                    + (field != null ? field.getName() : "null") + ") " + "Exception caught");
            throw new Exception("Problem truncating field(" + field.getName() + ")", e);
        }
    }

    /**
     * Handle truncate fields.
     * 
     * @param object
     *            the object
     * @param field
     *            the field
     * @throws Exception
     *             the exception
     */
    private void handleTruncateFields(Object object, Field field) throws Exception {
        String fieldName = field.getName();
        try {
            Object fieldValue = PropertyUtils.getProperty(object, fieldName);
            if (fieldValue == null) {
                return;
            }
            if (Collection.class.isInstance(fieldValue)) {
                truncateAllEntities((Collection) fieldValue);
            } else if (Map.class.isInstance(fieldValue)) {
                truncateAllEntities(((Map) fieldValue).values());
            } else if (Dictionary.class.isInstance(fieldValue)) {
                truncateAllEntities(((Dictionary) fieldValue).elements());
            } else {
                throw new Exception(
                        "TruncateFields annotation is only supported for Collections, Maps, and Dictionaries and their subclasses. Field ["
                                + fieldName + "] is a :" + field.getType());
            }
        } catch (Exception e) {

            throw new Exception("Problem truncating field(" + fieldName + ")", e);
        }

    }

    /**
     * Truncate all entities.
     * 
     * @param entities
     *            the entities
     * @throws Exception
     *             the exception
     */
    private void truncateAllEntities(Enumeration entities) throws Exception {
        while (entities.hasMoreElements()) {
            Object entity = entities.nextElement();
            truncate(entity);
        }
    }

    /**
     * Truncate all entities.
     * 
     * @param entities
     *            the entities
     * @throws Exception
     *             the exception
     */
    private void truncateAllEntities(Collection entities) throws Exception {
        for (Object entity : entities) {
            truncate(entity);
        }
    }

    /**
     * Handle string field.
     * 
     * @param object
     *            the object
     * @param field
     *            the field
     * @throws Exception
     *             the exception
     */
    private void handleStringField(Object object, Field field) throws Exception {
        ColumnLength column = field.getAnnotation(ColumnLength.class);
        if (column != null) {
            String fieldName = field.getName();
            try {
                String value = (String) PropertyUtils.getProperty(object, fieldName);
                if (value != null) {
                    int allowedLength = column.value();
                    boolean valueIsTooLong = allowedLength > 0 && value.length() > allowedLength;
                    if (valueIsTooLong) {
                        truncateValue(object, field, value, allowedLength);
                    }
                }
            } catch (Exception e) {
                throw new Exception("Problem truncating field (" + fieldName + ")", e);
            }
        }
    }

    /**
     * Truncate value.
     * 
     * @param object
     *            the object
     * @param field
     *            the field
     * @param value
     *            the value
     * @param allowedLength
     *            the allowed length
     * @throws InvocationTargetException
     *             the invocation target exception
     * @throws NoSuchMethodException
     *             the no such method exception
     * @throws IllegalAccessException
     *             the illegal access exception
     */
    private void truncateValue(Object object, Field field, String value, int allowedLength)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        String fieldName = field.getName();
        // logger.info("The value of the field [" + fieldName +
        // "] before check is [" + value + "]");
        value = value.substring(0, allowedLength);
        PropertyUtils.setSimpleProperty(object, fieldName, value);
        // logger.info("The value of the field [" + fieldName +
        // "] after  check is [" + value + "]");
    }
}
