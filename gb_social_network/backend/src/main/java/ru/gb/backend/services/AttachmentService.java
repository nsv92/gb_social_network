package ru.gb.backend.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.backend.entity.Attachment;
import ru.gb.backend.repositories.AttachmentRepository;
import ru.gb.backend.repositories.PostRepository;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@Log4j2
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private final PostRepository postRepository;

    private static final String UPLOADED_FOLDER = "gb_social_network/images/";

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository, PostRepository postRepository) {
        this.attachmentRepository = attachmentRepository;
        this.postRepository = postRepository;
    }

    public Resource getAttachmentAsResource(long id) {
        Attachment attachment = attachmentRepository.findById(id).get();
        Path fileStorageLocation = Paths.get(UPLOADED_FOLDER).toAbsolutePath().normalize();
        Path attachPath = fileStorageLocation.resolve(attachment.getStorageFileName() + attachment.getExtension()).normalize();
        try {
            return new UrlResource(attachPath.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public Attachment addAttachment(MultipartFile file, long postId) throws IOException {
        // Создаем директорию если ее не существует
        File uploadDir = new File(Paths.get(UPLOADED_FOLDER).toAbsolutePath().normalize().toUri());
        // Если директория uploads не существует, то создаем ее
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String fileName = UUID.randomUUID().toString();
        String extension = "." + file.getContentType().split("/", 2)[1];
        file.transferTo(new File(uploadDir + "/" + fileName + extension));
        Attachment attachment = new Attachment();
        attachment.setPost(postRepository.getReferenceById(postId));
        attachment.setStorageFileName(fileName);
        attachment.setExtension(extension);
        attachmentRepository.save(attachment);
        return attachment;
    }


    public boolean isAttachmentExist(long id) {
        return attachmentRepository.existsById(id);
    }

}
