package com.meta.jpa.models;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
//@Polymorphism(type = PolymorphismType.EXPLICIT)
@PrimaryKeyJoinColumn(name = "video_id")
//@DiscriminatorValue("V")
public class Video extends Resource{

    private int length;
}
