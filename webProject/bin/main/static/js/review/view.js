// 조회하고있는 이용후기글의 인덱스(revno)를 전역으로 관리
const urlParams = new URLSearchParams(window.location.search); console.log(urlParams);
const revno = urlParams.get('revno'); console.log(revno);


// 1. 이용후기 상세조회
const reviewView = () => {
    console.log("reviewVIew load")


    fetch(`/review/view.do?revno=${revno}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            
            // 1. html 출력할 구역 가져오기
            const contentSection = document.querySelector('.contentSection'); console.log(contentSection);
            const btnSection = document.querySelector('.btnSection'); console.log(btnSection);

            // 2. 출력할 html 저장하는 변수
            let contentHtml = ``;
            let btnHtml = ``;

            // 3. revimgList에 사진이 있으면 각각 변수에 저장
            let revimg1 = '';
            let revimg2= '';
            let revimg3 = '';
            // 이미지 리스트가 있는 경우
            if(data.revimgList && data.revimgList.length > 0) {
                // 이미지 최대 3개
                for(let i = 0; i < data.revimgList.length && i < 3; i++) {
                    // 각 이미지를 해당 변수에 저장
                    if(i === 0) {
                        revimg1 = data.revimgList[0];
                    } else if(i === 1) {
                        revimg2 = data.revimgList[1];
                    } else if(i === 2) {
                        revimg3 = data.revimgList[2];
                    } // if-else end
                }// for end
            }// if end

            // 4. 별점에 따른 아이콘 생성
            let starHtml = '';
            for(let i = 0; i < data.revstar; i++) {
                starHtml += `<i class="fa-solid fa-star"></i>`;
            }
            for(let i = data.revstar; i < 5; i++) {
                starHtml += `<i class="fa-regular fa-star"></i>`;
            }

            console.log(revimg1);
            console.log(revimg2);
            console.log(revimg3);

            // 5. review 상세 내용 대입
            contentHtml = `
                <div class="mb-3">
                    <div class="rtitlesection">
                        <label for="basic-url" class="form-label rtitle">${data.estWriterName}님 수납/정리 건에 대한 이용후기입니다.</label>
                    </div>
                    <div class="rstarsection">
                        <label for="basic-url" class="form-label">별점</label>
                        <p class="form-control rstar">${starHtml}</p>
                    </div>
                    <div class="rcontentsection">
                        <label for="basic-url" class="form-label">이용후기 내용</label>
                        <img src="/img/review/${revimg1}" class="card-img-top revimgList" alt="리뷰이미지" onerror="this.src='/img/WebProjectLogo.png'">
                        <p class="form-control rcontent">${data.revcontent}</p>
                    </div>
                    <div class="rcdatesection">
                        <label for="basic-url" class="form-label">이용후기 작성 날짜</label>
                        <p class="form-control rcdate">${data.revcdate}</p>
                    </div>
                </div>
            `;
            
            // contentSection 내용 업데이트
            contentSection.innerHTML = contentHtml;

            console.log('loginMemberInfo.mno '+loginMemberInfo.mno);
            console.log('data.mno '+data.mno);

            // 6. review 작성자가 조회할 경우에 수정, 삭제 버튼 생성
            if(loginMemberInfo.mno === data.mno) {
                btnHtml = `
                    <button type="button" class="btn" onclick="modifyReview(${revno})">수정하기</button>
                    <button type="button" class="btn" onclick="deleteReview()">삭제하기</button>
                `;
            }
            
            // btnSection 내용 업데이트
            btnSection.innerHTML = btnHtml;
        })
        .catch( error => {
            console.log(error)
        })
}

reviewView();



// 2. 이용후기 삭제 기능
const deleteReview = () => {
    console.log("deleting review with revno  : " + revno);
    let isDelete = confirm("해당 이용후기 글을 삭제 하실건가요?");
    if(isDelete ==  false) return;

    // fetch
    fetch(`/review/delete.do?delRevno=${revno}`, {method : 'DELETE'})
        .then(response => response.json())
        .then(result => {
            console.log('delete review result' + result);;

            if(result == true){// 삭제 성공시 alert
                alert("이용 후기글이 삭제 되었습니다.")
                window.location.href = '/review/list'; // 삭제 성공하고 페이지 이동
            } else {
                alert("이용 후기글 삭제가 실패하였습니다.") // 삭제 실패시 alert
            } // if-else end
        })
        .catch(error => {
            console.log('delete review result (error)'+ error);
            alert("이용 후기글 삭제 중 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        })
}

// 3. 이용후기 수정 기능
const modifyReview  = (revno) => {
    console.log("수정페이지로 이동")
    window.location.href = `/review/modify?revno=${revno}`
}