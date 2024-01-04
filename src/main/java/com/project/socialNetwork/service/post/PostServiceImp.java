package com.project.socialNetwork.service.post;

import com.project.socialNetwork.model.account.Account;
import com.project.socialNetwork.model.post.ImagePost;
import com.project.socialNetwork.model.post.Post;
import com.project.socialNetwork.model.response.BaseResponse;
import com.project.socialNetwork.repository.ImageRepository;
import com.project.socialNetwork.repository.PostRepository;
import com.project.socialNetwork.service.file.ImageService;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImp implements PostService {
    private final PostRepository postRepository;
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final String baseImagePath = "/api/v1/images/";

    @Override
    public ResponseEntity<BaseResponse> createPost(Post post, Principal currentAccount, List<MultipartFile> file, String host) {
        Account account = (Account) ((UsernamePasswordAuthenticationToken) currentAccount).getPrincipal();
        post.setUser(account.getUser());
        post.setCreate_at(LocalDateTime.now().toString());
        post.setUpdated_at(LocalDateTime.now().toString());
        Post newPost = postRepository.save(post);
        List<ImagePost> listImage = new ArrayList<>();
        if (file != null) {
            try {
                if (file.size() > 5) {
                    return ResponseEntity.badRequest().body(BaseResponse.builder().statusCode(400)
                            .message("The number of images must be equal or less than 5").build());
                }
                for (MultipartFile f : file) {
                    if (f.isEmpty()) {
                        return ResponseEntity.badRequest().body(BaseResponse.builder().statusCode(400)
                                .message("Cannot read empty file")
                                .data("")
                                .build());
                    }
                    listImage.add(imageRepository.save(ImagePost.builder()
                            .url("http://" + host + baseImagePath + imageService.storeFile(f))
                            .post(newPost)
                            .build()));
                }
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(BaseResponse.builder()
                        .statusCode(501)
                        .message("Failed to upload image")
                        .data(e.getMessage())
                        .build());
            }
        }
        newPost.setImagePosts(listImage);
        return ResponseEntity.ok(BaseResponse.builder().statusCode(200).message("Create post successfully.")
                .data(newPost).build());
    }

    @Override
    public ResponseEntity<BaseResponse> getAllPost() {
        List<Post> postList = postRepository.findAll();
        Collections.sort(postList, (o1, o2) -> {
            return (int) (o2.getPost_id() - o1.getPost_id());
        });
        return ResponseEntity.ok(BaseResponse.builder()
                .statusCode(200)
                .message("Success")
                .data(postList)
                .build());
    }
}
