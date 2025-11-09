package com.project.storage.service;


import com.project.storage.entity.Media;
import com.project.storage.entity.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MediaService {
    private final  MediaRepository mediaRepository;
    public MediaService( MediaRepository mediaRepository1) {
        this.mediaRepository = mediaRepository1;
    }
    public List<Media> getMedias()   {
        return mediaRepository.findAll();
    }
}
