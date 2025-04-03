package com.meta.jpa.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "resources")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Inheritance(
        strategy = InheritanceType.JOINED
)
//@DiscriminatorColumn(
//        name = "resource_type"
//) ---> only with SINGLE_TABLE
public class Resource {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private int size;

    private String url;

    @OneToOne
    @JoinColumn(
            name = "lecture_id"
    )
    private Lecture lecture;
}
