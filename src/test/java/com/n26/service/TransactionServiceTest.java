package com.n26.service;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.n26.api.dto.RequestTransactionDTO;
import com.n26.api.dto.ResponseStatisticsDTO;

public class TransactionServiceTest {

    private static final DateTimeFormatter TIMESTAMP_FORMATTER = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX").withZone(ZoneId.of("UTC"));

    @Autowired
	private StatisticsService statisticsService;

	@Autowired
	private TransactionService transactionService;
	
	@Before
    public void setUp() {
        statisticsService = new StatisticsService();
        transactionService = new TransactionService(statisticsService);
    }

	@Test
    public void addTransaction() {
    	String amount = "123.00";
    	RequestTransactionDTO transactionDTO = getTransaction(amount);

		transactionService.addTransaction(transactionDTO);

        ResponseStatisticsDTO statisticsDTO = statisticsService.getStatistics();
        assertEquals(amount, statisticsDTO.getSum());
        assertEquals(amount, statisticsDTO.getAvg());
        assertEquals(amount, statisticsDTO.getMin());
        assertEquals(amount, statisticsDTO.getMax());
        assertEquals(1, statisticsDTO.getCount());
    }
	
	@Test
	public void deleteTransactionsEmpty() {
		String zero = "0.00";

		statisticsService.deleteAllTransactions();
		ResponseStatisticsDTO statisticsDTO = statisticsService.getStatistics(); 
		
		assertEquals(zero, statisticsDTO.getSum());
        assertEquals(zero, statisticsDTO.getAvg());
        assertEquals(zero, statisticsDTO.getMin());
        assertEquals(zero, statisticsDTO.getMax());
        assertEquals(statisticsDTO.getCount(), 0);
	}

	@Test
	public void deleteTransactionsPopulated() {
		String zero = "0.00";

		RequestTransactionDTO transactionDTO1 = getTransaction("12.00");
    	RequestTransactionDTO transactionDTO2 = getTransaction("58.48");
    	RequestTransactionDTO transactionDTO3 = getTransaction("45.11");
    	RequestTransactionDTO transactionDTO4 = getTransaction("89.44");

    	transactionService.addTransaction(transactionDTO1);
    	transactionService.addTransaction(transactionDTO2);
    	transactionService.addTransaction(transactionDTO3);
    	transactionService.addTransaction(transactionDTO4);

    	statisticsService.deleteAllTransactions();
    	ResponseStatisticsDTO statisticsDTO = statisticsService.getStatistics();

		assertEquals(zero, statisticsDTO.getSum());
        assertEquals(zero, statisticsDTO.getAvg());
        assertEquals(zero, statisticsDTO.getMin());
        assertEquals(zero, statisticsDTO.getMax());
        assertEquals(zero, statisticsDTO.getCount(), 0);
	}
	
	private RequestTransactionDTO getTransaction(String amount) {
    	Instant now = Instant.now();
    	String formattedTimestamp = TIMESTAMP_FORMATTER.format(now);
    	return new RequestTransactionDTO(amount, formattedTimestamp);
	}

}
