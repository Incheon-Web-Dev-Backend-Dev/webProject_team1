// [1] 마이페이지에서 (로그인된) 내정보 불러오기
const getmyInfo = () => {

    // 1. fetch 이용한 내 정보 요청과 응답 받기
    fetch('/member/myinfo.do' , {method : 'GET'})
    .then(response => response.json())
    .then(data =>{
        if(data != ''){ // 응답 결과가 존재하면
            // 응답 결과를 각 input value에 각 정보들을 대입하기
            document.querySelector('.memailInput').value = data.memail;
            document.querySelector('.mnameInput').value = data.mname;
            document.querySelector('.mphoneInput').value = data.mphone;
            document.querySelector('.maddrInput').value = data.maddr;
        }
    }).catch(e => {console.log(e)})
}
getmyInfo(); //info.html 이 열릴때 내정보 보기