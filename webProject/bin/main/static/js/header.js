// 로그인 정보를 전역변수로 쓰기 위해서 변수로 저장
let loginMemberInfo = null;


// 로그인 정보 요청 함수
const getLoginMemail = () => {
  // fetch
  const option = { method: "GET" };
  let mlogBox = document.querySelector(".mlogBox");
  let html = "";

  fetch("/member/myinfo.do", option)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      console.log("로그인 상태");

      // 로그인 정보를 전역변수에 저장 
      loginMemberInfo = data;

      // 로그아웃 버튼 활성화 // 기능 추후 활성화
      html += `
            <ul class="authNav mlogBox">
            <li><a href="#" onclick="logOut()">로그아웃</a></li></ul>`;
      mlogBox.innerHTML = html;
    })
    .catch((e) => {
      console.log(e);
      console.log("비로그인 상태");
      html = `
            <ul class="authNav mlogBox">
            <li><a href="/member/login">로그인</a></li>
            <li><a href="/member/signup">회원가입</a></li>
            </ul>
            `;
      mlogBox.innerHTML = html;
    });
};
getLoginMemail();

// [2] 로그아웃 함수
const logOut = () => {
  // 1. fetch option
  const option = { method: "GET" };
  // 2. fetch
  fetch("/member/logout.do", option)
    .then((response) => response.json())
    .then((data) => {
      // 만약에 로그아웃 성공 했다면 로그인 페이지로 이동
      if (data == true) {
        //로그아웃시 초기화
        loginMemberInfo = null;
        alert("로그아웃 했습니다.");
        location.href = "/";
      }
    })
    .catch((e) => {
      console.log(e);
    });
}; // f end
