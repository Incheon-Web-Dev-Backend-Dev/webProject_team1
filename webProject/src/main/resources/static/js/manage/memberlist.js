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
                    <td><a onclick="showOffCanvas('${obj.mno}')">${obj.memail}</a></td>
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

const showOffCanvas = (mno) => {
    fetch(`/manage/memberview.do?mno=${mno}`, { method: 'GET' })
        .then(response => response.json())
        .then(data => {
            const offcanvasBody = document.querySelector('.offcanvas-body');
            let html = `
                <p><strong>회원코드:</strong> ${data.mno}</p>
                <p><strong>이름:</strong> ${data.mname}</p>
                <p><strong>주소:</strong> ${data.maddr}</p>
                <p><strong>전화번호:</strong> ${data.mphone}</p>
                <p><strong>역할:</strong> ${data.role}</p>
                <p><strong>승인여부:</strong> ${data.isapproved}</p>
                `;
                offcanvasBody.innerHTML = html;
                // Bootstrap Offcanvas 초기화 및 표시
            var myOffcanvas = new bootstrap.Offcanvas(document.getElementById('offcanvasScrolling'));
            myOffcanvas.show();
        })
        .catch(error => console.error('Error fetching patient details:', error));
}