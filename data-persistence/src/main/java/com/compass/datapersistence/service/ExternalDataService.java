package com.compass.datapersistence.service;

import com.compass.datapersistence.dto.CommentDTO;
import com.compass.datapersistence.dto.PostDTO;
import com.compass.datapersistence.entity.Post;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExternalDataService {
    private WebClient webClient;

    public ExternalDataService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com").build();
    }

    public Mono<PostDTO> fetchPost(Long postId){
        return webClient.get()
                .uri("/posts/{postId}", postId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(PostDTO.class);
    }

    public Flux<CommentDTO> fetchComments(Long postId){
        return webClient.get()
                .uri("/posts/{postId}/comments", postId)
                .retrieve()
                .bodyToFlux(CommentDTO.class);
    }
}
