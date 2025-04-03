package com.meta.jpa.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "lectures")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
public class Lecture extends BaseEntity{

    private String name;

    @ManyToOne
    @JoinColumn(
            name = "section_id"
    )
    private  Section section;

    @OneToOne
    @JoinColumn(
            name = "resource_id"
    )
    private Resource resource;
}
