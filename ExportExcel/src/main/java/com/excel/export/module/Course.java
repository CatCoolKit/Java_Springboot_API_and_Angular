package com.excel.export.module;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "COURSE_DTLS")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Course {

    @Id
    private Integer id;
    private String name;
    private Double price;
}
