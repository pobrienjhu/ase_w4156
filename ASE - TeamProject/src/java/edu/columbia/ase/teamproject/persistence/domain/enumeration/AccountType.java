package edu.columbia.ase.teamproject.persistence.domain.enumeration;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

public enum AccountType {

	/**
	 * User who authenticates using an in-database username/password.
	 */
	LOCAL("LOCAL"),
	/**
	 * User who authenticates using OpenId.
	 */
	OPENID("OPENID");

	public static final int MAX_ACCOUNT_TYPE_LENGTH = 64;

	private final String accountType;	
	
	private static final Map<String, AccountType> stringToEnum;
	
	static {
		final Map<String, AccountType> tempMap = 
				new HashMap<String, AccountType>();
		for(AccountType accountType: values()){
			tempMap.put(accountType.getAccountType(), accountType);
		}
		stringToEnum = ImmutableMap.copyOf(tempMap);
	}
	
	private AccountType(String accountType) {
		Preconditions.checkArgument(
				accountType.length() < MAX_ACCOUNT_TYPE_LENGTH);
		this.accountType = accountType;
	}

	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	@Override
	public String toString() {
		return accountType;
	}
	
	public static AccountType fromString( String accountType ){
		return stringToEnum.get(accountType);
	}

	
};

