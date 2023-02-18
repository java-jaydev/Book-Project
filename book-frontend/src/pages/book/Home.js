import React, { useEffect, useState } from 'react';
import BookItem from '../../components/BookItem.js';

const Home = () => {
  const [books, setBooks] = useState([]);

  // 함수 실행 시 최초 한번 실행되는 것
  useEffect(() => {
    fetch('http://localhost:8080/book', {
      method: 'GET',
    })
      .then((res) => res.json())
      .then((res) => {
        setBooks(res);
      }); // 비동기 함수
  }, []);

  return (
    <div>
      {books.map((book) => (
        <BookItem key={book.id} book={book} />
      ))}
    </div>
  );
};

export default Home;
