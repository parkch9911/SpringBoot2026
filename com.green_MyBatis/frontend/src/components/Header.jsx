import {Link} from 'react-router-dom';
import { AuthContext } from '../contexts/AuthContext';
import { useContext } from 'react';
import "./Header.css";

export default function Header(){
    //전역 저장소에서 user와 logout 함수 직접 가져온다.
    const {user,logout}=useContext(AuthContext); 

    return(
        <header>
            <div id="top">
                <h3>MEMBER JOIN</h3>
            </div>
            <div id="header_wrap">
                <Link to="/">HOME</Link>
                {!user? (
                    <>
                        <Link to="/member/signup">회원가입</Link>
                        <Link to="/member/login">로그인</Link>
                    </>
                ):(
                    <>
                        <span style={{fontWeight:'blod',color:'#333'}}>
                            {user === 'admin9867'?
                            <>
                                <span>관리자님</span>
                                <Link to="member/list">회원목록</Link>
                            </>
                            :
                            <>
                                <span>{user}님</span>
                            </>
                            }
                        </span>
                        {/* logout 함수 연결 */}
                        <Link to="/" onClick={logout}>로그아웃</Link>
                        <Link to="/member/myinfo">개인상세정보</Link>
                    </>
                    
                )}
                <Link to="/board/list">게시판</Link>
            </div>
        </header>
    )
}