package com.aaron.book.service;

import com.aaron.book.domain.Book;
import com.aaron.book.domain.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * 단위테스트 (Service와 관련된 애들만 IoC(메모리)에 띄우면 됨)
 * @ExtendWith(SpringExtension.class)
 * @ExtendWith(MockitoExtension.class)
 * 
 * BookRepository => 가짜 객체로 만들 수 있음
 */
@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {

    @InjectMocks // BookService 객체가 만들어질 때 해당 파일에 @Mock로 등록된 모든 애들을 주입받는다.
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;

    @Test
    public void 저장하기_테스트() {

        // BODMocikto 방식
        // given
        Book book = new Book();
        book.setTitle("책제목1");
        book.setAuthor("책저자1");

        // stub - 동작 지정
        when(bookRepository.save(book)).thenReturn(book);

        // test execute
        Book bookEntity = bookService.저장하기(book);

        // then
        assertEquals(bookEntity, book);

    }
}
