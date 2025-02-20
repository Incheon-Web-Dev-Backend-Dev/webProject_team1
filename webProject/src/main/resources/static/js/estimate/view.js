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

        // 견적 채택하기 버튼 관련 함수들(채택여부, 마감여부에 따라 속성 설정)
        const getBtnAction = (data) => { // 버튼 기능
            if(data.eststate || !data.reqstate) return ''; // 이미 채택된 견적서이거나 이미 마감된 요청글이면  아무것도 반환하지 않음
            return 'estimateSelect()'; // 위 조건이 아닌 경우에는 estimateSelect함수 실행
        }
        const getBtnClass = (data) => { // 버튼 색
            if(data.estate) return 'btn-success'; // 채택된 견적서
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
            <div class="card-link receivedEstimates">
                ${loginMemberInfo.role == "requester" ?  // 견적 선택하기
                    // eststate 와 reqstate 값에 따라서 버튼 효과 
                    `<a onclick= "${ getBtnAction(data) }"
                    class = "btn ${getBtnClass(data)}"
                    style = "color:white; ${getBtnStyle(data)}"
                    ${getBtnDisabled(data)}>
                    ${getBtnText(data)}
                    </a>`
                : ''}
            </div>
            <div>
                <button type="button" class="btn btn-primary"><a href='/estimate/list?reqno=${data.reqno}' style='color: white;' >목록</a></button>
            </div>
        `;
        // 결과를 HTML 요소에 삽입
        estviewForm.innerHTML = html;
                }
    })
    .catch(e => {
        console.log(e);
    });
}
// 호출
estView();


<<<<<<< HEAD
const estDelete = () => {

    const estno = new URL( location.href ).searchParams.get('estno')
    console.log(estno);

    let result = confirm("게시물을 삭제 하실건가요?")
    if(result == false){return};
    
    fetch(`/estimate/delete?estno=${estno}` , {method : "DELETE"})
    .then(r=>r.json())
    .then(d=>{console.log(d);
        if(d == true){alert("삭제가 완료되었습니다"); window.location.href = "/estimate/mywrote";
        }
    })
    .catch(e=>{console.log(e);})
=======
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
        .then(r => r.json())
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
>>>>>>> jimyung
}
