package com.n26.validator;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.n26.constants.Constants;
import com.n26.api.dto.RequestTransactionDTO;
import com.n26.api.validator.TransactionValidator;
import com.n26.exceptions.TransactionTimeoutExpection;
import com.n26.exceptions.UnprocessableTransactionException;
import com.n26.exceptions.ValidationErrorException;

public class TransactionValidatorTest {

	TransactionValidator validator;

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX").withZone(ZoneId.of("UTC"));

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setUp() {
		validator = new TransactionValidator();
    }

	@Test
    public void validateNullAtributes() {
        exceptionRule.expect(ValidationErrorException.class);
        exceptionRule.expectMessage(Constants.MISSING_ATTRIBUTES);
        
		RequestTransactionDTO dto = new RequestTransactionDTO("", "");
		validator.validate(dto);
    }

	@Test
    public void validateNullAmount() {
        exceptionRule.expect(ValidationErrorException.class);
        exceptionRule.expectMessage(Constants.AMOUNT_REQUIRED);
        
		RequestTransactionDTO dto = new RequestTransactionDTO("", getCurrentTimestamp());
		validator.validate(dto);

    }

	@Test
	public void validateEmptyTimestamp() {
        exceptionRule.expect(ValidationErrorException.class);
        exceptionRule.expectMessage(Constants.TIMESTAMP_REQUIRED);
        
		RequestTransactionDTO dto = new RequestTransactionDTO("123.312", "");
		validator.validate(dto);
	}
	
	@Test
    public void validateFutureTransaction() {
		exceptionRule.expect(UnprocessableTransactionException.class);
        exceptionRule.expectMessage(Constants.FUTURE_TRANSACTION);
        
		RequestTransactionDTO dto =
				new RequestTransactionDTO("123.312", getFutureTimestamp());
		validator.validate(dto);
    }

	@Test
    public void validateExpiredTransaction() {
		exceptionRule.expect(TransactionTimeoutExpection.class);
        exceptionRule.expectMessage(Constants.TRANSACTION_TIMEOUT);
        
		RequestTransactionDTO dto =
				new RequestTransactionDTO("123.312", getPastTimestamp());
		validator.validate(dto);
    }

	@Test
    public void validateUnprocessableAmount() {
        exceptionRule.expect(UnprocessableTransactionException.class);
        exceptionRule.expectMessage(Constants.INVALID_AMOUNT);
        
		RequestTransactionDTO dto = new RequestTransactionDTO("abc", getCurrentTimestamp());
		validator.validate(dto);
    }

	@Test
    public void validateUnprocessableTimestamp() {
        exceptionRule.expect(UnprocessableTransactionException.class);
        exceptionRule.expectMessage(Constants.INVALID_TIMESTAMP);
        
		RequestTransactionDTO dto = new RequestTransactionDTO("123", "18:33:00");
		validator.validate(dto);
    }

	private String getCurrentTimestamp() {
    	Instant now = Instant.now();
    	return TIMESTAMP_FORMATTER.format(now);
	}
	
	private String getFutureTimestamp() {
    	Instant future = Instant.now().plusSeconds(200);
    	return TIMESTAMP_FORMATTER.format(future);
	}
	
	private String getPastTimestamp() {
    	Instant past = Instant.now().minusSeconds(200);
    	return TIMESTAMP_FORMATTER.format(past);
	}
	
}
