package com.dongdong.zoo.song.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Lyrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lyrics_id")
    private Long lyricsId;

    // 가사 한줄 내용
    private String content;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "song_id", insertable = false, updatable = false)
    private Long songId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "song_id")
    @JsonBackReference
    @Setter
    private Song song;


    //가사 키워드 리스트
    @OneToMany(mappedBy = "lyrics", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LyricsKeyword> lyricsKeywordList = new ArrayList<>();
}
