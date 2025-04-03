package com.dailycodework.dream_shops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;
    @Lob
    @JsonIgnore
    private Blob image;
    private String downloadUrl;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "product_id")
    private Product product;
}
