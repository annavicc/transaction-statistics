package com.n26.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.n26.constants.Constants;

@JsonPropertyOrder({
	"sum",
	"avg",
	"max",
	"min",
	"count"
})
public class Statistics {
	
	private long count;
	private BigDecimal sum;
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal avg;

    public Statistics() {
    	this.sum = this.min = this.max = this.avg = BigDecimal.ZERO.setScale(Constants.DECIMAL_PLACES);
    	this.count = 0L;
    }

    public Statistics(BigDecimal sum, BigDecimal min, BigDecimal max,
    				  BigDecimal avg, long count) {
        this.sum = sum;
        this.min = min;
        this.max = max;
        this.avg = avg;
        this.count = count;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

}
