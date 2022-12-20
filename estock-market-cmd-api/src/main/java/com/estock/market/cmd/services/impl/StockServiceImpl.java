package com.estock.market.cmd.services.impl;

import com.estock.market.cmd.models.Stock;
import com.estock.market.cmd.repositories.StockRepository;
import com.estock.market.cmd.services.StockService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class StockServiceImpl implements StockService {

    private static final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);
    private final StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock createStock(Stock createStock) {
        logger.info("StockServiceImpl :: createStock:: Stock: {}", createStock);
        Stock stock = stockRepository.save(createStock);
        logger.info("StockServiceImpl :: createStock:: Stock: Stored");
        return stock;

    }

    @Override
    public List<Stock> findByDateBetweenAndCompanyCode(Instant fromDate, Instant toDate, String companyCode) {
        logger.info("StockServiceImpl :: findByDateBetweenAndCompanyCode:: Stock: {}, {}, {}", fromDate, toDate, companyCode);
        List<Stock> stocks = stockRepository.findByDateBetweenAndCompanyCode(fromDate, toDate, companyCode);
        logger.info("StockServiceImpl :: findByDateBetweenAndCompanyCode::  data executed");
        return stocks;
    }
}
