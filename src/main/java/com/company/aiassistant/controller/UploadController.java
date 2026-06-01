package com.company.aiassistant.controller;

import com.company.aiassistant.entity.Attachment;
import com.company.aiassistant.entity.Message;
import com.company.aiassistant.repository.MessageRepository;
import com.company.aiassistant.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/upload")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UploadController {

    private final AttachmentService attachmentService;
    private final MessageRepository messageRepository;

    @PostMapping
    public Attachment uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("messageId") Long messageId
    ) throws Exception {

        String uploadDir = "uploads/";

        File dir = new File(uploadDir);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        String fileName =
                System.currentTimeMillis()
                        + "_"
                        + file.getOriginalFilename();

        Path filePath = Paths.get(
                uploadDir,
                fileName
        );

        Files.copy(
                file.getInputStream(),
                filePath
        );

        Message message =
                messageRepository.findById(messageId)
                        .orElseThrow();

        return attachmentService.saveAttachment(
                message,
                fileName,
                file.getContentType(),
                filePath.toString()
        );
    }
}