package com.invicto.vaconfigureservice.service;

import com.invicto.vaconfigureservice.model.VoProject;
import org.springframework.http.ResponseEntity;

public interface ProjectService {
    public ResponseEntity<String> createProject(VoProject voProject);
    public ResponseEntity<String> deleteProject(String projectId);
}
