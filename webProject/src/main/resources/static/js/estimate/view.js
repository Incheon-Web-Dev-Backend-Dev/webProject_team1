console.log("findAll.js open")


// 견적글 상세 보기 함수
const estView = () => {
    const option = {method:'GET'}
    const estno = new URL( location.href ).searchParams.get('estno')
    console.log(estno);

    fetch(`/estimate/find.do?estno=${estno}`, option)
    .then(r => r.json())
    .then(data => {
        console.log(data);
        // HTML로 표시할 부분을 만들어서 그곳에 넣을 것
        const estviewForm = document.querySelector(".estviewForm"); // 데이터를 넣을 곳
        let html = `
            <div class="estviewForm">
                <h6>작성자 이름</h6>
                <div class="form-floating mb-3">
                    <div class="form-control estcashInput" style="background-color: #f8f9fa; padding: 10px;">
                        ${data.mname}
                    </div>
                <h6>견적 제목</h6>
                <div class="form-floating mb-3">
                    <div class="form-control esttitleInput" style="background-color: #f8f9fa; padding: 10px;">
                        ${data.esttitle}
                    </div>
                </div>
                <h6>견적 내용</h6>
                <div class="form-floating mb-3">
                    <div class="form-control estcontentInput" style="background-color: #f8f9fa; padding: 10px;">
                        ${data.estcontent}
                    </div>
                </div>
                <h6>견적 금액</h6>
                <div class="form-floating mb-3">
                    <div class="form-control estcashInput" style="background-color: #f8f9fa; padding: 10px;">
                        ${data.estcash}
                    </div>
                </div>    
                <span>작성 시간 : </span> ${data.cdate}
                
                <div class="card-link receivedEstimates">
                ${loginMemberInfo.role !== "requester" ? //업체랑 개인활동자만 보이게
                    `<a href="/request/view?reqno=${data.reqno}" class="btn btn-primary" style="color: white;">해당 요청글 보기</a>` 
                    : ''}
                
                <div class="card-link receivedEstimates">
                ${loginMemberInfo.role !== "requester" ?  //삭제
                    `<a onclick="estDelete()" class="btn btn-primary" style="color: white;">견적글 삭제하기</a>` 
                    : ''}
            </div>
        `;
        // 결과를 HTML 요소에 삽입
        estviewForm.innerHTML = html;
    })
    .catch(e => {
        console.log(e);
    });
}
// 호출
estView();

const estDelete = () => {
    // 아직 백에서 안돼요
}
