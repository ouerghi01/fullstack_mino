package com.project.storage.Controller;


import com.project.storage.dto.MediaDto;
import com.project.storage.service.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@AllArgsConstructor
@RestController
@RequestMapping("/api/medias")
public class MediaController {
    private  final MediaService mediaService;
    @GetMapping("")
    public ResponseEntity<List<MediaDto>> getMedias() {
        List<MediaDto> urls = mediaService.getMedias()
                .stream()
                .map(m -> new MediaDto(m.getId(), m.getUrlFile()))
                .toList();
        return ResponseEntity.ok(urls);
    }

}
