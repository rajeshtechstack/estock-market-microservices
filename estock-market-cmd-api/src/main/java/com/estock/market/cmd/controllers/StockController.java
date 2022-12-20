package com.estock.market.cmd.controllers;

import com.estock.market.cmd.dto.GenericResponse;
import com.estock.market.cmd.models.Company;
import com.estock.market.cmd.models.Stock;
import com.estock.market.cmd.services.CompanyService;
import com.estock.market.cmd.services.StockService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1.0/market/stock")
public class StockController {

    private static final Logger logger = LoggerFactory.getLogger(StockController.class);

    private final CompanyService companyService;
    private final StockService stockService;

    public StockController(CompanyService companyService, StockService stockService) {

        this.companyService = companyService;
        this.stockService = stockService;
    }


    @PostMapping(value = "/add/{companyCode}")
    public ResponseEntity<GenericResponse> createStock(
            @Valid @RequestBody Stock registerStock,
            @PathVariable String companyCode){
        logger.info("StockController :: createStock:: companyCode: {}, Stock: {}", companyCode, registerStock);
        registerStock.setDate(Instant.now());
        registerStock.setTime(getTime());

        try{
            Optional<Company> company = companyService.findByCompanyCode(companyCode);
            if(company.isPresent()){
                logger.info("StockController :: createStock:: Company details: {}",company.get());
               Stock stock =  stockService.createStock(registerStock);
                return new ResponseEntity<>(new GenericResponse("Stock added successfully", stock), HttpStatus.CREATED);
            }else {
                logger.info("StockController :: createStock:: Company data not found to add stock");
                return new ResponseEntity<>(new GenericResponse("Company data not found to add stock"), HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            logger.error("StockController :: createStock:: Error while adding stock request :: {}", e.getMessage());
            var errorMessage = "Error while adding stock request for";
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping(value = "/get/{companyCode}/{startDate}/{endDate}")
    public ResponseEntity<GenericResponse> getStocks(@PathVariable String companyCode,
                                                      @PathVariable String startDate,
                                                      @PathVariable String endDate){
        logger.info("StockController :: getStocks:: companyCode: {}, startDate: {}, endDate: {}",
                companyCode, startDate, endDate);
        try{
            Instant fromDate = Instant.parse(startDate);
            Instant toDate = Instant.parse(endDate);
            logger.info("StockController :: getStocks:: formatted dates:: startDate: {}, endDate: {}",
                    fromDate, toDate);
            List<Stock> stocks = stockService.findByDateBetweenAndCompanyCode(fromDate,toDate, companyCode);
            if(stocks == null || stocks.isEmpty()){
                logger.info("StockController :: getStocks:: Stocks not found");
                return new ResponseEntity<>(new GenericResponse("Data not found"), HttpStatus.NO_CONTENT);
            }
            logger.info("StockController :: getStocks:: Stocks size:: {}", stocks.size());
            return new ResponseEntity<>(new GenericResponse("Data found", stocks), HttpStatus.OK);
        }catch (Exception e){
            logger.error("StockController :: getStocks:: Error while executing get all stocks request :: {}"
                    , e.getMessage());
            var errorMessage = "Error while executing get all stocks request ";
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getdateandtime")
    public ResponseEntity<GenericResponse> getDateAndTime(){
        GenericResponse response= new GenericResponse("Test");
        //GenericResponse response = new GenericResponse(LocalDate.now(), LocalTime.now());
        ResponseEntity<GenericResponse> responseEntity = new ResponseEntity<>(response
                , HttpStatus.OK);
        System.out.println(responseEntity.getBody());
        return responseEntity;
    }

    private String getTime(){
        LocalTime time = LocalTime.now(ZoneId.of("UTC"));
        String timeColonPattern = "HH:mm:ss";
        DateTimeFormatter timeColonFormatter = DateTimeFormatter.ofPattern(timeColonPattern);
        return time.format(timeColonFormatter);
    }
}
