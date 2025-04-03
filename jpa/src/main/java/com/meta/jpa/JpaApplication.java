package com.meta.jpa;

import com.github.javafaker.Faker;
import com.meta.jpa.Repositories.AuthorRepository;
import com.meta.jpa.Repositories.FileRepository;
import com.meta.jpa.Repositories.VideoRepository;
import com.meta.jpa.models.Author;
import com.meta.jpa.models.File;
import com.meta.jpa.models.Video;
import com.meta.jpa.specification.AuthorSpecification;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

@SpringBootApplication
public class JpaApplication {

	public static void main(String[] args) {

		SpringApplication.run(JpaApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(
			AuthorRepository repository,
			VideoRepository videoRepository,
			FileRepository fileRepository
	){
		return args -> {
//			for (int i = 0; i < 50; i++) {
//				Faker faker = new Faker();
//				var author = Author.builder()
//						.firstName(faker.name().firstName())
//						.lastName(faker.name().lastName())
//						.age(faker.number().numberBetween(19,50))
//						.email("contact" + i + "john123@gmail.com")
//						.build();
//				repository.save(author);
//			}

			var author = Author.builder()
					.firstName("Ali")
					.lastName("Bou")
					.age(22)
					.email("contactjohn123@gmail.com")
					.build();
			//repository.updateAuthor(50,1);

			Specification<Author> spec = Specification
					.where(AuthorSpecification.hasAge(27)
							.and(AuthorSpecification.firstnameLike("Ba")));
			repository.findAll(spec).forEach(System.out::println);


//			var video = Video.builder()
//					.name("abc")
//					.length(30)
//					.build();
//			videoRepository.save(video);
		};
	}

}
