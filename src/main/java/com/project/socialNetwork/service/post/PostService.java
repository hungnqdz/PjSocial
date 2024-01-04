package com.project.socialNetwork.service.post;

import com.project.socialNetwork.model.post.Post;
import com.project.socialNetwork.model.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

public interface PostService {
    ResponseEntity<BaseResponse> createPost(Post post, Principal currentAccount, List<MultipartFile> file, String host);
    ResponseEntity<BaseResponse> getAllPost();
}
