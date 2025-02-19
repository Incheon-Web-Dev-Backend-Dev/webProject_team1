    // login 때 역할 버튼 js
$(document).ready(function () {
  // 버튼 클릭 시 active 클래스를 토글
  $(".role-btn").click(function () {
    // 모든 버튼에서 active 클래스 제거
    $(".role-btn").removeClass("active");

    // 클릭된 버튼에만 active 클래스 추가
    $(this).addClass("active");
  });
});

const onLogin = () => {
  // Input Dom 가져오기
  let memailInput = document.querySelector(".memailInput");
  console.log(memailInput);
  let mpwdInput = document.querySelector(".mpwdInput");
  console.log(mpwdInput);

  // DOM의 Value(입력값) 가져오기
  let memail = memailInput.value;
  console.log(memail);
  let mpwd = mpwdInput.value;
  console.log(mpwd);

  //(!!) 유효성 검사 추가 ex) 비밀번호길이

  // 객체화 - 추후 role 추가
  let loginDto = { memail: memail, mpwd: mpwd };

  // fetch
  const option = {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(loginDto),
  };

  fetch("/member/login.do", option)
    .then((response) => response.json())
    .then((data) => {
      // (6) 결과에 따른 화면 제어
      if (data == true) {
        alert("로그인성공");
        location.href = "/";
      } else {
        alert("회원정보가 일치하지 않습니다.");
      }
    })
    .catch((error) => {
      alert("시스템오류:관리자에게문의");
      console.log(error);
    });
};
