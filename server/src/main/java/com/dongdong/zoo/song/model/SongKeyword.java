package com.dongdong.zoo.song.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class SongKeyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_keyword_id")
    private Long songKeywordId;

    @Column(name = "song_keyword")
    private String songKeyword;

    @Column(name = "song_keyword_korean")
    private String songKeywordKorean;

    @Column(name = "song_id", insertable = false, updatable = false)
    private Long songId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    @JsonManagedReference
    @Setter
    private Song song;
}
