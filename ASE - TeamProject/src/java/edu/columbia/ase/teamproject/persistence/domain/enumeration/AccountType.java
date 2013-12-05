package edu.columbia.ase.teamproject.persistence.domain.enumeration;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

// TODO: Auto-generated Javadoc
/**
 * The Enum AccountType.
 */
public enum AccountType {

	/**
	 * User who authenticates using an in-database username/password.
	 */
	LOCAL("LOCAL"),
	/**
	 * User who authenticates using OpenId.
	 */
	OPENID("OPENID");

	/** The Constant MAX_ACCOUNT_TYPE_LENGTH. */
	public static final int MAX_ACCOUNT_TYPE_LENGTH = 64;

	/** The account type. */
	private final String accountType;

	/** The Constant stringToEnum. */
	private static final Map<String, AccountType> stringToEnum;

	static {
		final Map<String, AccountType> tempMap = new HashMap<String, AccountType>();
		for (AccountType accountType : values()) {
			tempMap.put(accountType.getAccountType(), accountType);
		}
		stringToEnum = ImmutableMap.copyOf(tempMap);
	}

	/**
	 * Instantiates a new account type.
	 *
	 * @param accountType
	 *            the account type
	 */
	private AccountType(String accountType) {
		Preconditions
				.checkArgument(accountType.length() < MAX_ACCOUNT_TYPE_LENGTH);
		this.accountType = accountType;
	}

	/**
	 * Gets the account type.
	 *
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return accountType;
	}

	/**
	 * From string.
	 *
	 * @param accountType
	 *            the account type
	 * @return the account type
	 */
	public static AccountType fromString(String accountType) {
		return stringToEnum.get(accountType);
	}

};
