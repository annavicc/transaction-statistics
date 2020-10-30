package com.n26.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.n26.api.dto.RequestTransactionDTO;
import com.n26.api.validator.TransactionValidator;
import com.n26.service.TransactionService;

@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;


	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}
	
	@Autowired
    private TransactionValidator validator;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void post(@RequestBody RequestTransactionDTO transactionDTO) {
		validator.validate(transactionDTO);
		this.transactionService.addTransaction(transactionDTO);
	}

	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete() {
		this.transactionService.deleteTransactions();
	}
}
