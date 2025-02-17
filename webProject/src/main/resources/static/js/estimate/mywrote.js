const mywrote = () => {
    console.log("내가 작성한 견적서 전체조회 함수 실행 ")
    
    const option = {method : 'GET'}

    fetch('/estimate/mywrote.do' , option)
        .then(r => r.json())
        .then(data => {
            console.log(data);

            const estCardContent = document.querySelector(".estListCardBox")

            let html =``;

            data.forEach(list=>{
                html += `
                
                <div class="card" style="width: 32rem;">
                    <div class="card-body">
                        <div class="card-content cardbox">
                            <div>
                                <h6 class="card-subtitle mb-2 text-body-secondary"><a href="/estimate/view?estno=${list.estno}">${list.esttitle}</a></h6>
                                <h5 class="card-title">${list.estcontent}</h5>
                            </div>
                        </div>
                    </div>
                </div>               
            
            `              
            })
            estCardContent.innerHTML=html;
        })
        .catch(e => {console.log(e);})
}
mywrote()