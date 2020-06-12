package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.model.VoVirtualApi;
import org.springframework.http.ResponseEntity;

public interface VirtualApiService {
    public ResponseEntity<String> createApi(VoVirtualApi voVirtualApi);
    public ResponseEntity<String> deleteApi(String apiId);
}
