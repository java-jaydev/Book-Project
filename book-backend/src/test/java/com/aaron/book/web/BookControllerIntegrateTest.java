package com.aaron.book.web;

import com.aaron.book.domain.Book;
import com.aaron.book.domain.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 통합테스트 (모든 Bean들을 똑같이 IoC에 올리고 테스트 하는 것)
 * MOCK : 실제 톰캣을 올리는게 아니라, 다른 톰캣으로 테스트
 * RANDOM_PORT : 실제 톰캣을 사용하는 것
 * @AutoConfigureMockMvc : MockMvc를 IoC에 등록해줌
 * @Transactional : 각각의 테스트 함수가 종료될 때마다 트랜잭션을 rollback 해주는 어노테이션
 */

@Slf4j
@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class BookControllerIntegrateTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init() {

//        List<Book> books = new ArrayList<>();
//        books.add(new Book(null, "스프링부트 따라하기", "Aaron"));
//        books.add(new Book(null, "리액트 따라하기", "Aaron"));
//        books.add(new Book(null, "JUnit 따라하기", "Aaron"));
//        bookRepository.saveAll(books);

        // H2
//        entityManager.createNativeQuery("ALTER TABLE book ALTER COLUMN id RESTART WITH 1").executeUpdate();

        // MySQL
        entityManager.createNativeQuery("ALTER TABLE Book AUTO_INCREMENT = 1").executeUpdate();
    }

    @AfterEach
    public void end() {
//        bookRepository.deleteAll();
    }

    // BDDMockito 패턴 given, when, then
    @Test
    public void save_테스트() throws Exception {
        // given (테스트를 하기 위한 준비)
        Book book = new Book(null, "스프링 따라하기", "Aaron");
        String content = new ObjectMapper().writeValueAsString(book);

        // when (테스트 실행)
        ResultActions resultAction = mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then (검증)
        resultAction
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("스프링 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findAll_테스트() throws Exception {
        // given
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "스프링부트 따라하기", "Aaron"));
        books.add(new Book(null, "리액트 따라하기", "Aaron"));
        books.add(new Book(null, "JUnit 따라하기", "Aaron"));
        bookRepository.saveAll(books);

        // when
        ResultActions resultActions = mockMvc.perform(get("/book")
            .accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        resultActions
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].id").value(1L))
            .andExpect(jsonPath("$", Matchers.hasSize(3)))
            .andExpect(jsonPath("$.[2].title").value("JUnit 따라하기"))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void findById_테스트() throws Exception {
        // given
        Long id = 2L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "스프링부트 따라하기", "Aaron"));
        books.add(new Book(null, "리액트 따라하기", "Aaron"));
        books.add(new Book(null, "JUnit 따라하기", "Aaron"));
        bookRepository.saveAll(books);

        // when
        ResultActions resultActions = mockMvc.perform(get("/book/{id}", id)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("리액트 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void update_테스트() throws Exception {
        // given
        Long id = 3L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "스프링부트 따라하기", "Aaron"));
        books.add(new Book(null, "리액트 따라하기", "Aaron"));
        books.add(new Book(null, "JUnit 따라하기", "Aaron"));
        bookRepository.saveAll(books);

        Book book = new Book(null, "C# 따라하기", "Aaron");
        String content = new ObjectMapper().writeValueAsString(book);

        // when
        ResultActions resultActions = mockMvc.perform(put("/book/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(content)
                .accept(MediaType.APPLICATION_JSON_UTF8));

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.title").value("C# 따라하기"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void delete_테스트() throws Exception {
        // given
        Long id = 1L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "스프링부트 따라하기", "Aaron"));
        books.add(new Book(null, "리액트 따라하기", "Aaron"));
        books.add(new Book(null, "JUnit 따라하기", "Aaron"));
        bookRepository.saveAll(books);

        // when
        ResultActions resultActions = mockMvc.perform(delete("/book/{id}", id)
                .accept(MediaType.TEXT_PLAIN));

        // then
        resultActions
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        MvcResult requestResult = resultActions.andReturn();
        String result = requestResult.getResponse().getContentAsString();

        assertEquals("OK", result);
    }
}
