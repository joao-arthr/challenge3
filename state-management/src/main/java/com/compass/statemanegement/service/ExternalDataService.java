package com.compass.statemanegement.service;

import com.compass.statemanegement.dto.CommentDTO;
import com.compass.statemanegement.dto.PostDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ExternalDataService {
    private WebClient webClient;

    public ExternalDataService(WebClient.Builder webClientBuilder){
        this.webClient = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com/").build();
    }

    public Mono<PostDTO> fetchPost(Long postId){
        return webClient.get()
                .uri("/posts/{postId}")
                .retrieve()
                .bodyToMono(PostDTO.class);
    }

    public Flux<CommentDTO> fetchComments(Long postId){
        return webClient.get()
                .uri("/posts/{postId}/comments")
                .retrieve()
                .bodyToFlux(CommentDTO.class);
    }
}
