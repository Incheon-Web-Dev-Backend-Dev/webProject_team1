

// [1] 마이페이지에서 (로그인된) 내정보 불러오기
const getmyInfo = () => {

    // 1. fetch 이용한 내 정보 요청과 응답 받기
    fetch('/member/myinfo.do' , {method : 'GET'})
    .then(response => response.json())
    .then(data =>{
        if(data != ''){ // 응답 결과가 존재하면
            let profileHeader = '';
            if (data.role === "company") {
                profileHeader = `<div class="profileheader">든든한 업체 ${data.mname}&#128147;</div>`;
            } else if (data.role === "master") {
                profileHeader = `<div class="profileheader">숨은 고수 ${data.mname}님&#128147;</div>`;
            } else if (data.role === "requester") {
                profileHeader = `<div class="profileheader">반갑습니다 ${data.mname}님&#128147;</div>`;
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


