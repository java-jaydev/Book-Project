import React from 'react';
import { Container } from 'react-bootstrap';
import { Route, Routes } from 'react-router-dom';
import Header from './components/Header.js';
import Detail from './pages/book/Detail.js';
import Home from './pages/book/Home.js';
import SaveForm from './pages/book/SaveForm.js';
import UpdateForm from './pages/book/UpdateForm.js';
import JoinForm from './pages/user/JoinForm.js';
import LoginForm from './pages/user/LoginForm.js';

function App() {
  return (
    <div>
      <Header />
      <Container>
        <Routes>
          <Route path="/" exact={true} element={<Home />} />
          <Route path="/saveForm" exact={true} element={<SaveForm />} />
          <Route path="/book/:id" exact={true} element={<Detail />} />
          <Route path="/updateForm/:id" exact={true} element={<UpdateForm />} />
          <Route path="/loginForm" exact={true} element={<LoginForm />} />
          <Route path="/joinForm" exact={true} element={<JoinForm />} />
        </Routes>
      </Container>
    </div>
  );
}

export default App;
