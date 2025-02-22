console.log("open")

function listm(){
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
                    <td><a onclick="showOffCanvas('${obj.memail}')">${obj.memail}</a></td>
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
}
listm();

function showOffCanvas(email) {
    // 이메일을 받은 후 필요한 처리를 할 수 있음 (예: 이메일을 offcanvas에 표시)
    const offCanvasBody = document.querySelector('.offcanvas-body');
    offCanvasBody.innerHTML = `<p>이메일: ${email}</p>`

    // 오프캔버스 열기
    const offCanvas = new bootstrap.Offcanvas(document.getElementById('offcanvasScrolling'));
    offCanvas.show();
}