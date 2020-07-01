package com.invicto.vaconfigureservice.model;

import lombok.Data;

import java.util.List;

@Data
public class VoVirtualApi {
    private String name;
    private String requestMethod;
    private String path;
    List<VoVirtualApiSpec> voVirtualApiSpecList;
}
