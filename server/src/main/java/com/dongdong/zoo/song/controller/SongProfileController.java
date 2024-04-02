package com.dongdong.zoo.song.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.dongdong.zoo.song.dto.SongListResponse;
import com.dongdong.zoo.song.repository.SongRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SongProfileController {

	private final SongRepository songRepository;

	@GetMapping("profiles/songlike/{userId}")
	public List<SongListResponse> getSongLikeList(@PathVariable Long userId) {

		return songRepository.findSongsByLikes(userId).stream()
			.map(result -> SongListResponse.builder()
				.songId(result.getSongId())
				.songName(result.getSongName())
				.songImageUrl(result.getSongImageUrl())
				.views(result.getViews())
				.likeCount(result.getLikeCount())
				.build())
			.collect(Collectors.toList());
	}
}
