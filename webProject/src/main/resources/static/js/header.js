
// 로그인 정보 요청 함수
const getLoginMemail = () => {
    // fetch
    const option = {method : 'GET'}
    let mlogBox = document.querySelector('.mlogBox')
    let html = '';
    
    fetch('/member/myinfo.do' , option)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            console.log("로그인 상태");
            // 로그아웃 버튼 활성화 // 기능 추후 활성화
            html +=`
            <ul class="authNav mlogBox">
            <li><a href="#">로그아웃</a></li></ul>`
        mlogBox.innerHTML = html;
        })
        .catch(e=>{console.log(e); console.log('비로그인 상태');
            html = `
            <ul class="authNav mlogBox">
            <li><a href="/member/login">로그인</a></li>
            <li><a href="/member/signup">회원가입</a></li>
            </ul>
            `
            mlogBox.innerHTML=html;
        })
}
getLoginMemail();