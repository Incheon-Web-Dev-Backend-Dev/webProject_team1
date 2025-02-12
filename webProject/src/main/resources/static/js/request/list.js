
// 내가 올린 견적요청서 리스트 조회 함수
const requestFindAll = () => {
    console.log("견적요청서 전체조회 함수 실행");

    fetch('/request/findall.do', {method: 'GET'})
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

            // 1. html을 출력할 구역 가져오기 
            const reqCardContent = document.querySelector(".reqListCardBox");

            // 2. 출력할 html을 저장하는 변수 선언 
            let html = ``;

            // 3. 응답 자료를 반복문을 이용하여 하나씩 순회해서 html 누적으로 더해서 출력하기 
            data.forEach ( list => {
                html += `
                <div class="card" style="width: 32rem;">
                    <div class="card-body">
                        <div class="card-content">
                            <div>
                                <h6 class="card-subtitle mb-2 text-body-secondary">${list.reqdatetime}</h6>
                                <h5 class="card-title">${list.reqtitle}</h5>
                            </div>
                            <div class="card-link">
                                <a href="#" class="card-link">들어온 견적서 수</a>
                            </div>
                        </div>
                    </div>
                </div>
                `
            }) // forEach end

            // 4. 반복문 종료후 html 변수에 누적된 구역 출력하기
            reqCardContent.innerHTML = html;
        })
        .catch (e => {console.log(e)})
} // f end

// 페이지 실행되면 자동으로 함수 실행
requestFindAll();