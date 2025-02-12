console.log("findAll.js open")

const reqno = 2;

// 요청글에 대한 전체 견적 조회 함수
const findAll = () => {
    const option = {method : 'GET'}

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
                        <h5 class="card-title">${estimate.esttitle}</h5>
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