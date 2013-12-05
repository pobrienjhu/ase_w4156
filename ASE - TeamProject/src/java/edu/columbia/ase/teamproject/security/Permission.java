package edu.columbia.ase.teamproject.security;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

// TODO: Auto-generated Javadoc
/**
 * An enumeration of permission levels that users have.
 */
public enum Permission {

    /** The admin. */
    ADMIN("ADMIN"),

    /** The user. */
    USER("USER"),
    // This is kind of a dirty hack. We add the appropriate value as a granted
    // authority during login so we can later look up the correct account
    // using just the Spring UserDetails object.
    /** The openid. */
    OPENID("OPENID"),

    /** The local. */
    LOCAL("LOCAL");

    /** The permission name. */
    private final String permissionName;

    /** The Constant stringToEnum. */
    private static final Map<String, Permission> stringToEnum;

    static {
        final Map<String, Permission> tempMap = new HashMap<String, Permission>();
        for (final Permission permission : values()) {
            tempMap.put(permission.getPermissionName(), permission);
        }
        stringToEnum = ImmutableMap.copyOf(tempMap);
    }

    /**
     * Create a new permission level with the specified {@code roleName}, which
     * must not be empty and may not contain a comma.
     *
     * @param permissionName
     *            the permission name
     */
    private Permission(final String permissionName) {
        Preconditions.checkArgument(permissionName.length() != 0);
        Preconditions.checkArgument(!permissionName.contains(","));
        this.permissionName = permissionName;
    }

    /**
     * Gets the permission name.
     *
     * @return the permission name
     */
    public String getPermissionName() {
        return permissionName;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return permissionName;
    }

    /**
     * From string.
     *
     * @param permission
     *            the permission
     * @return the permission
     */
    public static Permission fromString(final String permission) {
        return stringToEnum.get(permission);
    }

}
