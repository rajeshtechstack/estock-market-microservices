package com.estock.market.cmd.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "esm_company_details")
public class Company {
    @Id
    private String id;
    private String companyName;
    private String companyCode;
    private String ceo;
    private String companyWebsite;
    private BigDecimal turnOver;
    private List<ExchangeType> exchangeTypes;
}
