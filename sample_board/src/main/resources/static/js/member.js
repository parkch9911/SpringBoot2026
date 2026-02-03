/*회원가입 유효성 검사 규칙*/
function signupForm(){
	console.log("회원가입폼")
	/*DOM 으로 form을 연결*/
	let form = document.signup_form;
	if(form.id.value===""){
		alert("새로운 id 입력");
		/*커서를 id로 지정*/
		form.id.focus();
	}else if(form.pw.value===""){
		 alert("새로운 pw 입력");
		 /*커서를 id로 지정*/
		form.pw.focus();	
	}else if(form.name.value===""){
		alert("새로운 name 입력");
		/*커서를 id로 지정*/
		form.name.focus();			
	}else{
		/* 전송해라*/
		form.submit();
	}			
	
}