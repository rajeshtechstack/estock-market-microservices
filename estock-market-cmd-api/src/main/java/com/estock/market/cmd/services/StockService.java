package com.estock.market.cmd.services;

import com.estock.market.cmd.models.Stock;

import java.time.Instant;
import java.util.List;

public interface StockService {
    Stock createStock(Stock stock);
    List<Stock> findByDateBetweenAndCompanyCode(Instant fromDate, Instant toDate, String companyCode);
}
