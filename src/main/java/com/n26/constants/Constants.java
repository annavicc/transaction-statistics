package com.n26.constants;

public class Constants {

	public static final String EMPTY_BODY = "Body cannot be empty";
	public static final String AMOUNT_REQUIRED = "Amount is required";
	public static final String TIMESTAMP_REQUIRED = "Timestamp is required";
	public static final String MISSING_ATTRIBUTES = "Timestamp and amount are required";
	public static final String FUTURE_TRANSACTION = "Timestamp cannot be in the future";
	public static final String TRANSACTION_TIMEOUT = "Timestamp has expired";
	public static final String INVALID_TIMESTAMP = "Timestamp must be YYYY-MM-DDThh:mm:ss.sssZ";
	public static final String INVALID_AMOUNT = "Amount is not a number";
	public static final String TIMESTAMP_REGEX = "^\\d{4}[-]\\d{2}[-]\\d{2}[T]\\d{2}:\\d{2}:\\d{2}[.]\\d{3}[Z]$";
	public static final String NUMBER_REGEX = "^[0-9]*[.]?[0-9]*$";
	public static final long TRANSACTION_TIMEOUT_MILLIS = 60000;
	public static final int DECIMAL_PLACES = 2;
}
