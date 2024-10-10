package uz.developers.messenger.service;

import uz.developers.messenger.exceptions.PostException;
import uz.developers.messenger.exceptions.ResourceNotFoundException;
import uz.developers.messenger.payload.PostDto;

import java.util.List;
import java.util.Optional;

public interface PostService {

    //get all posts
    List<PostDto> getAllPosts();

    //get single post
    Optional<PostDto> getPostById(Long postId) throws ResourceNotFoundException;

    //get all posts by category
    List<PostDto> getPostsByCategory(Long categoryId);

    //get all posts by user
    List<PostDto> getPostsByUser(Long userId);

    //search posts
    List<PostDto> searchPosts(String keyword);

    //create
    PostDto createPost(PostDto postDto) throws PostException;

    //update
    PostDto updatePost(Long postId, PostDto postDto) throws ResourceNotFoundException;

    //delete
    void deletePost(Long postId) throws ResourceNotFoundException;




















}
