package com.meta.jpa.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "authors")
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@NamedQueries(
        {
                @NamedQuery(
                        name = "Author.findByNamedQuery",
                        query = "select a from Author a where a.age >= :age"
                ),
                @NamedQuery(
                        name = "Author.UpdateByNamedQuery",
                        query = "update Author a set a.age = :age"
                )
        }
)
public class Author extends BaseEntity{

//    @Id
//    @GeneratedValue
//            (
//            strategy = GenerationType.TABLE,
//            generator = "author_id_gen"
//    )
//    @SequenceGenerator(
//            name = "author_sequence",
//            sequenceName = "author_sequence",
//            allocationSize = 1
//    )
//    @TableGenerator(
//            name = "author_id_gen",
//            table = "id_generator",
//            pkColumnName = "id_name",
//            valueColumnName = "id_value",
//            allocationSize = 1
//    )
//    private Integer id;

    @Column(
            name = "f_name",
            length = 35
    )
    private String firstName;

    @Column(
            name = "l_name",
            length = 35
    )
    private String lastName;

    @Column(
            unique = true,
            nullable = false
    )
    private String email;

    private int age;

    @ManyToMany(
            mappedBy = "authors"
    )
    private List<Course> courses;

//    @Column(
//            updatable = false,
//            nullable = false
//    )
//    private LocalDateTime createAt;
//
//    @Column(
//            insertable = false,
//            nullable = false
//    )
//    private LocalDateTime lastModified;
}
