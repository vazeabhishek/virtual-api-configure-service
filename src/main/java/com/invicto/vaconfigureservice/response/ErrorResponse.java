package com.invicto.vaconfigureservice.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.invicto.vaconfigureservice.exception.base.JsonConversionFailureException;
import lombok.Data;

@Data
public class ErrorResponse {
    private String detail;

    public String toJsonString(ObjectMapper mapper) {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new JsonConversionFailureException();
        }
    }
}
