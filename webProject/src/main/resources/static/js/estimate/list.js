console.log("findAll.js open")

// * 현재 URL 의 쿼리스트링 매개변수 가져오기
// 현재 페이지의 URL 정보가 담긴 객체 생성
console.log( new URL(location.href))
// 현재 페이지의 URL 쿼리스트링 정보 속성 반환
console.log( new URL(location.href).searchParams)
// 현재 페이지의 URL 쿼리스트링 속성 중 'eno' 속성값 반환
console.log( new URL(location.href).searchParams.get('reqno'))

// 요청글에 대한 전체 견적 조회 함수
const findAll = () => {

    

    const option = {method : 'GET'}

    const reqno = new URL(location.href).searchParams.get('reqno');

    fetch(`/estimate/findall.do?reqno=${reqno}`,option)
        .then(r => r.json())
        .then(data =>{
            console.log(data);
            const estCardContent = document.querySelector(".estFindAllCardBox");
            
            // 요청서 마감 상태에 따른 뱃지 상태 반환하는 함수들
            const getBadgeClass = (estimate) => {
                if(!estimate.reqstate) {// 요청글이 마감된 경우
                    return estimate.eststate ? 'text-bg-success' : 'text-bg-danger';
                } // if end
                return 'text-bg-secondary'; // 요청글이 마감되지 않은 경우
            }
            const getBadgeText = (estimate) => {
                if (!estimate.reqstate) {  // 요청글이 마감된 경우
                    return estimate.eststate ? '채택' : '미채택';
                }
                return '진행중';  // 요청글이 마감되지 않은 경우
            }

            // 요청서 마감 상태에 따라 가트 배경색을 반환하는 함수
            const getCardStyle = (estimate) => {
                if (!estimate.reqstate) {  // 요청글이 마감된 경우
                    return 'background-color: #f8f9fa;';  // 부트스트랩의 옅은 회색
                }
                return '';  // 마감되지 않은 경우 기본 배경색
            }
            
            
            let html = ``;
            data.forEach(estimate =>{
                if(estimate.mno>0){
                html +=`
                <div class="card" style="width: 32rem; ${getCardStyle(estimate)}">
                    <div class="card-body">
                        <div class="card-title-content">
                            <h5 class="card-title"><a href="/estimate/view?estno=${estimate.estno}">${estimate.esttitle}</a></h5>
                            <p class="card-text">${estimate.estcontent}</p>
                            <h4><span class="badge ${getBadgeClass(estimate)}">${getBadgeText(estimate)}</span></h4>
                        </div>
                        <div>
                            <p class="card-text">금액: ${estimate.estcash}</p>
                        </div>
                    </div>
                </div>
                `;
                }
            })
            estCardContent.innerHTML = html;
        })
        .catch(e=>{console.log(e);})

} // f end
findAll();
