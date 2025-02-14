// login 때 역할 버튼 js
$(document).ready(function() {
    // 버튼 클릭 시 active 클래스를 토글
    $('.role-btn').click(function() {
      // 모든 버튼에서 active 클래스 제거
      $('.role-btn').removeClass('active');
      
      // 클릭된 버튼에만 active 클래스 추가
      $(this).addClass('active');
    });
  });

  const onLogin = () => {
    // Input Dom 가져오기
    let memailInput = document.querySelector('.memailInput'); console.log(memailInput);
    let mpwdInput = document.querySelector('.mpwdInput'); console.log(mpwdInput);
  
    // DOM의 Value(입력값) 가져오기
    let memail = memailInput.value; console.log(memail);
    let mpwd = mpwdInput.value; console.log(mpwd);
  
    //(!!) 유효성 검사 추가 ex) 비밀번호길이
  
    // 객체화 - 추후 role 추가
    let loginDto = {memail : memail, mpwd : mpwd}; console.log(loginDto);
  
    // fetch
    const option = {
      method : 'POST',
      headers : {'Content-Type':'application/json'},
      body : JSON.stringify(loginDto)
    };
  
    fetch('/member/login.do', option)
      .then(r => {
        if (!r.ok) {  // 응답 상태가 정상적이지 않으면
          throw new Error('서버 오류');
        }
        return r.json();  // 응답을 JSON으로 파싱
      })
      .then(data => {
        // 서버에서 true 또는 false만 반환하는 경우
        if (data === true) {  // 로그인 성공시 true, 실패시 false
          alert('로그인 성공!');
          location.href = "/";  // 로그인 후 이동할 페이지
        } else {
          alert('로그인을 실패했습니다.');
        }
      })
      .catch(e => {
        alert('시스템 오류 : 관리자에게 문의바랍니다.');
        console.log(e);
      });
  }
  



  