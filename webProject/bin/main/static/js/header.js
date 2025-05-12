let loginMemberInfo = null; // 전역 변수

const getLoginMemail = () => {
  const option = { method: "GET" };
  const mlogBox = document.querySelector(".mlogBox");

  fetch("/member/myinfo", option)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      console.log("로그인 상태");
      loginMemberInfo = data;

      mlogBox.innerHTML = `
        <li><a href="#" onclick="logOut()">로그아웃</a></li>
      `;
    })
    .catch(e => {
      console.log(e);
      console.log("비로그인 상태");

      mlogBox.innerHTML = `
        <li><a href="/member/login">로그인</a></li>
        <li><a href="/member/signup">회원가입</a></li>
      `;
    });
};

const logOut = () => {
  fetch("/member/logout", { method: "GET" })
    .then(response => response.json())
    .then(data => {
      if (data === true) {
        loginMemberInfo = null;
        alert("로그아웃 했습니다.");
        location.href = "/";
      }
    })
    .catch(console.log);
};

getLoginMemail();
