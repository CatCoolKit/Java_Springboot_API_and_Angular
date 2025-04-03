package com.meta.facebook.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue
    private Integer student_id;
    @Column(name = "c_fname",
    length = 20)
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private int age;

    @OneToOne(
            mappedBy = "student",
            cascade = CascadeType.ALL
    )
    private StudentProfile studentProfile;

    @ManyToOne
    @JoinColumn(
            name = "school_id"
    )
    @JsonBackReference
    private School school;

    public Student(String firstName, String lastName, String email, int age) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.age = age;
    }
}
