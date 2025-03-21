function unlogin(){
    alert("로그인을 먼저 해주세요")
}

//  로그인 정보 요청 함수
 function getLoginMemailFoot(){

    const option = {method : 'GET'}
    let mlogBoxFooter = document.querySelector('.mlogBoxFooter')
    let html = '';
    fetch('/member/myinfo.do', option)
    .then(response => response.json())
    .then(data => {
        console.log(data);
        let html = ''; // html 초기화
        if (data.role == "company") {
            html += `
                <div class="container text-bg-secondary p-3">
                    <div class="mlogBoxFooter">
                        <div class="footer-buttons">
                            <a href="/job/mylist" class="footer-btn">
                                <i class="fa-solid fa-sheet-plastic btn-icon"></i> <!-- 아이콘 -->
                                <span class="btn-text">작성 구인글</span>
                            </a>
                            <a href="/request/list" class="footer-btn">
                                <i class="fa-solid fa-phone-volume btn-icon"></i> <!-- 아이콘 -->
                                <span class="btn-text">견적 요청서</span>
                            </a>
                            <a href="/estimate/mywrote" class="footer-btn">
                                <i class="fa-solid fa-person-circle-question btn-icon"></i> <!-- 아이콘 -->
                                <span class="btn-text">작성 견적서</span>
                            </a>
                            <a href="/member/mypage" class="footer-btn">
                                <i class="fa-solid fa-user btn-icon"></i> <!-- 아이콘 -->
                                <span class="btn-text">마이페이지</span>
                            </a>
                        </div>
                    </div>
                </div>
            `;
        } else if (data.role == "master") {
            html += `
                <div class="container text-bg-secondary p-3">
                    <div class="mlogBoxFooter">
                        <div class="footer-buttons">
                            <a href="/job/list" class="footer-btn">
                                <i class="fa-solid fa-people-line btn-icon"></i> <!-- 아이콘 -->
                                <span class="btn-text">구인글</span>
                            </a>
                            <a href="/request/list" class="footer-btn">
                                <i class="fa-solid fa-phone-volume btn-icon"></i> <!-- 아이콘 -->
                                <span class="btn-text">견적 요청서</span>
                            </a>
                            <a href="/estimate/mywrote" class="footer-btn">
                                <i class="fa-solid fa-person-circle-question btn-icon"></i> <!-- 아이콘 -->
                                <span class="btn-text">작성 견적서</span>
                            </a>
                            <a href="/member/mypage" class="footer-btn">
                                <i class="fa-solid fa-user btn-icon"></i> <!-- 아이콘 -->
                                <span class="btn-text">마이페이지</span>
                            </a>
                        </div>
                    </div>
                </div>
            `;
        } else if (data.role == "requester") {
            html += `
                <div class="container text-bg-secondary p-3">
                    <div class="mlogBoxFooter">
                        <div class="footer-buttons">
                            <a href="/" class="footer-btn">
                                <i class="fa-solid fa-house btn-icon"></i> <!-- 아이콘 -->
                                <span class="btn-text">메인 페이지</span>
                            </a>
                            <a href="/request/post" class="footer-btn">
                                <i class="fa-solid fa-comments-dollar btn-icon"></i> <!-- 아이콘 -->
                                <span class="btn-text">고수 찾기</span>
                            </a>
                            <a href="/request/list" class="footer-btn">
                                <i class="fa-solid fa-envelope btn-icon"></i> <!-- 아이콘 -->
                                <span class="btn-text">들어온 견적</span>
                            </a>
                            <a href="/member/mypage" class="footer-btn">
                                <i class="fa-solid fa-user btn-icon"></i> <!-- 아이콘 -->
                                <span class="btn-text">마이페이지</span>
                            </a>
                        </div>
                    </div>
                </div>
            `;
        } else {
            alert("오류발생! 관리자에게 문의바랍니다.");
        }

        mlogBoxFooter.innerHTML = html; // HTML 내용을 footer에 삽입
    })
    .catch(e => {console.log(e)}); // 오류 처리

}
getLoginMemailFoot();