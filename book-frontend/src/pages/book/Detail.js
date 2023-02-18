import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';

const Detail = (props) => {
  const propsParams = useParams();
  const id = propsParams.id;
  const navigate = useNavigate();

  const [book, setBook] = useState({
    id: 0,
    title: '',
    author: '',
  });

  useEffect(() => {
    fetch('http://localhost:8080/book/' + id, { method: 'GET' })
      .then((res) => res.json())
      .then((res) => {
        setBook(res);
      });
  }, []);

  const deleteBook = (id) => {
    fetch('http://localhost:8080/book/' + id, { method: 'DELETE' })
      .then((res) => res.text())
      .then((res) => {
        if (res === 'OK') {
          console.log(1, '삭제됨');
          navigate('/');
        } else {
          alert('삭제실패');
        }
      });
  };

  const updateBook = () => {
    navigate('/updateForm/' + id);
  };

  const moveToList = () => {
    navigate('/');
  };

  return (
    <div>
      <h1>책 상세보기</h1>
      <Button variant="warning" onClick={updateBook}>
        수정
      </Button>{' '}
      <Button variant="danger" onClick={() => deleteBook(book.id)}>
        삭제
      </Button>{' '}
      <Button variant="primary" onClick={moveToList}>
        목록
      </Button>
      <hr />
      <h3>{book.author}</h3>
      <h1>{book.title}</h1>
    </div>
  );
};

export default Detail;
