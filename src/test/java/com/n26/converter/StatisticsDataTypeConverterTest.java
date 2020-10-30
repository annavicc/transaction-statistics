package com.n26.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Test;

import com.n26.api.converter.StatisticsDataTypeConverter;
import com.n26.api.dto.ResponseStatisticsDTO;
import com.n26.model.Statistics;

public class StatisticsDataTypeConverterTest {

	@Test
    public void convertFromModeltoDTO() {
		Statistics model = new Statistics(
				BigDecimal.valueOf(80.34),
				BigDecimal.valueOf(80.34),
				BigDecimal.valueOf(80.34),
				BigDecimal.valueOf(80.34),
				1);
		
		StatisticsDataTypeConverter converter = new StatisticsDataTypeConverter();
		ResponseStatisticsDTO dto = converter.toEntity(model);
		
		assertEquals(String.valueOf(model.getSum()), dto.getSum());
		assertEquals(String.valueOf(model.getAvg()), dto.getAvg());
		assertEquals(String.valueOf(model.getMin()), dto.getMin());
		assertEquals(String.valueOf(model.getMax()), dto.getMax());
		assertEquals(model.getCount(), dto.getCount());
    }

	@Test
    public void convertFromNullModel() {
		Statistics model = null;
		
		StatisticsDataTypeConverter converter = new StatisticsDataTypeConverter();
		ResponseStatisticsDTO dto = converter.toEntity(model);
		assertNull(dto);
    }

}
