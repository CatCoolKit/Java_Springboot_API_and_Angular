package com.meta.facebook.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "school")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class School {

    @Id
    @GeneratedValue
    private Integer school_id;
    private String name;
    @OneToMany(
            mappedBy = "school",
            cascade = CascadeType.ALL
    )
    @JsonManagedReference
    private List<Student> students;

    public School(String name) {
        this.name = name;
    }
}
