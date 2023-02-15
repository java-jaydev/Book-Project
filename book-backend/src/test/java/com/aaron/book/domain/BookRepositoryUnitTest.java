package com.aaron.book.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

// 단위테스트 (DB관련된 Bean들이 IoC에 등록되면 됨)
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // Replace.ANY : 가짜 DB로 테스트, Replace.NONE : 실제 DB로 테스트
@DataJpaTest // Repository 들을 다 IoC에 등록해줌
public class BookRepositoryUnitTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void save_테스트() {
        // given
        Book book = new Book(null, "책제목1", "책저자1");

        // when
        Book bookEntity = bookRepository.save(book);

        // then
        assertEquals("책제목1", bookEntity.getTitle());
    }
}
