console.log("findAll.js open")

const estno = new URL( location.href ).searchParams.get('estno')
console.log(estno);

// 견적글 상세 보기 함수
const estView = () => {
    const option = {method:'GET'}

    fetch(`/estimate/find.do?estno=${estno}`, option)
    .then(r => r.json())
    .then(data => {
        console.log(data);
        // HTML로 표시할 부분을 만들어서 그곳에 넣을 것
        const estviewForm = document.querySelector(".estviewForm"); // 데이터를 넣을 곳
        const adoptedBtn = document.querySelector(".adoptedBtn"); // 버튼 넣을 곳
        
        

        // 견적 채택하기 버튼 관련 함수들(채택여부, 마감여부에 따라 속성 설정)
        const getBtnAction = (data) => { // 버튼 기능
            if(data.eststate || !data.reqstate) return ''; // 이미 채택된 견적서이거나 이미 마감된 요청글이면  아무것도 반환하지 않음
            return 'estimateSelect()'; // 위 조건이 아닌 경우에는 estimateSelect함수 실행
        }
        const getBtnClass = (data) => { // 버튼 색
            if(data.eststate) return 'btn-success'; // 채택된 견적서
            if(!data.reqstate) return 'btn-secondary'; // 마감된 요청글의 견적서이면 
            return 'btn-primary'; //채택 가능한 견적서로 반환
        }
        const getBtnStyle = (data) => { // 버튼 효과
            if(data.eststate || !data.reqstate) return 'cursor:default;';
            return '';
        }
        const getBtnDisabled = (data) => { // 버튼 활성/비활성화 여부
            if(data.eststate || !data.reqstate) return 'disabled';
            return '';
        
        }
        const getBtnText = (data) => { // 버튼 문구
            if(data.eststate) return '채택된 견적서입니다.'; 
            if(!data.reqstate) return '마감된 요청글입니다.';
            return '견적글 채택하기';
        }


        if(data.mno>0){
        let html = `
            <div class="estviewForm">
                <div>
                    <label for="basic-url" class="form-label">작성자 이름</label>
                    <div class="form-control estcashInput" style="background-color: #f8f9fa; padding: 10px;">
                        ${data.mname}
                    </div>
                </div>
                <div>
                    <label for="basic-url" class="form-label">견적 제목</label>
                    <div class="form-control esttitleInput" style="background-color: #f8f9fa; padding: 10px;">
                        ${data.esttitle}
                    </div>
                </div>
                <div>
                    <label for="basic-url" class="form-label">견적 내용</label>
                    <div class="form-control estcontentInput" style="background-color: #f8f9fa; padding: 10px;">
                        ${data.estcontent}
                    </div>
                </div>
                <div>
                    <label for="basic-url" class="form-label">견적 금액</label>
                    <div class="form-control estcashInput" style="background-color: #f8f9fa; padding: 10px;">
                        ${data.estcash}
                    </div>
                </div>
                <div class="mb-3">
                    <span>작성 시간 : </span> ${data.cdate}
                </div>   
                
            </div>

        `;
        // 결과를 HTML 요소에 삽입
        estviewForm.innerHTML = html;
        }


        // 버튼 html 삽입 구역
        if(data.mno>0){
            let buttonHtml = `
                ${loginMemberInfo.role !== "requester" ? //업체랑 개인활동자만 보이게
                    `<a href="/request/view?reqno=${data.reqno}" class="btn btn-primary" style="color: white;">해당 요청글 보기</a>` 
                    : ''}
                ${loginMemberInfo.role !== "requester" ?  //삭제
                    `<a onclick="estDelete()" class="btn btn-primary" style="color: white;">견적글 삭제하기</a>` 
                    : ''}
                ${loginMemberInfo.role == "requester" ?  // 견적 선택하기
                    // eststate 와 reqstate 값에 따라서 버튼 효과 
                    `<a onclick= "${ getBtnAction(data) }"
                    class = "btn ${getBtnClass(data)}"
                    style = "color:white; ${getBtnStyle(data)}"
                    ${getBtnDisabled(data)}>
                    ${getBtnText(data)}
                    </a>`
                : ''}
                    ${loginMemberInfo.mno == data.mno ? 
                        `<button type="button" class="btn btn-primary" onclick="sendReviewRequest(${data.estno})">(완료)리뷰작성 페이지 보내기</button>` 
                        : ''
                    }
                    <button type="button" class="btn btn-primary">
                        <a href='/estimate/list?reqno=${data.reqno}' style='color: white;'>목록</a>
                    </button>
            `;
            adoptedBtn.innerHTML = buttonHtml;
        }
    })
    .catch(e => {
        console.log(e);
    });
}
// 호출
estView();

const estDelete = () => {
    const estno = new URL(location.href).searchParams.get('estno');
    console.log("Deleting estimate with estno:", estno);  // 삭제할 견적 번호 확인
    let result = confirm("게시물을 삭제 하실건가요?");
    if(result === false) return;
    // DELETE 요청
    fetch(`/estimate/delete?estno=${estno}`, { method: "DELETE" })
        .then(response => response.text())  // text()로 응답 처리
        .then(d => {
            console.log("Response from server:", d);  // 서버에서 반환된 응답 확인
            // 삭제 성공 메시지 처리
            if (d === "Estimate Delete success.") {
                alert("삭제 성공");  // 삭제 성공 메시지
                window.location.href = "/estimate/mywrote";  // 페이지 이동
            } else {
                // 삭제 실패 시
                alert("삭제 실패");  // 실패 메시지
            }
        })
        .catch(e => {
            console.error("Error occurred while deleting:", e);  // 오류 발생 시 콘솔에 출력
            alert("삭제 중 오류가 발생했습니다.");
        });
}

// 견적 채택하는 함수
const estimateSelect = () => {
    console.log('estimate select onClick');

    if(!confirm(`이 견적서를 채택하시겠습니까? \n확정 후 견적서를 수정할 수 없습니다.`))return;

    // 1. 현재 estno 가져오기
    const estno = new URL(location.href).searchParams.get('estno');

    // 2. fetch
    const option = {
        method : 'POST',
        headers : { 'Content-Type' : 'application/json' }
    }
    fetch(`/estimate/select.do?estno=${estno}`, option)
        .then(r => r.text())
        .then(result => {
            console.log(result);
            if(result) {
                alert('견적서가 채택되었습니다.');
                location.reload(); // 페이지 새로고침

            }else {
                alert('견적서 채택에 실패했습니다.')
            } // if-else end
        })
        .catch( e => console.log(e))
}

// 리뷰 요청이메일 발송 함수
const sendReviewRequest = (estno) => {
    console.log("sendReviewRequest method on");

    if(!confirm('서비스 이용자에게 리뷰 작성 안내 이메일을 발송하시겠습니까?')) return;

    const option = {
        method : 'POST',
        headers: {'Content-Type' : 'application/json'}
    };

    fetch(`/mail/sendQRCode?estno=${estno}`, option)
        .then(response => response.json())
        .then(result => {
            console.log(result);
            if(result) {
                alert('리뷰 작성 안내 이메일이 성공적으로 발송되었습니다.');
            } else {
                alert('이메일 발송에 실패했습니다.')
            }
        })
        .catch(error => {
            console.log(error)
            alert('이메일 발송 중 오류가 발생했습니다.')
        })
}
