package com.dongdong.zoo.like.model;

import com.dongdong.zoo.story.model.Story;
import com.dongdong.zoo.user.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "story_likes_id"))
public class StoryLikes extends Like{

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "story_id")
    private Story story;

    @Builder
    protected StoryLikes(Story story, User user){
        this.story = story;
        super.user = user;
    }
}
