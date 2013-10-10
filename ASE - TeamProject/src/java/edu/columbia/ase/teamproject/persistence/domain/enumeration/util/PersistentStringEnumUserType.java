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

public abstract class PersistentStringEnumUserType<E> implements UserType {
	 
    public abstract Class<E> returnedClass();
	    
    public abstract  Object fromString(String fromValue);
    
	private static final Logger logger =
			LoggerFactory.getLogger(PersistentStringEnumUserType.class);
	
    private static final int[] SQL_TYPES = {Types.VARCHAR};   
    public int[] sqlTypes() {   
        return SQL_TYPES;   
    }   
   
   
	@Override    
	public Object nullSafeGet(ResultSet resultSet, String[] names,
			SessionImplementor session, Object owner) throws HibernateException, SQLException {   
				
        String name = resultSet.getString(names[0]);   
                
        if(resultSet.wasNull()){
        	/*
        	 * Should never happen, 
        	 * but lets log it if it does. 
        	 */
    		logger.error("result set was NULL!!!!");
        	return null;
        }
                
        Object returnedObject = fromString(name);
                
        if( returnedObject == null ){
        	throw new IllegalStateException("Unable to find value ("+name+") for Enum ("+returnedClass().getSimpleName()+")");     
        }
        
        return returnedObject;
    }   
   
   
	@SuppressWarnings("rawtypes")
	@Override
	public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index,
			SessionImplementor session) throws HibernateException, SQLException {
		
        if (null == value) {   
            preparedStatement.setNull(index, Types.VARCHAR);   
        } else {   
            preparedStatement.setString(index, ((Enum)value).name());   
        }  		
        
	}
    
    @Override
    public Object deepCopy(Object value) throws HibernateException{   
        return value;   
    }   
   
    @Override
    public boolean isMutable() {   
        return false;   
    }   
   
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException{  
         return cached;  
    }   
  
    @Override
    public Serializable disassemble(Object value) throws HibernateException {   
        return (Serializable)value;   
    }   
   
    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {   
        return original;   
    }   
    public int hashCode(Object x) throws HibernateException {   
        return x.hashCode();   
    }   
    
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {   
        if (x == y)   
            return true;   
        if (null == x || null == y)   
            return false;   
        return x.equals(y);   
    }



}
