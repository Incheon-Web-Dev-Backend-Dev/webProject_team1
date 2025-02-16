const getMyInfo = () => {
    fetch('/member/myinfo.do',{method : 'GET'})
    .then(r => r.json())
    .then(d => {console.log(d); requestFindAll(d.role);})
    .catch(e => console.log(e))
}

getMyInfo();



// 내가 올린 견적요청서 리스트 조회 함수
const requestFindAll = (role) => {
    console.log("견적요청서 전체조회 함수 실행");
    console.log(role)
    fetch(`/request/findrole.do?role=${role}`, {method: 'GET'})
        .then( r => {
            if (!r.ok) {
                throw new Error(`HTTP error! status: ${r.status}`);
            }
            console.log('응답 상태:', r.status);
            console.log('응답 헤더:', r.headers);
            return r.json();  // 일단 텍스트로 받아보기
        })
        .then(data => {
            // 요청 결과 응답자료 확인
            console.log(data);
            console.log(role)
            // 1. html을 출력할 구역 가져오기 
            const reqCardContent = document.querySelector(".reqListCardBox");

            // 2. 출력할 html을 저장하는 변수 선언 
            let html = ``;

            data.forEach(list => {
                html += `
                <h2 class="reqstlistHeader"> 정리/수납 견적서 요청 전체목록</h2>
                <div class="card" style="width: 32rem;">
                    <div class="card-body">
                        <div class="card-content cardbox">
                            <div>
                                <h6 class="card-subtitle mb-2 text-body-secondary">${list.reqdatetime}</h6>
                                <h5 class="card-title">${list.reqtitle}</h5>
                                <!--견적쓰기 임시버튼(임준수)-->
                                <button><a href="/estimate/write" class="btn btn-primary">견적쓰기임시버튼</a></button>
                            </div>
                            <div class="card-link">
                                <span> 들어온 견적서 </span>
                                <a href="/estimate/list?reqno=${list.reqno}" class="card-link">${list.estimateCount}건 </a>
                            </div>
                        </div>
                    </div>
                </div>
                `
            });
            // 4. 반복문 종료후 html 변수에 누적된 구역 출력하기
            reqCardContent.innerHTML = html;
        })
        .catch (e => {console.log(e)})
} // f end

// 페이지 실행되면 자동으로 함수 실행
requestFindAll();