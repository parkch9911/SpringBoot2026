import { useState, useContext } from 'react';
import { AuthContext } from '../contexts/AuthContext';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Member.css';

export default function Login() {

const [id,setId]=useState('');
const [pw,setPw]=useState('');
const navigate = useNavigate();
const {login}=useContext(AuthContext); 

//로그인 버튼 클릭시 실행하는 핸들러
const loginHandler=()=>{
    if(id === ''){
            alert("아이디를 입력하세요");
            return;
        }
        if(pw === ''){
            alert("비밀번호를 입력하세요");
            return;
        }
    //서버 로그인 요청
    axios.post('/api/member/login',{id:id,pw:pw})
    .then((res)=>{
        //로그인 성공/실패 여부 확인
       if(res.data){
                alert(`${res.data.id}님 환영합니다.`);
                //AuthContext login함수
                login(res.data.id); //문제 O
                navigate("/"); //문제X
            }else{
                alert("아이디 또는 비밀번호를 확인하세요.");
            }
    })
    .catch((error)=>{
        console.log(error);
    })
}

  return (
    <div id="section_wrap">
      <div className="word">로그인</div>

      <table width="500" border="1">
        <tbody>
          <tr>
            <td>아이디</td>
            <td>
              <input type="text" onChange={(e)=>setId(e.target.value)} value={id} name="id"/>
            </td>
          </tr>

          <tr>
            <td>비밀번호</td>
            <td>
              <input type="password" onChange={(e)=>setPw(e.target.value)} value={pw} name="pw"/>
            </td>
          </tr>

          <tr>
            <td colSpan="2" align="center">
              <button onClick={loginHandler}>로그인</button>
              <button type="reset">취소</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}