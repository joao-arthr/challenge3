package com.compass.statemanegement.service;

import com.compass.statemanegement.dto.CommentDTO;
import com.compass.statemanegement.dto.PostDTO;
import com.compass.statemanegement.dto.PostStateDTO;
import com.compass.statemanegement.enums.PostState;
import com.compass.statemanegement.feign.RemoteServiceClient;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostStateService {
    private ExternalDataService externalDataService;

    private RemoteServiceClient remoteServiceClient;

    public void stateCreated(Long postId){
        var post = PostDTO.createdConstructor(postId);
        post.addStateToHistory( new PostStateDTO(postId, LocalDateTime.now(), PostState.CREATED));
        remoteServiceClient.createPost(post);
    }

    public void statePostFind(Long postId){
        remoteServiceClient.createHistory(new PostStateDTO(postId, LocalDateTime.now(), PostState.POST_FIND));
    }

    public void statePostOk(PostDTO postDTO){
        remoteServiceClient.createPost(postDTO);
        remoteServiceClient.createHistory(new PostStateDTO(postDTO.id(), LocalDateTime.now(), PostState.POST_OK));
    }

    public void stateDisabled(Long postId) {
        remoteServiceClient.createHistory(new PostStateDTO(postId, LocalDateTime.now(), PostState.DISABLED));
    }

    public void stateFailure(Long postId) {
        remoteServiceClient.createHistory(new PostStateDTO(postId, LocalDateTime.now(), PostState.FAILED));
    }

    public void stateCommentsFind(Long postId, List<CommentDTO> commentsDTO) {
        remoteServiceClient.createComments(commentsDTO);
        remoteServiceClient.createHistory(new PostStateDTO(postId, LocalDateTime.now(), PostState.COMMENTS_FIND));
    }

    public void stateCommentsOk(List<CommentDTO> commentsList) {
        remoteServiceClient.createHistory(
                new PostStateDTO(commentsList.get(0).id(), LocalDateTime.now(), PostState.COMMENTS_OK));
    }

    public void stateEnabled(Long postId) {
        remoteServiceClient.createHistory(new PostStateDTO(postId, LocalDateTime.now(), PostState.ENABLED));
    }

    public void stateUpdating(Long postId) {
        remoteServiceClient.createHistory(new PostStateDTO(postId, LocalDateTime.now(), PostState.UPDATING));
    }

    public boolean isUpdatable(PostDTO post){
        if(getLastStatus(post.history()) == PostState.DISABLED || getLastStatus(post.history()) == PostState.ENABLED){
            return true;
        }
        return false;
    }

    public boolean canBeDisabled(PostDTO post){
        return getLastStatus(post.history()) == PostState.ENABLED;
    }

    public PostState getLastStatus(List<PostStateDTO> history) {
        return history.stream()
                .map(PostStateDTO::status)
                .reduce((first, second) -> second)
                .orElse(null);
    }
}
