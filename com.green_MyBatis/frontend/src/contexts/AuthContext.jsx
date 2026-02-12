import { createContext,useState,useEffect } from "react";

// 저장소 생성
export const AuthContext = createContext();

export default function AuthProvider({children}){
    // [상태정의] 로그인한 사용자의 정보를 담는 변수
    // 초기값은 null(로그아웃상태)
    const [user,setUser]=useState(null);

    //자동 로그인 처리 useEffect 이용 딱 한번만 실행
    //만약 브라우저가 새로고침되더라도 로그인이 유지되도록
    //컴포넌트가 처음 화면에 나타날 때 = 마운트 될때 한번 실행됨
    useEffect(()=>{
        // 세션 스토리지에서 'user'라는 이름으로 문자열을 가져온다.
        // 세션 스토리지는 웹에서 탭을 종료하면 무조건 삭제된다.
        const saveUser = sessionStorage.getItem("user");

        // 저장된 데이터가 있다면 문자열을 다시 자바스크립트 객체로
        // 변환(JSON.parse) 하여 상태에 저장한다.
        if(saveUser){
            setUser(JSON.parse(saveUser));
        }
    },[])

// 로그인 함수
// 로그인 성공 시 서버로부터 받은 사용자 데이터(userData)
const login = (userData)=>{
    // 세션 스토리지는 문자열만 저장이 가능하므로 
    // 객체를 문자열로 변환(JSON.stringify())해야한다.
    sessionStorage.setItem("user",JSON.stringify(userData));

    // React 상태 업데이트 할 때 렌더링이 되므로 setUser() 저장
    setUser(userData);
} 

//로그아웃 함수
const logout =()=>{
    //세션 스토리지 정보 삭제
    sessionStorage.removeItem("user");
    //초기값 null
    setUser(null);
}

//데이터 공급 : Provider 사용
return(
    <AuthContext.Provider value={{user,login,logout}}>
        {/* Provider 사이에 위치한 모든 컴포넌트는 value를 공유받는다. */}
        {children}
    </AuthContext.Provider>
)
}