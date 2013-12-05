package edu.columbia.ase.teamproject.persistence.domain.enumeration;

import edu.columbia.ase.teamproject.security.Permission;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.util.PersistentStringEnumUserType;

// TODO: Auto-generated Javadoc
/**
 * The Class PermissionUserType.
 */
public class PermissionUserType extends PersistentStringEnumUserType<Permission>{
	
	/* (non-Javadoc)
	 * @see edu.columbia.ase.teamproject.persistence.domain.enumeration.util.PersistentStringEnumUserType#returnedClass()
	 */
	@Override
	public Class<Permission> returnedClass() {
		return Permission.class;
	}

	/* (non-Javadoc)
	 * @see edu.columbia.ase.teamproject.persistence.domain.enumeration.util.PersistentStringEnumUserType#fromString(java.lang.String)
	 */
	public Object fromString(String permission){
		return Permission.fromString(permission);
	}
	
}
