package com.project.socialNetwork.model.post;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.socialNetwork.model.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long post_id;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at")
    private String create_at;

    @Column(name = "updated_at")
    private String updated_at;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    private List<ImagePost> imagePosts;
}
