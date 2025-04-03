package UploadFile.demo.Module;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "brand")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "brand_name", 
            nullable = false, 
            length = 50,
            unique = true)
    private String name;

    @Column(name = "extra_image1")
    private String extraImage1;

    @Column(name = "extra_image2")
    private String extraImage2;

    @Column(name = "extra_image3")
    private String extraImage3;

    @Column(nullable = true, length = 50)
    private String logo;
}
