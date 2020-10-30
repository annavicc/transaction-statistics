package com.n26.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.Test;

import com.n26.api.converter.TransactionDataTypeConverter;
import com.n26.api.dto.RequestTransactionDTO;
import com.n26.model.Transaction;

public class TransactionDataTypeConverterTest {

	private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX").withZone(ZoneId.of("UTC"));

	@Test
    public void convertFromDtoToModel() {
		RequestTransactionDTO dto = new RequestTransactionDTO(
				"80.34",
				TIMESTAMP_FORMATTER.format(Instant.now()));
		
		TransactionDataTypeConverter converter = new TransactionDataTypeConverter();
		Transaction model = converter.toEntity(dto);
		
		assertEquals(dto.getAmount(), model.getAmount());
		assertEquals(dto.getTimestamp(), model.getTimestamp());
    }

	@Test
    public void convertFromNullDto() {
		RequestTransactionDTO dto = null;
		
		TransactionDataTypeConverter converter = new TransactionDataTypeConverter();
		Transaction model = converter.toEntity(dto);

		assertNull(model);
    }
}
