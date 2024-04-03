package com.dongdong.zoo.story.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
public class StoryImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storyImageId;

    private String storyImageUrl;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "story_id")
    @JsonManagedReference
    @Setter
    private Story story;
}
