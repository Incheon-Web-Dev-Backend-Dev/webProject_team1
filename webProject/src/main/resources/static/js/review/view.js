// 이용후기 상세조회
const reviewView = () => {
    console.log("reviewVIew load")

    const urlParams = new URLSearchParams(window.location.search); console.log(urlParams);
    const revno = urlParams.get('revno'); console.log(revno);

    fetch(`/review/view.do?revno=${revno}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);
            
            // 1. html 출력할 구역 가져오기
            const revViewContent = document.querySelector('.revViewContent'); console.log(revViewContent);
        
            // 2. 출력할 html 저장하는 변수
            let html = ``;

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
            for(let i = 0; i < list.revstar; i++) {
                starHtml += `<i class="fa-solid fa-star"></i>`;
            }
            for(let i = list.revstar; i < 5; i++) {
                starHtml += `<i class="fa-regular fa-star"></i>`;
            }

            // 5. 대입
            html += `
                <div class="revViewContent">
                    <div class="mb-3">
                        <div class="rtitlesection">
                            <p class="form-control rtitle">${data.estWriterName}님 수납/정리 건에 대한 이용후기입니다.</p>
                        </div>
                        <div class="rstarsection">
                            <label for="basic-url" class="form-label">별점</label>
                            <p class="form-control rstar">${starHtml}</p>
                        </div>
                        <div class="rcontentsection">
                            <label for="basic-url" class="form-label">이용후기 내용</label>
                            <p class="form-control rimg1"></p>
                            <p class="form-control rimg2"></p>
                            <p class="form-control rimg3"></p>
                            <p class="form-control rcontent"></p>
                        </div>
                        <div class="rcdatesection">
                            <label for="basic-url" class="form-label">이용후기 작성 날짜</label>
                            <p class="form-control rcdate"></p>
                        </div>
                    </div>
                </div>
            `;
        })
}

reviewView();