package com.chat.app.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class User extends BaseEntity {
    @Column(name = "email")
    private String email;
    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;
}
