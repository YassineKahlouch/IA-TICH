package com.company.aiassistant.service;

import com.company.aiassistant.entity.Attachment;
import com.company.aiassistant.entity.Message;
import com.company.aiassistant.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public Attachment saveAttachment(
            Message message,
            String fileName,
            String fileType,
            String fileUrl
    ) {

        Attachment attachment = Attachment.builder()
                .message(message)
                .fileName(fileName)
                .fileType(fileType)
                .fileUrl(fileUrl)
                .build();

        return attachmentRepository.save(attachment);
    }

}
