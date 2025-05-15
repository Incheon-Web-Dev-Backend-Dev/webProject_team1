const urlParams = new URLSearchParams(window.location.search);
const revno = urlParams.get('revno');

const reviewView = () => {
    console.log("reviewView load");

    fetch(`/review/view.do?revno=${revno}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);

            const contentSection = document.querySelector('.contentSection');
            const btnSection = document.querySelector('.btnSection');

            let contentHtml = ``;
            let btnHtml = ``;

            let revimg1 = '';
            let revimg2 = '';
            let revimg3 = '';

            if (data.revimgList && data.revimgList.length > 0) {
                for (let i = 0; i < data.revimgList.length && i < 3; i++) {
                    if (i === 0) revimg1 = data.revimgList[0];
                    else if (i === 1) revimg2 = data.revimgList[1];
                    else if (i === 2) revimg3 = data.revimgList[2];
                }
            }

            let starHtml = '';
            for (let i = 0; i < data.revstar; i++) {
                starHtml += `<i class="fa-solid fa-star"></i>`;
            }
            for (let i = data.revstar; i < 5; i++) {
                starHtml += `<i class="fa-regular fa-star"></i>`;
            }

            console.log(revimg1, revimg2, revimg3);

            contentHtml = `
                <div class="mb-3">
                    <div class="rtitlesection">
                        <label class="form-label rtitle">${data.estWriterName}님 수납/정리 건에 대한 이용후기입니다.</label>
                    </div>
                    <div class="rstarsection">
                        <label class="form-label">별점</label>
                        <p class="form-control rstar">${starHtml}</p>
                    </div>
                    <div class="rcontentsection">
                        <label class="form-label">이용후기 내용</label>
                        ${revimg1 ? `<img src="/uploads/review/${revimg1}" class="card-img-top revimgList" alt="리뷰이미지" onerror="this.src='/img/WebProjectLogo.png'">` : ''}
                        <p class="form-control rcontent">${data.revcontent}</p>
                    </div>
                    <div class="rcdatesection">
                        <label class="form-label">이용후기 작성 날짜</label>
                        <p class="form-control rcdate">${data.revcdate}</p>
                    </div>
                </div>
            `;

            contentSection.innerHTML = contentHtml;

            if (loginMemberInfo.mno === data.mno) {
                btnHtml = `
                    <button type="button" class="btn" onclick="modifyReview(${revno})">수정하기</button>
                    <button type="button" class="btn" onclick="deleteReview()">삭제하기</button>
                `;
            }
            btnSection.innerHTML = btnHtml;
        })
        .catch(error => {
            console.log(error);
        });
};

reviewView();

// 후기 삭제 기능
const deleteReview = () => {
    console.log("deleting review with revno  : " + revno);
    let isDelete = confirm("해당 이용후기 글을 삭제 하실건가요?");
    if (!isDelete) return;

    fetch(`/review/delete.do?delRevno=${revno}`, { method: 'DELETE' })
        .then(response => response.json())
        .then(result => {
            console.log('delete review result' + result);
            if (result === true) {
                alert("이용 후기글이 삭제 되었습니다.");
                window.location.href = '/review/list';
            } else {
                alert("이용 후기글 삭제가 실패하였습니다.");
            }
        })
        .catch(error => {
            console.log('delete review result (error)' + error);
            alert("이용 후기글 삭제 중 오류가 발생하였습니다. 관리자에게 문의해주세요.");
        });
};

// 후기 수정 기능
const modifyReview = (revno) => {
    console.log("수정페이지로 이동");
    window.location.href = `/review/modify?revno=${revno}`;
};
