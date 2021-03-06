package com.codecool.videoservice.controller;

import com.codecool.videoservice.VO.VideoRecommendation;
import com.codecool.videoservice.VO.VideoWithRecommendations;
import com.codecool.videoservice.entity.Video;
import com.codecool.videoservice.service.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/videos")
@Slf4j
@CrossOrigin(origins = "*")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("")
    public ResponseEntity<List<Video>> getVideos() {
        return new ResponseEntity<>(videoService.getAllVideos(), HttpStatus.OK);
    }

    @GetMapping("/video-by-id/{id}")
    public ResponseEntity<Video> getVideoById(@PathVariable Long id) {
        Video video = videoService.getVideoById(id);
        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoWithRecommendations> getVideoWithRecommendations(@PathVariable Long id) {
        log.info("Inside getVideoWithRecommendations of VideoController.");
        return new ResponseEntity<>(videoService.getVideoWithRecommendations(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Video> updateVideo(@RequestBody Video video, @PathVariable Long id) {
        log.info("Updating recommendations of video and video with id:" + video.getId());
        Video updatedVideo = videoService.updateVideo(video, id);
        return new ResponseEntity<>(updatedVideo, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Video> addVideo(@RequestBody Video video) {
        log.info("Saving video...");
        Video newVideo = videoService.addVideo(video);
        return new ResponseEntity<>(newVideo, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/add-recommendation/{videoId}")
    public ResponseEntity<VideoRecommendation> addRecommendation(@RequestBody VideoRecommendation videoRecommendation,
                                                                 @PathVariable Long videoId) {
        VideoRecommendation newRecommendation = videoService.addRecommendation(videoRecommendation, videoId);
        return new ResponseEntity<>(newRecommendation, HttpStatus.CREATED);
    }

    @GetMapping("/all-recommendations/{videoId}")
    public ResponseEntity<List<VideoRecommendation>> getAllRecommendationsForVideo(@PathVariable Long videoId) {
        log.info("Fetching all video recommendations for video with videoId: " + videoId);
        List<VideoRecommendation> recommendationList = videoService.getRecommendationsByVideoId(videoId);
        return new ResponseEntity<>(recommendationList, HttpStatus.OK);
    }

    @PutMapping("/update-recommendation/{id}")
    public ResponseEntity<VideoRecommendation> updateRecommendation(@RequestBody VideoRecommendation videoRecommendation, @PathVariable Long id) {
        log.info("Updating recommendation with id: " + id);
        videoService.updateVideoRecommendation(videoRecommendation, id);
        return new ResponseEntity<>(videoRecommendation, HttpStatus.OK);
    }

    @GetMapping("/recommendation-by-id/{id}")
    public ResponseEntity<VideoRecommendation> getRecommendationById(@PathVariable Long id) {
        log.info("Getting recommendation with id: " + id);
        VideoRecommendation videoRecommendation = videoService.getRecommendation(id);
        return new ResponseEntity<>(videoRecommendation, HttpStatus.OK);
    }

}
