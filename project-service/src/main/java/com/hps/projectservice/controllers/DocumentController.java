package com.hps.projectservice.controllers;

import com.hps.projectservice.entities.Document;
import com.hps.projectservice.services.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/projects")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/{projectId}/documents")
    public ResponseEntity<Document> uploadDocument(@PathVariable Long projectId,
                                                   @RequestParam("file") MultipartFile file) throws IOException {
        Document doc = documentService.saveDocument(projectId, file);
        return ResponseEntity.ok(doc);
    }

    @GetMapping("/{projectId}/documents")
    public ResponseEntity<List<Document>> getDocuments(@PathVariable Long projectId) {
        return ResponseEntity.ok(documentService.getDocumentsByProject(projectId));
    }

    @GetMapping("/documents/{documentId}/download")
    public ResponseEntity<Resource> download(@PathVariable Long documentId) throws IOException {
        Resource file = documentService.downloadDocument(documentId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @DeleteMapping("/documents/{documentId}")
    public ResponseEntity<Void> delete(@PathVariable Long documentId) throws IOException {
        documentService.deleteDocument(documentId);
        return ResponseEntity.noContent().build();
    }
}

