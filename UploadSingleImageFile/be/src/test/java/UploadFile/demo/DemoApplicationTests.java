package UploadFile.demo;

import UploadFile.demo.Module.Document;
import UploadFile.demo.Repository.DocumentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DemoApplicationTests {

	@Autowired
	private DocumentRepository repo;

	@Autowired
	private TestEntityManager testEntityManager;

	// @Test
	// @Rollback(value = false)
	// void testInsertDocument() throws IOException {
	// 	File file = new File("D:\\Java_Springboot_API_and_Angular\\Documents\\filedocx.docx");

	// 	Document document = new Document();
	// 	document.setName(file.getName());

	// 	byte[] bytes = Files.readAllBytes(file.toPath());
	// 	document.setContent(bytes);
	// 	long fileSize = bytes.length;
	// 	document.setSize(fileSize);
	// 	document.setUploadTime(new Date());

	// 	Document savedDoc = repo.save(document);

	// 	Document existDoc = testEntityManager.find(Document.class, savedDoc.getId());
	// 	Assertions.assertThat(existDoc.getSize()).isEqualTo(fileSize);
	// }

}
