package uz.pdp.planboard.controller;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import uz.pdp.planboard.entity.Attachment;
import uz.pdp.planboard.entity.AttachmentContent;
import uz.pdp.planboard.repo.AttachmentContentRepository;
import uz.pdp.planboard.repo.AttachmentRepository;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {


    private final AttachmentContentRepository attachmentContentRepository;
    private final AttachmentRepository attachmentRepository;

    @GetMapping("/image/{id}")
    public void getFile(@PathVariable String id, HttpServletResponse response) throws IOException {
        int i = Integer.parseInt(id);

        AttachmentContent attachmentContent = attachmentContentRepository.findByAttachment_Id(i).orElseThrow();

        response.getOutputStream().write(attachmentContent.getFile());
    }

}

