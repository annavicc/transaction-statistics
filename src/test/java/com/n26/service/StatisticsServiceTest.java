package com.n26.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.n26.api.dto.ResponseStatisticsDTO;
import com.n26.model.Transaction;

public class StatisticsServiceTest {

    @Autowired
	private StatisticsService statisticsService;

	@Before
    public void setUp() {
        statisticsService = new StatisticsService();
    }

	@Test
    public void getStatistics() {
		Transaction transaction1 = getTransaction(BigDecimal.valueOf(100.850));
    	Transaction transaction2 = getTransaction(BigDecimal.valueOf(99.4));
    	Transaction transaction3 = getTransaction(BigDecimal.valueOf(41.11));
    	Transaction transaction4 = getTransaction(BigDecimal.valueOf(55.98));

    	statisticsService.addTransaction(transaction1);
    	statisticsService.addTransaction(transaction2);
    	statisticsService.addTransaction(transaction3);
    	statisticsService.addTransaction(transaction4);

        ResponseStatisticsDTO statisticsDTO = statisticsService.getStatistics();

        assertEquals("297.34", statisticsDTO.getSum());
        assertEquals("74.34", statisticsDTO.getAvg());
        assertEquals("41.11", statisticsDTO.getMin());
        assertEquals("100.85", statisticsDTO.getMax());
        assertEquals(4, statisticsDTO.getCount());
    }

	@Test
    public void getStatisticsDecimalPlaces() {
		Transaction transaction1 = getTransaction(BigDecimal.valueOf(100.8));
		statisticsService.addTransaction(transaction1);

        ResponseStatisticsDTO statisticsDTO = statisticsService.getStatistics();
        String expected = "100.80";

        assertEquals(expected, statisticsDTO.getSum());
        assertEquals(expected, statisticsDTO.getAvg());
        assertEquals(expected, statisticsDTO.getMin());
        assertEquals(expected, statisticsDTO.getMax());
        assertEquals(1, statisticsDTO.getCount(), 1);
    }

	@Test
    public void getStatisticsRoundUp() {
		Transaction transaction1 = getTransaction(BigDecimal.valueOf(100.8599784));
		statisticsService.addTransaction(transaction1);

        ResponseStatisticsDTO statisticsDTO = statisticsService.getStatistics();
        String expected = "100.86";

        assertEquals(expected, statisticsDTO.getSum());
        assertEquals(expected, statisticsDTO.getAvg());
        assertEquals(expected, statisticsDTO.getMin());
        assertEquals(expected, statisticsDTO.getMax());
        assertEquals(1, statisticsDTO.getCount(), 1);
    }

	@Test
	public void getStatisticsEmpty() {
		String zero = "0.00";

		ResponseStatisticsDTO statisticsDTO = statisticsService.getStatistics(); 
		
		assertEquals(zero, statisticsDTO.getSum());
        assertEquals(zero, statisticsDTO.getAvg());
        assertEquals(zero, statisticsDTO.getMin());
        assertEquals(zero, statisticsDTO.getMax());
        assertEquals(0, statisticsDTO.getCount());
	}
	
	@Test
    public void addTransaction() {
		String amount = "100.85";
		Transaction transaction1 = getTransaction(new BigDecimal(amount));

    	statisticsService.addTransaction(transaction1);

        ResponseStatisticsDTO statisticsDTO = statisticsService.getStatistics();

        assertEquals(amount, statisticsDTO.getSum());
        assertEquals(amount, statisticsDTO.getAvg());
        assertEquals(amount, statisticsDTO.getMin());
        assertEquals(amount, statisticsDTO.getMax());
        assertEquals(1, statisticsDTO.getCount());
    }

	@Test
    public void deleteAllTransations() {
		Transaction transaction1 = getTransaction(BigDecimal.valueOf(100.850));
    	Transaction transaction2 = getTransaction(BigDecimal.valueOf(99.4));
    	Transaction transaction3 = getTransaction(BigDecimal.valueOf(41.11));
    	Transaction transaction4 = getTransaction(BigDecimal.valueOf(55.98));

    	statisticsService.addTransaction(transaction1);
    	statisticsService.addTransaction(transaction2);
    	statisticsService.addTransaction(transaction3);
    	statisticsService.addTransaction(transaction4);
    	statisticsService.deleteAllTransactions();

        ResponseStatisticsDTO statisticsDTO = statisticsService.getStatistics();
        String zero = "0.00";

        assertEquals(zero, statisticsDTO.getSum());
        assertEquals(zero, statisticsDTO.getAvg());
        assertEquals(zero, statisticsDTO.getMin());
        assertEquals(zero, statisticsDTO.getMax());
        assertEquals(0, statisticsDTO.getCount());
    }
	
	@Test
    public void deleteTransationsEmpty() {
		String zero = "0.00";

		statisticsService.deleteAllTransactions();
        ResponseStatisticsDTO statisticsDTO = statisticsService.getStatistics();

        assertEquals(zero, statisticsDTO.getSum());
        assertEquals(zero, statisticsDTO.getAvg());
        assertEquals(zero, statisticsDTO.getMin());
        assertEquals(zero, statisticsDTO.getMax());
        assertEquals(0, statisticsDTO.getCount());
    }

	private Transaction getTransaction(BigDecimal amount) {
    	Instant now = Instant.now();
    	return new Transaction(amount, LocalDateTime.ofInstant(now, ZoneOffset.UTC));
	}
	
}
