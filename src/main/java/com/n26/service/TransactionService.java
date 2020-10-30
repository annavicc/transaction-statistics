package com.n26.service;

import org.springframework.stereotype.Service;

import com.n26.api.converter.TransactionDataTypeConverter;
import com.n26.api.dto.RequestTransactionDTO;
import com.n26.model.Transaction;

@Service
public class TransactionService {

	private final StatisticsService statisticsService;

	private TransactionDataTypeConverter converter = new TransactionDataTypeConverter();

	public TransactionService(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}
	
	public void addTransaction(RequestTransactionDTO dto) {
		Transaction transaction = converter.toEntity(dto);
		statisticsService.addTransaction(transaction);

	}
	
	public void deleteTransactions() {
		this.statisticsService.deleteAllTransactions();
	}
	
}
