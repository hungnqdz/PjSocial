package com.project.socialNetwork.model.token;

import com.project.socialNetwork.model.account.Account;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "token")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "token",unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType = TokenType.BEARER;


    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "account_id",referencedColumnName = "account_id")
    private Account account;

}
