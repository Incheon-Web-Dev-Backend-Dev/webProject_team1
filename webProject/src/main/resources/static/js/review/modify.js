console.log("review.modify js open")


// 수정할 이용후기 글의 인덱스(revno)를 전역으로 관리
const urlParams = new URLSearchParams(window.location.search); console.log(urlParams);
const revno = urlParams.get('revno'); console.log(revno);

// 페이지 로드 시 실행되는 함수
document.addEventListener("DOMContentLoaded", function() {
    // 1. 현재 리뷰 정보 가져오기
    loadReviewData();
    
    // 2. 별점 기능 초기화
    initStarRating();
});

// 조회한 이용후기 데이터 불러오기
const loadReviewData = () => {
    fetch(`/review/view.do?revno=${revno}`)
        .then(response => response.json())
        .then(data => {
            console.log("리뷰 데이터:", data);
            
            // DOM 요소 확인 후 데이터 채우기
            const revstarElement = document.getElementById('revstar');
            if (revstarElement) {
                revstarElement.value = data.revstar;
            } else {
                console.error("revstar 요소를 찾을 수 없습니다!");
            }
            
            const revcontentElement = document.querySelector('input[name="revcontent"]');
            if (revcontentElement) {
                revcontentElement.value = data.revcontent;
            } else {
                console.error("revcontent 요소를 찾을 수 없습니다!");
            }
            
            // 기존 이미지 표시 (옵션)
            if(data.revimgList && data.revimgList.length > 0) {
                // 이미지 미리보기 로직 구현 가능
                console.log("기존 이미지:", data.revimgList);
            }
        })
        .catch(error => {
            console.error("리뷰 데이터 로딩 실패:", error);
            alert("리뷰 정보를 불러오는데 실패했습니다.");
        });
};

// 별점 초기화 및 이벤트 리스너 설정
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

// 1. 리뷰 수정 함수
const onWriteBtn = () => {
    console.log("리뷰 수정하기 함수 실행")

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
    console.log('폼 데이터 내용');
    for (let pair of writeFormData.entries()) {
        console.log(pair[0] + ': ' + pair[1]);
    }

    // 3. multipart-form 형식의 fetch 설정
    const option = {
        method : 'PUT',
        body : writeFormData
    }
    
    const isUploadBtn = confirm("이용 후기를 수정 하시겠습니까?");
    if(isUploadBtn == true){
        fetch("/review/modify.do", option)
            .then(response => response.text())
            .then(data => {
                if(data) { 
                    alert("이용후기 작성 완료")
                    // 상세 보기 페이지로 이동
                    window.location.href = `/review/view?revno=${revno}`
                } else {
                    alert("이용후기 작성 실패")
                }// if-else end
            })
            .catch(error => {console.log(error)})
    } else return; // if-else end
}
