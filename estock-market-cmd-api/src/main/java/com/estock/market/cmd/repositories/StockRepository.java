package com.estock.market.cmd.repositories;


import com.estock.market.cmd.models.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.Instant;
import java.util.List;

public interface StockRepository extends MongoRepository<Stock, String> {

    List<Stock> findByDateBetweenAndCompanyCode(Instant fromDate, Instant toDate, String companyCode);
}
