package com.estock.market.cmd.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.text.DecimalFormat;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Document(collection = "esm_stock_details")
public class Stock {

    @Id
    private String id;
    private float stockPrice;
    private Instant date;
    private String time;
    private String companyCode;

    public void setStockPrice(float stockPrice) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        float twoDigitsFR = Float.valueOf(decimalFormat.format(stockPrice));
        this.stockPrice = twoDigitsFR;
    }
}
