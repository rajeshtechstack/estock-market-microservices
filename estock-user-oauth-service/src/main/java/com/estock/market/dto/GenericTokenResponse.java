package com.estock.market.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenericTokenResponse {
    private String access_token;
    private String token_type;
    private int expires_in;
    private String  refresh_token;
    private String  scope;
}
