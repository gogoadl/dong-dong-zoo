package com.dongdong.zoo.song.repository;

import com.dongdong.zoo.song.model.Lyrics;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LyricsRepository extends JpaRepository<Lyrics, Long> {

    List<Lyrics> findBySongSongId(Long songId);

}
