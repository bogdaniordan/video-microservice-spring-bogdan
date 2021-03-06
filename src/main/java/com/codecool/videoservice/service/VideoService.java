package com.codecool.videoservice.service;

import com.codecool.videoservice.VO.VideoWithRecommendations;
import com.codecool.videoservice.VO.VideoRecommendation;
import com.codecool.videoservice.entity.Video;
import com.codecool.videoservice.repository.VideoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VideoService {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Video> getAllVideos() {
        log.info("Fetching all videos.");
        return videoRepository.findAll();
    }

    public VideoWithRecommendations getVideoWithRecommendations(Long id) {
        VideoWithRecommendations videoWithRecommendations = new VideoWithRecommendations();
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Could not find video with id: " + id));

        ResponseEntity<VideoRecommendation[]> response = restTemplate.getForEntity("http://VIDEO-RECOMMENDATION-SERVICE/video-recommendations/by-video-id/" + id, VideoRecommendation[].class);
        VideoRecommendation[] videoRecommendations = response.getBody();
        videoWithRecommendations.setVideo(video);
        assert videoRecommendations != null;
        videoWithRecommendations.setVideoRecommendations(List.of(videoRecommendations));
        return videoWithRecommendations;
    }

    public Video updateVideo(Video video, Long id) {
            Video updatedVideo = videoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Could not find video with id: "+ id));
            updatedVideo.setName(video.getName());
            updatedVideo.setUrl(video.getUrl());
            return videoRepository.save(updatedVideo);
    }

    public Video addVideo(Video video) {
        return videoRepository.save(video);
    }


    public VideoRecommendation addRecommendation(VideoRecommendation videoRecommendation, Long videoId) {
        restTemplate.postForObject("http://VIDEO-RECOMMENDATION-SERVICE/video-recommendations/save-by-video/" + videoId, videoRecommendation, VideoRecommendation.class);
        return videoRecommendation;
    }

    public List<VideoRecommendation> getRecommendationsByVideoId(Long videoId) {
        ResponseEntity<VideoRecommendation[]> response = restTemplate.getForEntity("http://VIDEO-RECOMMENDATION-SERVICE/video-recommendations/by-video-id/" + videoId, VideoRecommendation[].class);
        VideoRecommendation[] videoRecommendations = response.getBody();
        assert videoRecommendations != null;
        return List.of(videoRecommendations);
    }

    public Video getVideoById(Long id) {
        return videoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Couldn't find video with id: " + id));

    }

    public void updateVideoRecommendation(VideoRecommendation videoRecommendation, Long id) {
        restTemplate.put("http://VIDEO-RECOMMENDATION-SERVICE/video-recommendations/" + id, videoRecommendation, VideoRecommendation.class);
    }

    public VideoRecommendation getRecommendation(Long id) {
        return restTemplate.getForObject("http://VIDEO-RECOMMENDATION-SERVICE/video-recommendations/" + id, VideoRecommendation.class);
    }
}

