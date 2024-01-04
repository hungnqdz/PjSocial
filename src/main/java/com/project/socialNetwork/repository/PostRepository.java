package com.project.socialNetwork.repository;

import com.project.socialNetwork.model.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("Select p from Post as p where p.user.userId = :user_id")
    List<Post> findByUserId(Long user_id);
}
