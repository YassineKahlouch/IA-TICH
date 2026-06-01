package com.company.aiassistant.repository;


import com.company.aiassistant.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository
        extends JpaRepository<Attachment, Long> {
}
