package edu.columbia.ase.teamproject.persistence.domain.enumeration;

import edu.columbia.ase.teamproject.security.Permission;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.util.PersistentStringEnumUserType;

public class PermissionUserType extends PersistentStringEnumUserType<Permission>{
	
	@Override
	public Class<Permission> returnedClass() {
		return Permission.class;
	}

	public Object fromString(String permission){
		return Permission.fromString(permission);
	}
	
}
