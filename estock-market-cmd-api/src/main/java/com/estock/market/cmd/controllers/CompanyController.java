package com.estock.market.cmd.controllers;

import com.estock.market.cmd.dto.GenericResponse;
import com.estock.market.cmd.models.Company;
import com.estock.market.cmd.services.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1.0/market/company")
public class CompanyController {

    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @PostMapping(value = "/register")
    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    public ResponseEntity<GenericResponse> createCompany(@RequestBody Company registerCompany){
        logger.info("CompanyController :: createCompany:: company: {}",
                registerCompany);
        BigDecimal decimal = new BigDecimal(100000000);
       int value =  registerCompany.getTurnOver().compareTo(decimal);

        if(value == 0 || value == 1){
            Optional<Company> company = companyService.findByCompanyCodeOrCompanyName(registerCompany.getCompanyCode()
                    , registerCompany.getCompanyName());
            if(company.isEmpty()){
                try{
                    Company createdCompany = companyService.createCompany(registerCompany);
                    logger.info("CompanyController :: createCompany:: Company registered successfully: {}");
                    return new ResponseEntity<>(new GenericResponse("Company registered successfully", createdCompany), HttpStatus.CREATED);
                }catch (Exception e){
                    logger.error("CompanyController :: createCompany:: Error while registering company request: {}"
                            ,e.getMessage());
                    var errorMessage = "Error while registering company request for id - "+registerCompany.getId();
                    return new ResponseEntity<>(new GenericResponse(registerCompany.getId(),
                            errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
            var errorMessage="";
            if(company.get().getCompanyCode().equalsIgnoreCase(registerCompany.getCompanyCode())){
                errorMessage = "Company code already existed...!!!";
            }else if(company.get().getCompanyName().equalsIgnoreCase(registerCompany.getCompanyName())){
                errorMessage = "Company name already existed...!!!";
            }

            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);

        }
        var errorMessage = "Turnover should be greater than or equal to 10cr ";
        return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);


    }



    @DeleteMapping(value = "/delete/{companyCode}")
    public ResponseEntity<GenericResponse> deleteCompanyByCompanyCode(@PathVariable(value = "companyCode") String companyCode){

        try{

            companyService.deleteByCompanyCode(companyCode);
            logger.info("CompanyController :: deleteCompanyByCompanyCode:: Company successfully removed:: " +
                            "companyCode: {}", companyCode);
            return new ResponseEntity<>(new GenericResponse("Company successfully removed!!"), HttpStatus.OK);
        }catch (Exception e){
            logger.error("CompanyController :: deleteCompanyByCompanyCode:: Error while executing remove" +
                            " user request for company code: {} : error: {}"
                    ,e.getMessage(), companyCode);
            var errorMessage = "Error while executing remove user request for company code - "+companyCode;
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/getall")
    public ResponseEntity<GenericResponse> getCompanies(){

        try{

            List<Company> companies = companyService.getAllCompanies();
            if(companies == null || companies.isEmpty()){
                logger.info("CompanyController :: getCompanies:: Stocks not found");
                return new ResponseEntity<>(new GenericResponse("Data not found"), HttpStatus.NO_CONTENT);
            }
            logger.info("CompanyController :: getCompanies:: companies size:: {}", companies.size());
            return new ResponseEntity<>(new GenericResponse("Data found", companies), HttpStatus.OK);
        }catch (Exception e){
            logger.error("CompanyController :: getCompanies:: Error while executing get all companies request: error: {}"
                    ,e.getMessage());
            var errorMessage = "Error while executing get all companies request ";
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/info/{companyCode}")
    public ResponseEntity<GenericResponse> getCompanyByCompanyCode
            (@PathVariable(value = "companyCode") String companyCode){
        logger.info("CompanyController :: getCompanyByCompanyCode:: company:: {}", companyCode);
        try{
            Optional<Company> company = companyService.findByCompanyCode(companyCode);

            if(company.isEmpty()){
                logger.info("CompanyController :: getCompanyByCompanyCode:: company details not found::");
                return new ResponseEntity<>(new GenericResponse("Data not found"), HttpStatus.NO_CONTENT);
            }
            logger.info("CompanyController :: getCompanyByCompanyCode:: company:: {}", company.get());
            return new ResponseEntity<>(new GenericResponse("Data found", company.get()), HttpStatus.OK);
        }catch (Exception e){
            logger.error("CompanyController :: getCompanyByCompanyCode:: " +
                            "Error while executing get company by company code request: error: {}"
                    ,e.getMessage());
            var errorMessage = "Error while executing get company by company code request ";
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping(value = "/info/{companyCode}/{companyName}")
    public ResponseEntity<GenericResponse> findByCompanyCodeOrCompanyName
            (@PathVariable(value = "companyCode") String companyCode,
             @PathVariable(value = "companyName") String companyName){
        logger.info("CompanyController :: findByCompanyCodeOrCompanyName:: companyCode: {}, companyName: {}",
                companyCode, companyName);
        try{
            Optional<Company> company = companyService.findByCompanyCodeOrCompanyName(companyCode, companyName);

            if(company.isEmpty()){
                logger.info("CompanyController :: findByCompanyCodeOrCompanyName:: company details not found::");
                return new ResponseEntity<>(new GenericResponse("Data not found"), HttpStatus.NO_CONTENT);
            }
            logger.info("CompanyController :: getCompanyByCompanyCode:: company:: {}", company.get());
            return new ResponseEntity<>(new GenericResponse("Data found", company.get()), HttpStatus.OK);
        }catch (Exception e){
            logger.error("CompanyController :: findByCompanyCodeOrCompanyName:: " +
                            "Error while executing get company by company code request: error: {}"
                    ,e.getMessage());
            var errorMessage = "Error while executing findByCompanyCodeOrCompanyName request ";
            return new ResponseEntity<>(new GenericResponse(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
