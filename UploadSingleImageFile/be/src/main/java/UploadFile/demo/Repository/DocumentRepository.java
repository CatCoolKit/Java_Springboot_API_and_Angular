package UploadFile.demo.Repository;

import UploadFile.demo.Module.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT new Document(id, name, size) FROM Document d ORDER BY d.uploadTime DESC")
    List<Document> findAll();
}
