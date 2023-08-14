package com.compass.statemanegement.service;

import com.compass.statemanegement.dto.CommentDTO;
import com.compass.statemanegement.dto.PostDTO;
import com.compass.statemanegement.dto.PostStateDTO;
import com.compass.statemanegement.enums.PostState;
import com.compass.statemanegement.feign.RemoteServiceClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PostStateService {
    private ExternalDataService externalDataService;

    private RemoteServiceClient remoteServiceClient;

    public void stateCreated(Long postId){
        var post = new PostDTO();
        post.setId(postId);
        System.out.println(post);
        remoteServiceClient.createPost(post);
        remoteServiceClient.createHistory(
                new PostStateDTO(remoteServiceClient.getPostById(postId), LocalDateTime.now(), PostState.CREATED));
    }

    public void statePostFind(Long postId){
        remoteServiceClient.createHistory(
                new PostStateDTO(remoteServiceClient.getPostById(postId), LocalDateTime.now(), PostState.POST_FIND));
    }

    public void statePostOk(PostDTO postDTO){
        remoteServiceClient.createPost(postDTO);
        remoteServiceClient.createHistory(new PostStateDTO(postDTO, LocalDateTime.now(), PostState.POST_OK));
    }

    public void stateDisabled(Long postId) {
        remoteServiceClient.createHistory(
                new PostStateDTO(remoteServiceClient.getPostById(postId), LocalDateTime.now(), PostState.DISABLED));
    }

    public void stateFailure(Long postId) {
        remoteServiceClient.createHistory(
                new PostStateDTO(remoteServiceClient.getPostById(postId), LocalDateTime.now(), PostState.FAILED));
    }

    public void stateCommentsFind(Long postId, List<CommentDTO> commentsDTO) {
        remoteServiceClient.createComments(commentsDTO);
        remoteServiceClient.createHistory(
                new PostStateDTO(remoteServiceClient.getPostById(postId), LocalDateTime.now(), PostState.COMMENTS_FIND));
    }

    public void stateCommentsOk(List<CommentDTO> commentsList) {
        remoteServiceClient.createHistory(
                new PostStateDTO(commentsList.get(0).getPost(), LocalDateTime.now(), PostState.COMMENTS_OK));
    }

    public void stateEnabled(Long postId) {
        remoteServiceClient.createHistory(
                new PostStateDTO(remoteServiceClient.getPostById(postId), LocalDateTime.now(), PostState.ENABLED));
    }

    public void stateUpdating(Long postId) {
        remoteServiceClient.createHistory(
                new PostStateDTO(remoteServiceClient.getPostById(postId), LocalDateTime.now(), PostState.UPDATING));
    }

    public boolean isUpdatable(PostDTO post){
        if(getLastStatus(post.getHistory()) == PostState.DISABLED || getLastStatus(post.getHistory()) == PostState.ENABLED){
            return true;
        }
        return false;
    }

    public boolean canBeDisabled(PostDTO post){
        return getLastStatus(post.getHistory()) == PostState.ENABLED;
    }

    public PostState getLastStatus(List<PostStateDTO> history) {
        return history.stream()
                .map(PostStateDTO::getStatus)
                .reduce((first, second) -> second)
                .orElse(null);
    }
}
