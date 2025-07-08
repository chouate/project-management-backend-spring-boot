package com.hps.projectservice.services;

import com.hps.projectservice.entities.Document;
import com.hps.projectservice.entities.Project;
import com.hps.projectservice.exceptions.ResourceNotFoundException;
import com.hps.projectservice.repositories.DocumentRepository;
import com.hps.projectservice.repositories.ProjectRepository;
import org.springframework.core.io.Resource;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final ProjectRepository projectRepository;

    private final String uploadDir = "uploads/documents/";

    public Document saveDocument(Long projectId, MultipartFile file) throws IOException {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        Files.createDirectories(Paths.get(uploadDir));
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filepath = Paths.get(uploadDir, filename);
        Files.copy(file.getInputStream(), filepath, StandardCopyOption.REPLACE_EXISTING);

        Document doc = Document.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .path(filepath.toString())
                .dateAdded(LocalDateTime.now())
                .project(project)
                .build();

        return documentRepository.save(doc);
    }

    public Resource downloadDocument(Long documentId) throws IOException {
        Document doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));

        Path path = Paths.get(doc.getPath());
        return new UrlResource(path.toUri());
    }

    public void deleteDocument(Long documentId) throws IOException {
        Document doc = documentRepository.findById(documentId)
                .orElseThrow(() -> new ResourceNotFoundException("Document not found"));
        Files.deleteIfExists(Paths.get(doc.getPath()));
        documentRepository.deleteById(documentId);
    }

    public List<Document> getDocumentsByProject(Long projectId) {
        return documentRepository.findByProjectId(projectId);
    }
}
