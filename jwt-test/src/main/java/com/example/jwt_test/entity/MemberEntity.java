package com.example.jwt_test.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="member_test")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( unique = true, nullable = false)
    private String username;
    @Column( nullable = false)
    private String password;
    @Column( nullable = false )
    private String role;
    @PrePersist //role null일 경우 초기화 신규 *
    public void prePersist(){
        if( this.role == null)
            this.role = "USER";
    }
}
