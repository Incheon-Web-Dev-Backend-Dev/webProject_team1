console.log("review.write js open")


// 별점 관련 기능 추가
document.addEventListener("DOMContentLoaded", function() {
    const stars = document.querySelectorAll('.star-rating .star');
    const ratingInput = document.getElementById('revstar');
    
    // 초기 설정 - 모든 별 비활성화
    stars.forEach(star => {
        star.classList.remove('fa-solid');
        star.classList.add('fa-regular');
    });
    
    // 별점 클릭 이벤트
    stars.forEach(star => {
        star.addEventListener('click', function() {
            const rating = this.getAttribute('data-rating');
            ratingInput.value = rating;
            
            // 별 모양 업데이트
            updateStars(rating);
        });
        
        // 마우스 오버 효과
        star.addEventListener('mouseover', function() {
            const rating = this.getAttribute('data-rating');
            highlightStars(rating);
        });
        
        // 마우스 아웃 효과
        star.addEventListener('mouseout', function() {
            // 현재 선택된 별점으로 돌아가기
            updateStars(ratingInput.value);
        });
    });
    
    // 별점 표시 함수
    function updateStars(rating) {
        stars.forEach(star => {
            const starRating = star.getAttribute('data-rating');
            if (starRating <= rating) {
                star.classList.remove('fa-regular');
                star.classList.add('fa-solid');
            } else {
                star.classList.remove('fa-solid');
                star.classList.add('fa-regular');
            }
        });
    }
    
    // 마우스 오버 시 별점 표시 함수
    function highlightStars(rating) {
        stars.forEach(star => {
            const starRating = star.getAttribute('data-rating');
            if (starRating <= rating) {
                star.classList.remove('fa-regular');
                star.classList.add('fa-solid');
            } else {
                star.classList.remove('fa-solid');
                star.classList.add('fa-regular');
            }
        });
    }
});





// 1. 리뷰 작성하기 함수
const onWriteBtn = () => {
    console.log("리뷰 작성하기 함수 실행")

    // 별점 확인
    const revstar = document.getElementById('revstar').value;
    if (revstar === '0') {
        alert('별점을 선택해주세요.');
        return;
    }

    // 1. 전송할 form dom 객체를 가져옴
    const writeForm = document.querySelector(".writeBox");
    console.log(writeForm);

    // 2. form dom 객체를 byte로 변환
    const writeFormData = new FormData(writeForm);
    console.log(writeFormData);

    // 3. multipart-form 형식의 fetch 설정
    const option = {
        method : 'POST',
        body : writeFormData
    }
    
    const isUploadBtn = confirm("이용 후기를 올리시겠습니까?");
    if(isUploadBtn == true){
        fetch("/review/write.do", option)
            .then(response => response.json())
            .then(data => {
                if(data == true) {
                    alert("이용후기 작성 완료")
                    // review.view 로 경로이동하기
                } else {
                    alert("이요후기 작성 식패")
                }// if-else end
            })
            .catch(error => {console.log(error)})
    } else return; // if-else end
}