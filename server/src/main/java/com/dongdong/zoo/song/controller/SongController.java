package com.dongdong.zoo.song.controller;

import com.dongdong.zoo.song.dto.SongDetailResponse;
import com.dongdong.zoo.song.dto.SongListResponse;
import com.dongdong.zoo.song.dto.StoryImageResponse;
import com.dongdong.zoo.song.model.Lyrics;
import com.dongdong.zoo.song.service.SongService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/songs")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Songs", description = "Song API")
public class SongController {

    private final SongService songService;
 
    // 좋아요, 조회수 내림차순으로 인기 동요 조회
    @GetMapping
    public ResponseEntity<List<SongListResponse>> getSongsByLikesAndViews(){
        return new ResponseEntity<>(songService.getSongsByLikesAndViews(), HttpStatus.OK);
    }

    // 동요 상세 조회
    @GetMapping("/{songId}")
    public ResponseEntity<SongDetailResponse> getSongBySongId(@PathVariable Long songId, HttpServletRequest req, HttpServletResponse res){
        viewCountUp(songId, req, res);
        return new ResponseEntity<>(songService.getSongBySongId(songId), HttpStatus.OK);
    }

    @GetMapping("/create/{songId}")
    public ResponseEntity<List<StoryImageResponse>>getStoryImages(@PathVariable Long songId, @NotNull String tag) {
        return new ResponseEntity<>(songService.getStoryImages(songId, tag), HttpStatus.OK);
    }

    private void viewCountUp(Long id, HttpServletRequest req, HttpServletResponse res) {

        Cookie oldCookie = null;

        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("boardView")) {
                    oldCookie = cookie;
                }
            }
        }

        if (oldCookie != null) {
            if (!oldCookie.getValue().contains("[" + id.toString() + "]")) {
                songService.viewCountUp(id);
                oldCookie.setValue(oldCookie.getValue() + "_[" + id + "]");
                oldCookie.setPath("/");
                oldCookie.setMaxAge(60 * 60 * 24);
                res.addCookie(oldCookie);
            }
        } else {
            songService.viewCountUp(id);
            Cookie newCookie = new Cookie("boardView","[" + id + "]");
            newCookie.setPath("/");
            newCookie.setMaxAge(60 * 60 * 24);
            res.addCookie(newCookie);
        }
    }

}
