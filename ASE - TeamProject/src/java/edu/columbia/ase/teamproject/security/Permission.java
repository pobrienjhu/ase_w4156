package edu.columbia.ase.teamproject.security;

import com.google.common.base.Preconditions;

/**
 * An enumeration of permission levels that users have.
 */
public enum Permission {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");

	private String roleName;

	/**
	 * Create a new permission level with the specified {@code roleName}, which
	 * must not be empty and may not contain a comma. 
	 */
	Permission(String roleName) {
		Preconditions.checkArgument(roleName.length() != 0);
		Preconditions.checkArgument(!roleName.contains(","));
		this.roleName = roleName;
	}
	
	public String getRoleName() {
		return roleName;
	}

	@Override
	public String toString() {
		return roleName;
	}
}
