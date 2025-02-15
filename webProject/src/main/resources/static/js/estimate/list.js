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
            let html = ``;
            data.forEach(estimate =>{
                html +=`
                <div class="card" style="width: 18rem;">
                    <div class="card-body">
                        <a href="/estimate/view?estno=${estimate.estno}"> 
                        <h5 class="card-title">${estimate.esttitle}</h5>
                        </a>
                        <p class="d-inline-block text-truncate" style="max-width: 158px;" class="card-text">${estimate.estcontent}</p>
                        <p class="card-text">금액: ${estimate.estcash}</p>
                        <p class="card-text">${estimate.cdate}</p>
                    </div>
                </div>
                `;
            })
            estCardContent.innerHTML = html;
        })
        .catch(e=>{console.log(e);})

} // f end
findAll();
