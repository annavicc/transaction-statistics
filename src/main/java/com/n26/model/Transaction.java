package com.n26.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

public class Transaction {

	@NotNull
	private BigDecimal amount;

	@NotNull
	private LocalDateTime timestamp;

	public Transaction(BigDecimal amount, LocalDateTime timestamp) {
		this.amount = amount;
		this.timestamp = timestamp;
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
}
