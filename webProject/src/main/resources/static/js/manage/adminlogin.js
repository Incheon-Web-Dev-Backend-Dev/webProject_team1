function login() {

    let memailInput = document.querySelector(".memailInput");
    let mpwdInput = document.querySelector(".mpwdInput");

    let memail = memailInput.value;
    let mpwd = mpwdInput.value;

    let loginDto = {memail : memail, mpwd : mpwd};

    const option = {
        method : "POST",
        headers : {"Content-Type":"application/json"},
        body : JSON.stringify(loginDto),
    };min
    fetch("/member/login.do", option)
        .then(response => response.json())
        .then(data=> {
            if(data==true){
                // 로그인 성공 시, 로컬스토리지에 로그인 상태 저장
                localStorage.setItem('isLoggedIn', 'true'); // 로그인 상태 저장
                location.href = "/manage/main";
            } else {
                alert("등록된 관리자가 아닙니다.");
            }
        })
        .catch(e=>{console.log(e);})
}