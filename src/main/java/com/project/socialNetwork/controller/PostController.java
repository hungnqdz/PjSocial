package com.project.socialNetwork.controller;

import com.project.socialNetwork.model.post.Post;
import com.project.socialNetwork.model.response.BaseResponse;
import com.project.socialNetwork.service.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostController {
    final PostService postService;

    @PostMapping("/create")
    ResponseEntity<BaseResponse> createPost(@RequestParam("content") String content,
                                            @RequestParam(value = "file",required = false) List<MultipartFile> file,
                                            @RequestHeader("host") String host,
                                            Principal currentAccount) {
        return postService.createPost(Post.builder().content(content).build(), currentAccount, file, host);
    }

    @GetMapping("/all")
    ResponseEntity<BaseResponse> getAllPosts() {
        return postService.getAllPost();
    }

}
