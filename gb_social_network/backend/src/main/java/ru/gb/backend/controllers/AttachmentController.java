package ru.gb.backend.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.gb.backend.entity.Attachment;
import ru.gb.backend.services.AttachmentService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
@RequestMapping("/api/v1/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }


    @GetMapping("/{attachmentId}")
    public ResponseEntity<Resource> getAttachment(@PathVariable("attachmentId") long attachmentId,
                                                  HttpServletRequest request) {
        Resource resource = attachmentService.getAttachmentAsResource(attachmentId);
        String contentType;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            log.error("File not found", e);
            throw new RuntimeException(e);
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping(value = "/add", produces = "application/json")
    @ResponseBody
    public ResponseEntity<Map<String, String>> uploadAttachment(
            @RequestPart MultipartFile file,
            @RequestParam("postId") long postId)
            throws IOException {
        Attachment attachment = attachmentService.addAttachment(file, postId);
        Map<String, String> attachmentStatus = new HashMap<>();
        attachmentStatus.put("status", "ok");
        attachmentStatus.put("attachId", attachment.getId().toString());
        return ResponseEntity.ok(attachmentStatus);
    }


}
