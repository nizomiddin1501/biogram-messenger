package uz.developers.messenger.service;

import uz.developers.messenger.exceptions.CommentException;
import uz.developers.messenger.exceptions.ResourceNotFoundException;
import uz.developers.messenger.payload.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {



    // get all
    List<CommentDto> getAllCommentsByPostId(Long postId);

    // get by ID
    Optional<CommentDto> getCommentById(Long commentId) throws ResourceNotFoundException;

    // create
    CommentDto createComment(Long postId, CommentDto commentDto) throws CommentException;

    // update
    CommentDto updateComment(Long commentId, CommentDto commentDto) throws ResourceNotFoundException;

    // delete
    void deleteComment(Long commentId) throws ResourceNotFoundException;







}
