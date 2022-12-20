package com.estock.market.cmd.repositories;

import com.estock.market.cmd.models.Company;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends MongoRepository<Company, String> {

    @Query("{'$or': [{'companyName':{$regex: ?0, $options: '1i'}}, {'companyCode':{$regex: ?0, $options: '1i'}}]}")
    List<Company> findByFilterRegex(String filter);


   Optional<Company> findByCompanyCode(String companyCode);
    Optional<Company> findByCompanyName(String companyName);
    void deleteByCompanyCode(String companyCode);


    Optional<Company> findByCompanyCodeIgnoreCaseOrCompanyNameIgnoreCase(String companyCode, String companyName);

}
