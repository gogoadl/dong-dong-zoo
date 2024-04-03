package com.dongdong.zoo.story.controller;

import com.dongdong.zoo.story.dto.StoryDetailResponse;
import com.dongdong.zoo.story.dto.StoryListResponse;
import com.dongdong.zoo.story.service.StoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stories")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Stories", description = "Story API")
public class StoryController {

    private final StoryService storyService;

    @GetMapping
    public ResponseEntity<List<StoryListResponse>> getStoryList(){
        return new ResponseEntity<>(storyService.getStoryList(), HttpStatus.OK);
    }

    @GetMapping("/{storyId}")
    public ResponseEntity<StoryDetailResponse> getStoryDetail(@PathVariable Long storyId){
        return new ResponseEntity<>(storyService.getStoryDetail(storyId), HttpStatus.OK);
    }

    @DeleteMapping("/{storyId}")
    public ResponseEntity<Void> deleteStory(@NotNull @PathVariable Long storyId){
        storyService.deleteStory(storyId);
        return ResponseEntity.ok().build();
    }
}
