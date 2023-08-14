package com.compass.datapersistence.service;

import com.compass.datapersistence.entity.Comment;
import com.compass.datapersistence.entity.Post;
import com.compass.datapersistence.entity.PostState;
import com.compass.datapersistence.enums.Status;
import com.compass.datapersistence.repository.PostStateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PostStateService {
    private final PostStateRepository postStateRepository;
    private CommentService commentService;
    private PostService postService;

    public PostState createHistory(PostState postState) {
        return postStateRepository.save(postState);
    }


    public List<PostState> getAllHistoryByPostId(Long postId) {
        return postStateRepository.findByPostId(postId);
    }

    public void stateCreated(Long postId){
        var post = new Post();
        post.setId(postId);
        System.out.println(post);
        postService.createPost(post);
        postStateRepository.save(
                new PostState(postService.getPostById(postId), LocalDateTime.now(), Status.CREATED));
    }

    public void statePostFind(Long postId){
        postStateRepository.save(
                new PostState(postService.getPostById(postId), LocalDateTime.now(), Status.POST_FIND));
    }

    public void statePostOk(Post post){
        postService.createPost(post);
        postStateRepository.save(new PostState(post, LocalDateTime.now(), Status.POST_OK));
    }

    public void stateDisabled(Long postId) {
        postStateRepository.save(
                new PostState(postService.getPostById(postId), LocalDateTime.now(), Status.DISABLED));
    }

    public void stateFailure(Long postId) {
        postStateRepository.save(
                new PostState(postService.getPostById(postId), LocalDateTime.now(), Status.FAILED));
    }

    public void stateCommentsFind(Long postId, List<Comment> comments) {
        commentService.saveOrUpdateAllComments(comments);
        postStateRepository.save(
                new PostState(postService.getPostById(postId), LocalDateTime.now(), Status.COMMENTS_FIND));
    }

    public void stateCommentsOk(List<Comment> commentsList) {
        postStateRepository.save(
                new PostState(commentsList.get(0).getPost(), LocalDateTime.now(), Status.COMMENTS_OK));
    }

    public void stateEnabled(Long postId) {
        postStateRepository.save(
                new PostState(postService.getPostById(postId), LocalDateTime.now(), Status.ENABLED));
    }

    public void stateUpdating(Long postId) {
        postStateRepository.save(
                new PostState(postService.getPostById(postId), LocalDateTime.now(), Status.UPDATING));
    }

    public boolean isUpdatable(Post post){
        if(getLastStatus(post.getHistory()) == Status.DISABLED || getLastStatus(post.getHistory()) == Status.ENABLED){
            return true;
        }
        return false;
    }

    public boolean canBeDisabled(Post post){
        return getLastStatus(post.getHistory()) == Status.ENABLED;
    }

    public Status getLastStatus(List<PostState> history) {
        return history.stream()
                .map(PostState::getStatus)
                .reduce((first, second) -> second)
                .orElse(null);
    }

}
