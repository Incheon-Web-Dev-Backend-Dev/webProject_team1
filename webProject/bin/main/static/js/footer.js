function unlogin(){
    alert("로그인을 먼저 해주세요");
}

function getLoginMemailFoot(){
    const option = { method: 'GET' };
    const mlogBoxFooter = document.querySelector('.mlogBoxFooter');

    fetch('/member/myinfo', option)
        .then(response => response.json())
        .then(data => {
            console.log(data);

            let html = '';
            let buttons = [];

            if (data.role === "company") {
                buttons = [
                    { href: "/job/mylist", icon: "fa-sheet-plastic", text: "작성 구인글" },
                    { href: "/request/list", icon: "fa-phone-volume", text: "견적 요청서" },
                    { href: "/estimate/mywrote", icon: "fa-person-circle-question", text: "작성 견적서" },
                    { href: "/member/mypage", icon: "fa-user", text: "마이페이지" }
                ];
            } else if (data.role === "master") {
                buttons = [
                    { href: "/job/list", icon: "fa-people-line", text: "구인글" },
                    { href: "/request/list", icon: "fa-phone-volume", text: "견적 요청서" },
                    { href: "/estimate/mywrote", icon: "fa-person-circle-question", text: "작성 견적서" },
                    { href: "/member/mypage", icon: "fa-user", text: "마이페이지" }
                ];
            } else if (data.role === "requester") {
                buttons = [
                    { href: "/", icon: "fa-house", text: "메인 페이지" },
                    { href: "/request/post", icon: "fa-comments-dollar", text: "고수 찾기" },
                    { href: "/request/list", icon: "fa-envelope", text: "들어온 견적" },
                    { href: "/member/mypage", icon: "fa-user", text: "마이페이지" }
                ];
            } else {
                alert("오류발생! 관리자에게 문의바랍니다.");
                return;
            }

            buttons.forEach(btn => {
                html += `
                    <a href="${btn.href}" class="footer-btn">
                        <i class="fa-solid ${btn.icon} btn-icon"></i>
                        <span class="btn-text">${btn.text}</span>
                    </a>
                `;
            });

            mlogBoxFooter.innerHTML = `<div class="footer-buttons">${html}</div>`;
        })
        .catch(e => console.log("로그인 정보 요청 실패:", e));
}

getLoginMemailFoot();
