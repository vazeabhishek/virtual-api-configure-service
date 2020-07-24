package com.invicto.vaconfigureservice.model;

import lombok.Data;

@Data
public class VoVirtualApiSpec {
    private String reqPayload;
    private String reqHeaders;
    private String respHeaders;
    private String respPayload;
    private int httpStatus;
}
