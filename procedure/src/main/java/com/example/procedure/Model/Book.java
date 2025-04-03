package com.example.procedure.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@NamedStoredProcedureQuery(
        name = "getBookCountByAuthor",
        procedureName = "get_count_of_books",
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "auth", type = String.class),
                @StoredProcedureParameter(mode = ParameterMode.OUT, name = "total", type = Integer.class)
        }
)
public class Book {
    @Id
    private Integer id;
    private String author;
    private LocalDate yearPublish;
    private String category;
    private Integer cost;
}
