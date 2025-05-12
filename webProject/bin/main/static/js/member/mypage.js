// [1] 마이페이지에서 (로그인된) 내정보 불러오기
const getmyInfo = () => {

    // 1. fetch 이용한 내 정보 요청과 응답 받기
    fetch('/member/myinfo.do' , {method : 'GET'})
    .then(response => response.json())
    .then(data =>{
        if(data != ''){ // 응답 결과가 존재하면
            let profileHeader = '';
            if (data.role === "company") {
                profileHeader = `<div class="profileheader">든든한 업체 ${data.mname}🏢</div>`;
            } else if (data.role === "master") {
                profileHeader = `<div class="profileheader">열혈 숨은 고수 ${data.mname}님🔥</div>`;
            } else if (data.role === "requester") {
                profileHeader = `<div class="profileheader">반갑습니다. ${data.mname}님😁</div>`;
            }

            // 프로필 헤더가 표시될 곳을 찾아서 내용 삽입
            document.querySelector('.profileheader').innerHTML = profileHeader;
            // 응답 결과를 각 input value에 각 정보들을 대입하기
            document.querySelector('.memailInput').value = data.memail;
            document.querySelector('.mnameInput').value = data.mname;
            document.querySelector('.mphoneInput').value = data.mphone;
            document.querySelector('.maddrInput').value = data.maddr;
        }
    }).catch(e => {console.log(e)})
}
getmyInfo(); //info.html 이 열릴때 내정보 보기

// [2] 마이페이지 에서 (로그인된)회원탈퇴 요청하기
const onDelete = ( ) => {
    // * 예/아니요 형식으로 탈퇴 여부를 묻고 아니요 이면 탈퇴를 중지한다.
    let result = confirm('정말 탈퇴 하실건가요?');
    if( result == false ) { return; }
    // 1. fetch 이용한 회월탈퇴 서비스 요청 과 응답 받기
    fetch( '/member/delete.do' , { method : "DELETE"} )
    .then( response => response.json() )
    .then( data => {
        if( data == true ){ alert('탈퇴 성공'); location.href='/'; }
        else{ alert('탈퇴 실패'); }
    }).catch( e => { console.log(e); })
} // f enmd

// [3] 마이페이지에서 (로그인된)회원정보 수정버튼 클릭시 비밀번호확인

const checkPwd = () => {
    const pwd = document.querySelector('#password').value;

    fetch(`/member/checkpwd.do`, {
        method: "POST", // ✅ GET 대신 POST 사용
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ mpwd: pwd }) // ✅ URL에 노출되지 않도록 Body에 담음
    })
    .then(response => response.json())
    .then(data => {
        if (data === true) {
            window.location.href = '/member/update'; // ✅ 회원정보 수정 페이지로 이동
        } else {
            alert("비밀번호가 일치하지 않습니다.");
            window.location.reload(); // ✅ 페이지 새로고침
        }
    })
    .catch(e => console.error("비밀번호 확인 오류:", e));
};
// 비밀번호 변경 클릭시 비밀번호입력창 나오도록
document.addEventListener("DOMContentLoaded", function () {
    const changePwdBtn = document.getElementById("changePwdBtn");
    const passwordChangeForm = document.getElementById("passwordChangeForm");

    // "비밀번호 변경" 버튼 클릭 시 폼 표시
    changePwdBtn.addEventListener("click", function () {
      if (passwordChangeForm.style.display === "none" || passwordChangeForm.style.display === "") {
        passwordChangeForm.style.display = "block";
      } else {
        passwordChangeForm.style.display = "none";
      }
    });
  });


// 내 정보 수정
const onUpdate = ( ) => {
    // 1. 입력받은 input value 값 가져오기.
    let memail = document.querySelector('.memailInput').value;
    let mpwd = document.querySelector('#mpwdInput').value;
    let mname = document.querySelector('.mnameInput').value;
    let mphone = document.querySelector('.mphoneInput').value;
    let maddr = document.querySelector('.maddrInput').value;

    // 2. 객체화
    let dataObj = { memail : memail , mpwd : mpwd , mname : mname , mphone : mphone , maddr : maddr }
    // 3. fetch
    const option = {
        method : 'PUT' ,
        headers : {'Content-Type' : 'application/json'},
        body : JSON.stringify( dataObj )
    }// o end
    fetch( '/member/update.do' , option )
    .then( response => response.json() )
    .then( data => {
        if( data ){ alert('수정 성공'); location.href="/member/mypage"; }
        else{ alert('수정 실패');}
    }).catch( e => { console.log(e); } )
}// f end