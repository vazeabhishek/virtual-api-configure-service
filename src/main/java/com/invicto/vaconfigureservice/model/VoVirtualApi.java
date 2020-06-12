package com.invicto.vaconfigureservice.model;

import lombok.Data;

import java.util.Map;

@Data
public class VoVirtualApi {
    private String name;
    private String requestPayLoad;
    private String requestMethod;
    private Map<String,String> requestHeaders;
    private String responsePayload;
    private String reponseStatusCode;
    private Map<String,String> responseHeaders;
}
