package com.ex01.basic.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="member_test")
@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column( unique = true, nullable = false )
    private String username;
    @Column( nullable = false )
    private String password;
    @Column( nullable = false )
    private String role;
}

