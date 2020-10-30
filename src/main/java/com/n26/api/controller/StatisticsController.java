package com.n26.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.api.dto.ResponseStatisticsDTO;
import com.n26.service.StatisticsService;


@RestController
@RequestMapping("/statistics")
public class StatisticsController {

	
	private final StatisticsService statisticsService;

	public StatisticsController(StatisticsService statisticsService) {
		this.statisticsService = statisticsService;
	}

	@GetMapping
    public ResponseEntity<ResponseStatisticsDTO> getStatistics() {
        return new ResponseEntity<>(statisticsService.getStatistics(), HttpStatus.OK);
    }

}
