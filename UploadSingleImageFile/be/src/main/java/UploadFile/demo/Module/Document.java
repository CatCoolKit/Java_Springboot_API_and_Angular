package UploadFile.demo.Module;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "documents")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 512, nullable = false, unique = true)
    private String name;
    private long size;
    @Column(name = "upload_time")
    private Date uploadTime;

    @Lob
    @Column(name = "content", columnDefinition = "MEDIUMBLOB")
    private byte[] content;

    public Document(Long id, String name, long size) {
        this.id = id;
        this.name = name;
        this.size = size;
    }
}
