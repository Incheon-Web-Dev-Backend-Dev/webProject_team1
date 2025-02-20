console.log("open")

fetch("/manage/memberlist.do", { method: "GET" })
    .then(r => r.json())
    .then(d => {
        console.log(d);
            const tbody = document.querySelector('tbody')
            let html = '';
            let memberList = d;
            memberList.forEach((obj) => {
                if (obj.role === 'admin') {
                    return; // admin일 경우 이 항목을 건너뜀
                }
                html += `<tr>
                    <td>${obj.mno}</td>
                    <td>${obj.memail}</td>
                    <td>${obj.mname}</td>
                    <td>${obj.maddr}</td>
                    <td>${obj.mphone}</td>
                    <td>${obj.role}</td>
                    <td>${obj.isapproved}</td>
                </tr>`;
            });
            
            // 결과를 테이블에 넣기
            tbody.innerHTML = html;
        
    })
    .catch(e => { console.log(e); });
