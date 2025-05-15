// 1. 리뷰 전체조회 함수
const reviewFindAll = () => {
    console.log("리뷰 전체조회 함수 실행");
    fetch('/review/viewall.do', { method: 'GET' })
        .then(response => response.json())
        .then(data => {
            console.log(data);

            const cardBox = document.querySelector('.cardBox');
            let html = ``;

            data.forEach(list => {
                if (list.revno > 0) {
                    let reviewImg = '';
                    if (list.revimgList && list.revimgList.length > 0) {
                        reviewImg = list.revimgList[0];
                    }
                    console.log("리뷰이미지 파일명: " + reviewImg);

                    let starHtml = '';
                    for (let i = 0; i < list.revstar; i++) {
                        starHtml += `<i class="fa-solid fa-star"></i>`;
                    }
                    for (let i = list.revstar; i < 5; i++) {
                        starHtml += `<i class="fa-regular fa-star"></i>`;
                    }

                    html += `
                        <div class="card" style="width: 16rem;">
                            ${reviewImg ?
                            `<img src="/uploads/review/${reviewImg}" class="card-img-top revimgList" alt="리뷰이미지" onerror="this.src='/img/WebProjectLogo.png'">` :
                            `<img src="/img/WebProjectLogo.png" class="card-img-top revimgList" alt="기본 이미지">`}
                            <div class="card-body">
                                <p class="card-text revstar">${starHtml}</p>
                                <p class="card-text revcontent">
                                    <a href="/review/view?revno=${list.revno}">
                                        ${list.revcontent.substring(0, 100)}${list.revcontent.length > 100 ? '...' : ''}
                                    </a>
                                </p>
                            </div>
                        </div>
                    `;
                }
            });
            cardBox.innerHTML = html;
        })
        .catch(error => {
            console.log(error);
        });
};

reviewFindAll();
