package com.n26.api.validator;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.n26.api.dto.RequestTransactionDTO;
import com.n26.constants.Constants;
import com.n26.exceptions.TransactionTimeoutExpection;
import com.n26.exceptions.UnprocessableTransactionException;
import com.n26.exceptions.ValidationErrorException;

@Service
public class TransactionValidator {

	public void validate(RequestTransactionDTO dto) {
		validateRequest(dto);
		validateLifetime(dto);
	}

	private void validateRequest(RequestTransactionDTO dto) {
		if (dto == null) {
			throw new ValidationErrorException(Constants.EMPTY_BODY);
		}
		if (dto.getAmount() == null && dto.getTimestamp() == null) {
			throw new ValidationErrorException(Constants.MISSING_ATTRIBUTES);
		}
		if (dto.getAmount() == null) {
			throw new ValidationErrorException(Constants.AMOUNT_REQUIRED);
		}
		if (dto.getTimestamp() == null) {
			throw new ValidationErrorException(Constants.TIMESTAMP_REQUIRED);
		}
	}

	private void validateLifetime(RequestTransactionDTO dto) {
		LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);

		if (dto.getTimestamp().isAfter(now)) {
			throw new UnprocessableTransactionException(Constants.FUTURE_TRANSACTION);
		} else if (Duration.between(dto.getTimestamp(), now).toMillis() > Constants.TRANSACTION_TIMEOUT_MILLIS) {
			throw new TransactionTimeoutExpection(Constants.TRANSACTION_TIMEOUT);
		}
	}

}
