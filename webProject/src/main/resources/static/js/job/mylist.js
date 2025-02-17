

function mylist(){
    fetch('/joboffer/mylist.do',{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        console.log(d)
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
        else{document.querySelector('.table').innerHTML = '작성한 구인글이 없습니다.'}
    })
    .catch(e => console.log(e))
};

mylist();