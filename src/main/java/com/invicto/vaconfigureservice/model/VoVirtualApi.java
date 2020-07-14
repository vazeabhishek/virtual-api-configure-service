package com.invicto.vaconfigureservice.model;

import lombok.Data;

import java.util.List;

@Data
public class VoVirtualApi {
    private String apiName;
    private String method;
    private String path;
    private List<VoVirtualApiSpec> voVirtualApiSpecList;
}
