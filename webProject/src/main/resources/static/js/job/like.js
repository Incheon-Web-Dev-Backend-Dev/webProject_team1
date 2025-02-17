const likeList = () => {
    // 이전 페이지의 URL을 가져옵니다.
    const referrerUrl = document.referrer;

    // URLSearchParams를 사용하여 쿼리 파라미터를 추출합니다.
    const referrerParams = new URLSearchParams(new URL(referrerUrl).search);
    
    // jono 파라미터 값을 가져옵니다.
    const jono = referrerParams.get('jono');

    fetch(`/like/list.do?jono=${jono}`, {method : 'GET'})
    .then(r => r.json())
    .then(d => {
        console.log(d);
            const likeCardContent = document.querySelector(".likeFindAllCardBox");
            let html = ``;
            d.forEach(member =>{
                html +=`
                <div class="card" style="width: 18rem;">
                    <div class="card-body">
                        <h5 class="card-title">${member.mname}</h5>
                        <p class="card-text">${member.profile}</p>
                        <p class="card-text">${member.mphone}</p>
                        <p class="card-text">${member.maddr}</p>
                    </div>
                </div>
                `;
            })
            likeCardContent.innerHTML = html;
        })
    .catch(e => console.log(e))
}

likeList();