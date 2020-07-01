package com.invicto.vaconfigureservice.model;

import lombok.Data;

import java.util.Map;

@Data
public class VoVirtualApiSpec {
    private String requestPayLoad;
    //private Map<String,String> requestHeaders;
    private String responsePayload;
    private int reponseStatusCode;
    //private Map<String,String> responseHeaders;
}
