package com.dongdong.zoo.like.controller;

import com.dongdong.zoo.like.dto.LikeRequest;
import com.dongdong.zoo.like.service.SongLikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/likes/song")
@CrossOrigin(origins = "*",allowedHeaders = "*")
@Tag(name = "SongLike", description = "SongLike API")
public class SongLikeController extends LikeController<SongLikeService> {

    public SongLikeController(SongLikeService songLikeService) {
        super(songLikeService);
    }
    @Override
    public ResponseEntity<URI> createLike(@Valid @RequestBody LikeRequest likeRequest){
        if (likeRequest.checkSongId(likeRequest)){
            System.out.println("likeRequest - songId : " + likeRequest.getSongId() + " userId : " + likeRequest.getUserId());
            likeService.createLike(likeRequest);
        }
        return ResponseEntity.ok().build();
    }
}
