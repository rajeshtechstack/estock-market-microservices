package com.estock.market.cmd.services.impl;

import com.estock.market.cmd.models.Company;
import com.estock.market.cmd.repositories.CompanyRepository;
import com.estock.market.cmd.services.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(Company registerCompany) {
        Company company = companyRepository.save(registerCompany);
        return company;
    }

    @Override
    public Optional<Company> getCompanyById(String id) {
        Optional<Company> company = companyRepository.findById(id);
        return company;
    }

    @Override
    public Company updateCompany(Company updateCompany) {
        Company company = companyRepository.save(updateCompany);
        return company;
    }

    @Override
    public void removeCompany(String id) {
        companyRepository.deleteById(id);
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies;
    }

    @Override
    public void deleteByCompanyCode(String companyCode) {
        companyRepository.deleteByCompanyCode(companyCode);
    }

    @Override
    public Optional<Company> findByCompanyCode(String companyCode) {
        Optional<Company> company = companyRepository.findByCompanyCode(companyCode);
        return company;
    }

    @Override
    public Optional<Company> findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }

    @Override
    public Optional<Company> findByCompanyCodeOrCompanyName(String companyCode, String companyName) {
        return companyRepository.findByCompanyCodeIgnoreCaseOrCompanyNameIgnoreCase(companyCode, companyName);
    }

}
