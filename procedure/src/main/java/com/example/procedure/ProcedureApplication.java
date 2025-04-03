package com.example.procedure;

import com.example.procedure.Model.Book;
import com.example.procedure.Repository.BookRepository;
import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
@EnableCaching
public class ProcedureApplication {
	private final BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(ProcedureApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner run() {
//		return args -> {
//			List<Book> books = generateBooks(10000);
//			books.forEach(System.out::println);
//			bookRepository.saveAll(books);
//		};
//	}

	public static List<Book> generateBooks(int count) {
		List<Book> books = new ArrayList<>();
		Faker faker = new Faker();

		for (int i = 1; i <= count; i++) {
			Book book = new Book();
			book.setId(i);
			book.setAuthor(faker.book().author());
			book.setYearPublish(LocalDate.of(
					faker.number().numberBetween(1900, 2023), // Năm từ 1900-2023
					faker.number().numberBetween(1, 12),     // Tháng 1-12
					faker.number().numberBetween(1, 28)      // Ngày 1-28 để tránh lỗi tháng 2
			));
			book.setCategory(faker.book().genre());
			book.setCost(faker.number().numberBetween(100, 5000)); // Giá từ 100-5000

			books.add(book);
		}

		return books;
	}

}
