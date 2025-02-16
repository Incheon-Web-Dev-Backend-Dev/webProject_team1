
const jobFindAll = () => {
    fetch('/joboffer/findall.do',{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        if(d != null){
            const list = document.querySelector('tbody')
            let HTML = ``
            
            for (let index = 0; index <= d.length-1; index++){
                let joboffer = d[index]
                HTML += `<tr>
                            <td> ${joboffer.jono}</td>
                            <td> ${joboffer.joservice}</td>
                            <td class="jotitle ${joboffer.jostate? 'success' : ''}"> <a href="/job/view?jono=${joboffer.jono}"> ${joboffer.jotitle} </a> </td>
                            <td> ${joboffer.memberDto.mname}</td>
                            <td> ${joboffer.cdate}</td>
                        </tr>`
            }
            list.innerHTML = HTML;   
        }
        else{document.querySelector('.table').innerHTML = '작성된 구인글이 없습니다.'}
    })
    .catch(e => console.log(e))
};

jobFindAll();

const getMyInfo = () => {
    fetch('/member/myinfo.do',{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        console.log(d)
        if (d.role == 'company'){
            document.querySelector('.bottomMenu').innerHTML += 
                    `<a href="/job/write"><button class="btn btn-secondary btn-lg" type="button">글쓰기</button></a>
                     <a href="/job/mylist"><button class="btn btn-secondary btn-lg" type="button">내가 쓴 글 보기</button></a>`
        }
    })
    .catch(e => console.log(e))
}

getMyInfo();