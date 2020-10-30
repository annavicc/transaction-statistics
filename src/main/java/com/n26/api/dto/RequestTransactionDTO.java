package com.n26.api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.n26.constants.Constants;
import com.n26.exceptions.UnprocessableTransactionException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestTransactionDTO {

	private BigDecimal amount;

	private LocalDateTime timestamp;

	public RequestTransactionDTO(String amount, String timestamp) {
		this.amount = convertAmountFromString(amount);
		this.timestamp = convertTimestampFromString(timestamp);
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public LocalDateTime getTimestamp() {
		return this.timestamp;
	}

	private LocalDateTime convertTimestampFromString(String timestamp) {
		if (timestamp.isBlank()) {
			return null;
		}
		Pattern p = Pattern.compile(Constants.TIMESTAMP_REGEX);

		if (!p.matcher(timestamp).matches()) {
			throw new UnprocessableTransactionException(Constants.INVALID_TIMESTAMP);
		}

		DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
		final ZonedDateTime parsed = ZonedDateTime
				.parse(timestamp, formatter.withZone(ZoneId.of("UTC")));
		return parsed.toLocalDateTime();
	}

	private BigDecimal convertAmountFromString(String amount) {
		if (amount.isBlank()) {
			return null;
		}
		Pattern p = Pattern.compile(Constants.NUMBER_REGEX);
		if (!p.matcher(amount).matches()) {
			throw new UnprocessableTransactionException(Constants.INVALID_AMOUNT);
		}

		return new BigDecimal(amount);
	}

}
