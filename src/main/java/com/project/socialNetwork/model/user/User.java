package com.project.socialNetwork.model.user;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "dob")
    private Date dob;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "user_avatar")
    private String userAvatar;

    @Column(name = "gender")
    private String gender;


    public User(String userName, Date dob, String address, String phone, String userAvatar, String gender) {
        this.userName = userName;
        this.dob = dob;
        this.address = address;
        this.phone = phone;
        this.userAvatar = userAvatar;
        this.gender = gender;
    }


}
