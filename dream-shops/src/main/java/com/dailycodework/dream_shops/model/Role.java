package com.dailycodework.dream_shops.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Role(String name) {
        this.name = name;
    }

    @ManyToMany(mappedBy = "roles")
    private Collection<User> users = new HashSet<>();


}
