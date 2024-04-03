package com.dongdong.zoo.like.controller;

import com.dongdong.zoo.like.dto.LikeRequest;
import com.dongdong.zoo.like.service.LikeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.http.HttpResponse;

@CrossOrigin(origins = "*", allowedHeaders = "*")
public abstract class LikeController<T extends LikeService<?, ?>> {

    protected final T likeService;

    public LikeController(T likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public abstract ResponseEntity<URI> createLike(@Valid @RequestBody LikeRequest likeRequest);

    @DeleteMapping("/{id}/{userId}")
    public ResponseEntity<Void> deleteLike(@NotNull @PathVariable Long id, @NotNull @PathVariable Long userId) {
        likeService.deleteLike(id, userId);
//        return ResponseEntity.noContent().build();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/{userId}")
    public ResponseEntity<Boolean> getLikeByUser(@NotNull @PathVariable Long id, @NotNull @PathVariable Long userId){
        boolean doesUserLike = likeService.checkUserLike(id, userId);
        return ResponseEntity.ok(doesUserLike);
    }
}
