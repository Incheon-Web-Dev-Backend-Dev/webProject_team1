// 로그인 시 활성화된 역할 버튼 처리
$(document).ready(function () {
  $(".role-btn").click(function () {
      $(".role-btn").removeClass("active");
      $(this).addClass("active");
  });
});

// 엔터 키 눌렀을 때 로그인 처리
const enterKey = () => {
  if (window.event.keyCode === 13) {
      onLogin();
  }
};

// 로그인 함수
const onLogin = () => {
  let memailInput = document.querySelector(".memailInput");
  let mpwdInput = document.querySelector(".mpwdInput");

  let memail = memailInput.value;
  let mpwd = mpwdInput.value;

  let loginDto = { memail: memail, mpwd: mpwd };

  const option = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(loginDto),
  };

  fetch("/member/login", option)
      .then((response) => response.json())
      .then((data) => {
          if (data === true) {
              alert("로그인 성공");
              location.href = "/";
          } else {
              alert("회원정보가 일치하지 않습니다.");
          }
      })
      .catch((error) => {
          alert("시스템 오류: 관리자에게 문의");
          console.log(error);
      });
};


