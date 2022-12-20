package com.estock.market.cmd.services;

import com.estock.market.cmd.models.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyService {

    Company createCompany(Company company);
    Optional<Company> getCompanyById(String id);
    Company updateCompany(Company user);
    void removeCompany(String id);
    List<Company> getAllCompanies();
    void deleteByCompanyCode(String companyCode);
    Optional<Company> findByCompanyCode(String companyCode);
    Optional<Company> findByCompanyName(String companyName);
    Optional<Company> findByCompanyCodeOrCompanyName(String companyCode, String companyName);
}
