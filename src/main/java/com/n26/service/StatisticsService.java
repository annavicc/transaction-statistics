package com.n26.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.PriorityQueue;

import org.springframework.stereotype.Service;

import com.n26.api.converter.StatisticsDataTypeConverter;
import com.n26.api.dto.ResponseStatisticsDTO;
import com.n26.constants.Constants;
import com.n26.model.Statistics;
import com.n26.model.Transaction;

@Service
public class StatisticsService {

	private StatisticsDataTypeConverter converter = new StatisticsDataTypeConverter();

	private final PriorityQueue<Transaction> transactions =
			new PriorityQueue<>(Comparator.comparing(Transaction::getTimestamp));

	public void addTransaction(Transaction transaction) {
		transactions.add(transaction);
	}

	public void deleteAllTransactions() {
		transactions.clear();
	}

	public ResponseStatisticsDTO getStatistics() {
		this.updateTransactions();
		Statistics statistics = calculate();
		return converter.toEntity(statistics);
	}

	private void updateTransactions() {
		while (!transactions.isEmpty()) {
			Transaction t = transactions.peek();
			if (Duration.between(t.getTimestamp(), LocalDateTime.now(ZoneOffset.UTC)).toMillis() > Constants.TRANSACTION_TIMEOUT_MILLIS) {
				transactions.poll();
			} else {
				break;
			}
		}
	}

	private Statistics calculate() {
		if (transactions.size() == 0) {
			return new Statistics();
		}

		return new Statistics(
				getSum(),
				getMin(),
				getMax(),
				getAvg(),
				getCount()
				);
	}

	private BigDecimal getMax() {
		BigDecimal max = BigDecimal.valueOf(Long.MIN_VALUE);
		for (Transaction t: transactions) {
			if (t.getAmount().compareTo(max) > 0) {
				max = t.getAmount();
			}
		}
		return max.setScale(Constants.DECIMAL_PLACES, RoundingMode.HALF_UP);
	}

	private BigDecimal getMin() {
		BigDecimal min = BigDecimal.valueOf(Long.MAX_VALUE);
		for (Transaction t: transactions) {
			if (t.getAmount().compareTo(min) < 0) {
				min = t.getAmount();
			}
		}
		return min.setScale(Constants.DECIMAL_PLACES, RoundingMode.HALF_UP);
	}

	private BigDecimal getSum() {
		BigDecimal sum = BigDecimal.ZERO;

		for (Transaction t: transactions) {
			sum = sum.add(t.getAmount());
		}
		return sum.setScale(Constants.DECIMAL_PLACES, RoundingMode.HALF_UP);
	}

	private BigDecimal getAvg() {
		BigDecimal sum = getSum();
		BigDecimal count = BigDecimal.valueOf(getCount());
		BigDecimal avg = sum.divide(count, Constants.DECIMAL_PLACES, RoundingMode.HALF_UP);
		return avg;
	}

	private long getCount() {
		return transactions.size();
	}

}
