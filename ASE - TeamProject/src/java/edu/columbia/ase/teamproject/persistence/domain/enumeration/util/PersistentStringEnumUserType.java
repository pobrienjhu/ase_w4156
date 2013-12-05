package edu.columbia.ase.teamproject.persistence.domain.enumeration.util;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.UserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class PersistentStringEnumUserType.
 *
 * @param <E>
 *            the element type
 */
public abstract class PersistentStringEnumUserType<E> implements UserType {

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.usertype.UserType#returnedClass()
     */
    public abstract Class<E> returnedClass();

    /**
     * From string.
     *
     * @param fromValue
     *            the from value
     * @return the object
     */
    public abstract Object fromString(String fromValue);

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory
            .getLogger(PersistentStringEnumUserType.class);

    /** The Constant SQL_TYPES. */
    private static final int[] SQL_TYPES = { Types.VARCHAR };

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.usertype.UserType#sqlTypes()
     */
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet,
     * java.lang.String[], org.hibernate.engine.spi.SessionImplementor,
     * java.lang.Object)
     */
    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor session,
            Object owner) throws HibernateException, SQLException {

        String name = resultSet.getString(names[0]);

        if (resultSet.wasNull()) {
            /*
             * Should never happen, but lets log it if it does.
             */
            logger.error("result set was NULL!!!!");
            return null;
        }

        Object returnedObject = fromString(name);

        if (returnedObject == null) {
            throw new IllegalStateException("Unable to find value (" + name + ") for Enum ("
                    + returnedClass().getSimpleName() + ")");
        }

        return returnedObject;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement,
     * java.lang.Object, int, org.hibernate.engine.spi.SessionImplementor)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index,
            SessionImplementor session) throws HibernateException, SQLException {

        if (null == value) {
            preparedStatement.setNull(index, Types.VARCHAR);
        } else {
            preparedStatement.setString(index, ((Enum) value).name());
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
     */
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.usertype.UserType#isMutable()
     */
    @Override
    public boolean isMutable() {
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable,
     * java.lang.Object)
     */
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
     */
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) value;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.usertype.UserType#replace(java.lang.Object,
     * java.lang.Object, java.lang.Object)
     */
    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
     */
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.usertype.UserType#equals(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y)
            return true;
        if (null == x || null == y)
            return false;
        return x.equals(y);
    }

}
