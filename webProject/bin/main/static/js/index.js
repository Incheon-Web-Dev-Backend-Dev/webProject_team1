const findTop2Reviews = async () => {
    try {
        const response = await fetch('/review/mainReview.do');
        const data = await response.json();
        const cardBox = document.querySelector(".cardBox");
        cardBox.innerHTML = ""; // 초기화 (중복 방지)

        data.forEach((review) => {
            const stars = Array.from({ length: 5 }, (_, i) =>
                i < review.revstar
                    ? `<i class="fa-solid fa-star"></i>`
                    : `<i class="fa-regular fa-star"></i>`
            ).join("");

            const reviewImg = (review.revimgList?.[0]) || "default.png";
            const shortContent = review.revcontent.length > 100
                ? review.revcontent.slice(0, 100) + "..."
                : review.revcontent;

            const card = `
                <div class="card" style="width: 100%; max-width: 260px;">
                    <img src="/uploads/review/${reviewImg}" class="card-img-top revimgList" alt="리뷰이미지" onerror="this.src='/img/default.png'">
                    <div class="card-body">
                        <p class="card-text revstar">${stars}</p>
                        <p class="card-text">${shortContent}</p>
                    </div>
                </div>
            `;
            cardBox.innerHTML += card;
        });

    } catch (error) {
        console.error("리뷰 데이터를 불러오는 중 오류 발생:", error);
    }
};

findTop2Reviews();
