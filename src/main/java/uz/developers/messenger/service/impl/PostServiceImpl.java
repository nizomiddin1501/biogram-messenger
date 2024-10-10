package uz.developers.messenger.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.developers.messenger.entity.Post;
import uz.developers.messenger.exceptions.PostException;
import uz.developers.messenger.exceptions.ResourceNotFoundException;
import uz.developers.messenger.payload.PostDto;
import uz.developers.messenger.repository.CategoryRepository;
import uz.developers.messenger.repository.PostRepository;
import uz.developers.messenger.repository.UserRepository;
import uz.developers.messenger.service.PostService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public PostServiceImpl(ModelMapper modelMapper, PostRepository postRepository, UserRepository userRepository, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }






    //get all posts by category
    @Override
    public List<PostDto> getPostsByCategory(Long categoryId) {
        List<Post> posts = postRepository.findByCategoryId(categoryId);
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("Posts", "Category ID", categoryId);
        }
        // Post -> PostDto conversion
        return posts.stream()
                .map(this::postToDto)
                .collect(Collectors.toList());
    }


    //get all posts by user
    @Override
    public List<PostDto> getPostsByUser(Long userId) {
        List<Post> posts = postRepository.findByUserId(userId);
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("Posts", "User ID", userId);
        }
        // Post -> PostDto conversion
        return posts.stream()
                .map(this::postToDto)
                .collect(Collectors.toList());
    }

    //search posts
    @Override
    public List<PostDto> searchPosts(String keyword) {
        List<Post> posts = postRepository.searchByTitleOrContent(keyword);
        if (posts.isEmpty()) {
            throw new ResourceNotFoundException("Posts", "Keyword", keyword.hashCode());
        }
        // Post -> PostDto conversion
        return posts.stream()
                .map(this::postToDto)
                .collect(Collectors.toList());
    }

    //crud

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream()
                .map(this::postToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PostDto> getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // Convert Post entity to PostDto
        PostDto postDto = postToDto(post);
        return Optional.ofNullable(postDto);
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        // 1. Convert DTO to entity
        Post post = dtoToPost(postDto);

        // 2. Perform business checks on the entity
        if (post.getTitle() == null || post.getContent() == null) {
            throw new PostException("Post title content must not be null");
        }

        // 3. Checking that the title and content columns do not exist
        boolean exists = postRepository.existsByTitleOrContent(post.getTitle(), post.getContent());
        if (exists) {
            throw new PostException("Post with this title name and content already exists");
        }

        // 4. Save Post
        Post savedPost = postRepository.save(post);

        // 4. Convert the saved Post to DTO and return
        return postToDto(savedPost);
    }

    @Override
    public PostDto updatePost(Long id, PostDto postDto) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));

        // Use ModelMapper to map DTO to entity
        Post postDetails = dtoToPost(postDto);

        existingPost.setTitle(postDetails.getTitle());
        existingPost.setContent(postDetails.getContent());
        existingPost.setImage(postDetails.getImage());
        existingPost.setDate(postDetails.getDate());

        // Save updated post
        Post updatedPost = postRepository.save(existingPost);

        // Convert updated post entity to DTO and return
        return postToDto(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
        postRepository.delete(post);
    }





    // DTO ---> Entity
    private Post dtoToPost(PostDto postDto){
        return modelMapper.map(postDto, Post.class);
    }


    // Entity ---> DTO
    public PostDto postToDto(Post post){
        return modelMapper.map(post, PostDto.class);
    }







}
