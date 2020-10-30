package com.n26.api.converter;

import com.n26.api.dto.RequestTransactionDTO;
import com.n26.model.Transaction;

public class TransactionDataTypeConverter {

	public Transaction toEntity(RequestTransactionDTO dto) {
		if (dto == null) {
			return null;
		}
		
		return new Transaction(dto.getAmount(), dto.getTimestamp());
	}
}
