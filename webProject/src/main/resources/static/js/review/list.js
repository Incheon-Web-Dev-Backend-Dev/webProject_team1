// 1. 리뷰 전체조회 함수
const reviewFindAll = () => {
    console.log("리뷰 전체조회 함수 실행");
    fetch('/review/viewall.do', {method: 'GET'})
        .then( response => response.json()) // Review 목록에 포함된 이미지를 같이 가져오므로 text
        .then( data => {
            console.log(data);

            // 1. html 출력할 구역 가져오기
            const cardBox = document.querySelector('.cardBox'); console.log(cardBox);

            // 2. 출력할 html 저장하는 변수 선언
            let html = ``;

            // 3. 응답 자료 박복문을 이용해서 html 누적 더해서 출력하기
            data.forEach ( list => {
                if(list.revno > 0) { // 리뷰가 존재하면 보여주기
                    // 1) 리뷰데이터에 이미지가 있으면 첫번째 이미지 하나만 추출하기
                    let reviewImg = '';
                    if(list.revimgList && list.revimgList.length >0) { // 이미자가 존재하면
                        reviewImg = list.revimgList[0];
                    }

                    // 2) 별점에 따른 아이콘 생성
                    let starHtml = '';
                    for(let i = 0; i < list.revstar; i++) {
                        starHtml += '<i>';
                    }
                    for(let i = list.revstar; i < 5; i++) {
                        starHtml += '☆';
                    }

                    html += `
                        <div class="card" style="width: 16rem;">
                            ${reviewImg ? `<img src="${reviewImg}" class="card-img-top revimgList" alt="리뷰이미지">` : ''}
                            <div class="card-body">
                                <p class="card-text revstar">${list.revstar}</p>
                                <p class="card-text revcontent"><a href="/review/view?revno=${list.revno}>${list.revcontent}</a></p>
                            </div>
                        </div>
                    `
                }// if end
            })// foreach end
            cardBox.innerHTML = html;
        })
        .catch( error => {console.log(error)})
} // reviewFindAll end

reviewFindAll();

// 리뷰 별점 숫자에 따른 아이콘 출력 기능
const makeStar = () => {

}