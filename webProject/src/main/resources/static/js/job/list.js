function jobFindAll(){
    fetch('/joboffer/findall.do',{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        const list = document.querySelector('tbody')
        let HTML = ``
        
        for (let index = 0; index <= d.length-1; index++){
            let joboffer = d[index]
            HTML += `<tr>
                        <td> ${joboffer.jono}</td>
                        <td> <a href="/job/view?jono=${joboffer.jono}"> ${joboffer.jotitle} </a> </td>
                        <td> ${joboffer.memberDto.mname}</td>
                        <td> ${joboffer.cdate}</td>
                    </tr>`
        }
        list.innerHTML = HTML;   
    })
    .catch(e => console.log(e))
};

jobFindAll();
