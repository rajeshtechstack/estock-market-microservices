package com.estock.market.services;

import com.estock.market.dto.GenericResponse;
import com.estock.market.dto.requests.UserRequest;

public interface AuthService {
    GenericResponse getAuthToken(UserRequest userRequest, String baseUrl);
}
