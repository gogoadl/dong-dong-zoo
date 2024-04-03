package com.dongdong.zoo.like.model;

import com.dongdong.zoo.song.model.Song;
import com.dongdong.zoo.user.model.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AttributeOverride(name = "id", column = @Column(name = "song_likes_id"))
public class SongLikes extends Like{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    @JsonManagedReference
    private Song song;

    @Builder
    protected SongLikes(Song song, User user){
        this.song = song;
        super.user = user;
    }
}
