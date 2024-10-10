package uz.developers.messenger.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.developers.messenger.entity.Comment;
import uz.developers.messenger.entity.Post;
import uz.developers.messenger.exceptions.CommentException;
import uz.developers.messenger.exceptions.ResourceNotFoundException;
import uz.developers.messenger.payload.CommentDto;
import uz.developers.messenger.repository.CommentRepository;
import uz.developers.messenger.repository.PostRepository;
import uz.developers.messenger.service.CommentService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final ModelMapper modelMapper;

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    @Autowired
    public CommentServiceImpl(ModelMapper modelMapper, CommentRepository commentRepository, PostRepository postRepository) {
        this.modelMapper = modelMapper;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }


    @Override
    public List<CommentDto> getAllCommentsByPostId(Long postId) {
        // 1. Postni bazadan olish
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new ResourceNotFoundException("Post", "id", postId));

        // 2. Postga tegishli barcha kommentlarni olish
        List<Comment> comments = commentRepository.findByPostId(postId);

        // 3. Kommentlarni DTO ga o'zgartirish va qaytarish
        return comments.stream()
                .map(this::commentToDto)
                .collect(Collectors.toList());
    }


    @Override
    public Optional<CommentDto> getCommentById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        // Convert Comment entity to CommentDto
        CommentDto commentDto = commentToDto(comment);
        return Optional.ofNullable(commentDto);
    }

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        // 1. Find post by ID
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));

        // 2. Convert DTO to entity
        Comment comment = dtoToComment(commentDto);

        // 3. Save Post to Comment
             comment.setPost(post);

        // 4. Perform business checks on the entity
        if (comment.getContent() == null || comment.getContent().trim().isEmpty()) {
            throw new CommentException("Comment content must not be null or empty");
        }

        // 5. Save Comment
        Comment savedComment = commentRepository.save(comment);

        // 6. Convert the saved Comment to DTO and return
        return commentToDto(savedComment);
    }

    @Override
    public CommentDto updateComment(Long id, CommentDto commentDto) {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));

        // Use ModelMapper to map DTO to entity
        Comment commentDetails = dtoToComment(commentDto);

        // update employee details
        existingComment.setContent(commentDetails.getContent());

        // Save updated comment
        Comment updatedComment = commentRepository.save(existingComment);

        // Convert updated comment entity to DTO and return
        return commentToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        commentRepository.delete(comment);
    }

    // DTO ---> Entity
    private Comment dtoToComment(CommentDto commentDto){
        return modelMapper.map(commentDto, Comment.class);
    }

    // Entity ---> DTO
    public CommentDto commentToDto(Comment comment){
        return modelMapper.map(comment, CommentDto.class);
    }




}
