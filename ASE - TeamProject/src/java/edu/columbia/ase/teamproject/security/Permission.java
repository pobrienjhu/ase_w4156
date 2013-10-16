package edu.columbia.ase.teamproject.security;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;


/**
 * An enumeration of permission levels that users have.
 */
public enum Permission {
	ADMIN("ADMIN"),
	USER("USER"),
	// This is kind of a dirty hack.  We add the appropriate value as a granted
	// authority during login so we can later look up the correct account
	// using just the Spring UserDetails object.
	OPENID("OPENID"),
	LOCAL("LOCAL");

	private final String permissionName;

	private static final Map<String, Permission> stringToEnum;
	
	static{
		final Map<String, Permission> tempMap = 
				new HashMap<String, Permission>();
		for(Permission permission: values()){
			tempMap.put(permission.getPermissionName(), permission);
		}
		stringToEnum = ImmutableMap.copyOf(tempMap);
	}
	
	/**
	 * Create a new permission level with the specified {@code roleName}, which
	 * must not be empty and may not contain a comma. 
	 */
	private Permission(String permissionName) {
		Preconditions.checkArgument(permissionName.length() != 0);
		Preconditions.checkArgument(!permissionName.contains(","));
		this.permissionName = permissionName;
	}
	
	public String getPermissionName() {
		return permissionName;
	}

	@Override
	public String toString() {
		return permissionName;
	}
	

	public static Permission fromString( String permission ){
		return stringToEnum.get(permission);
	}


	
	
}
