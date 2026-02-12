import { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import './Member.css';

export default function Signup() {

    // 리액트 화면에서 입력한 자료를 가지고 (JSON)
    // 백엔드의 주소로 데이터를 넘겨주어야 함
    // 입력값을 저장할 상태변수
    const [id,setId]=useState('');
    const [pw,setPw]=useState('');
    const [mail,setMail]=useState('');
    const [phone,setPhone]=useState('');

    const navigate= useNavigate(); // 이동할 때 사용하는 커스텀 훅

    //회원가입 버튼 클릭 시 실행되는 핸들러
    const signup = ()=>{
        //id,pw 유효성 검사
        if(id === ''){
            alert("아이디를 입력하세요")
            return;
        }
        if(pw === ''){
            alert("비밀번호를 입력하세요")
            return;
        }

        // 데이터 전송
        axios.post('/api/member/signup',{id:id,pw:pw,mail:mail,phone:phone})
        .then((res)=>{
            // 회원가입이 성공 했을 경우 result signup_result 페이지로 이동 예정
            if(res.data === 1){
                alert("회원가입 성공")
                navigate('/member/signup_result?result=success');
            }else if(res.data === 0){
                alert("아이디가 이미 존재합니다.")
                navigate('/member/signup_result?result=duplicate');
            }else{
                alert("회원가입 실패")
                navigate('/member/signup_result?result=fail');
            }
        })
        .catch((error)=>{
            console.log(error);
            navigate('/member/signup_result?result=error');
        })
    }


return (
    <div id="section_wrap">
      <div className="word">회원가입</div>
      <table width="500" border="1">
        <tbody>
          <tr>
            <td>아이디</td>
            <td>
              <input
                type="text"
                name="id"
                value={id}
                onChange={(e)=>setId(e.target.value)}
              />
            </td>
          </tr>
          <tr>
            <td>비밀번호</td>
            <td>
              <input
                type="password"
                name="pw"
                value={pw}
                onChange={(e)=>setPw(e.target.value)}
              />
            </td>
          </tr>
          <tr>
            <td>이메일</td>
            <td>
              <input
                type="email"
                name="mail"
                value={mail}
                onChange={(e)=>setMail(e.target.value)}
              />
            </td>
          </tr>
          <tr>
            <td>전화번호</td>
            <td>
              <input
                type="tel"
                name="phone"
                value={phone}
                onChange={(e)=>setPhone(e.target.value)}
              />
            </td>
          </tr>
          <tr>
            <td colSpan="2" align="center">
              <button onClick={signup}>회원가입</button>
              <button type="reset">취소</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}