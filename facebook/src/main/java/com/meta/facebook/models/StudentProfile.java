package com.meta.facebook.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_profile")
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentProfile {

    @Id
    @GeneratedValue
    private Integer id;
    private String bio;

    @OneToOne
    @JoinColumn(
            name = "student_id"
    )
    private Student student;

}
