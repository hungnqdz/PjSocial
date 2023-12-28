package com.project.socialNetwork.repository;

import com.project.socialNetwork.model.post.ImagePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImagePost,Long> {
    @Query(value = "Select img from ImagePost as img where img.post.post_id = :post_id",nativeQuery = true)
    List<ImagePost> findByPostId(Long post_id);

}
