package com.n26.api.converter;

import com.n26.api.dto.ResponseStatisticsDTO;
import com.n26.model.Statistics;

public class StatisticsDataTypeConverter {

	public ResponseStatisticsDTO toEntity(Statistics model) {
		if (model == null) {
			return null;
		}

		return new ResponseStatisticsDTO(
				model.getSum().toPlainString(),
				model.getAvg().toPlainString(),
				model.getMax().toPlainString(),
				model.getMin().toPlainString(),
				model.getCount());
	}

}
