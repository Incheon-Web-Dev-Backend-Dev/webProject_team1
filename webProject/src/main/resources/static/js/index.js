const findTop2Reviews = () => {
    fetch('/review/mainReview.do')
        .then(response => response.json())
        .then(data => {
            console.log(data);

            const cardBox = document.querySelector(".cardBox");

            let html = ``;
            data.forEach( review =>{
                // 별점 표시 HTML 생성
                let starHtml = '';
                for (let i = 0; i < review.revstar; i++) {
                    starHtml += `<i class="fa-solid fa-star"></i>`;
                }
                for (let i = review.revstar; i < 5; i++) {
                    starHtml += `<i class="fa-regular fa-star"></i>`;
                }

                // 리뷰 카드 HTML 생성
                let reviewImg = '';
                if (review.revimgList && review.revimgList.length > 0) {
                    reviewImg = review.revimgList[0];
                }

                html += `
                <div class="card" style="width: 16rem;">
                    <img src="/img/review/${reviewImg}" class="card-img-top revimgList" alt="리뷰이미지" onerror="this.src='/img/default.png'">
                    <div class="card-body">
                        <p class="card-text revstar">${starHtml}</p>
                        <p class="card-text">${review.revcontent.substring(0, 100)}${review.revcontent.length > 100 ? '...' : ''}</p>
                    </div>
                </div>
                `;

            });
            cardBox.innerHTML += html;
        })
        .catch(error => {console.log("mainTopReviews error" + error)})
}

findTop2Reviews();