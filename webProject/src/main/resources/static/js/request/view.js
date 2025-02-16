console.log('request/veiw.js open');

// 개별 요청서 조회
const onRequestFind = () => {
    // 1 .쿼리스트링(요청서 번호)조회
    const urlParams = new URLSearchParams(window.location.search); console.log(urlParams);
    const reqno = urlParams.get('reqno'); console.log("reqno", reqno);

    // 2. fetch
    fetch(`/request/find.do?reqno=${reqno}`)
        .then(r => r.json())
        .then(data => {
            console.log(data);
            console.log("로그인회원정보", loginMemberInfo);

            // 2). 버튼 조건부 출력할 html을 저장하고 출력할 구역도 가져오기
            let html = ``;
            const uploadBtnBox = document.querySelector(".uploadBtnBox");


            // 3). 글의 내용을 받아와서 그대로 출력
            document.querySelector('.rtitlebox').innerHTML = data.reqtitle; 
            document.querySelector('.rcdate').innerHTML = data.reqdatetime;
            document.querySelector('.rrolebox').innerHTML = data.reqrole == '1'? "업체(2인 이상)" : "개인 (1인)";
            document.querySelector('.rspacebox').innerHTML = data.reqspace;
            document.querySelector('.rbigareabox').innerHTML = data.reqbigarea;
            document.querySelector('.rsmallareabox').innerHTML = data.reqsmallarea;
            document.querySelector('.rcontentbox').innerHTML = data.reqcontent;


            console.log("data.reqrole", data.reqrole);

            // 4). 유저 정보(role)에 따라서 button 보이기 여부
            if(loginMemberInfo.role === "master" || loginMemberInfo.role === "company"){
                html += `
                    <button class="onUploadEstimateBtn()"><a href="/estimate/write" class="btn btn-primary">이 요청글에 견적서 올리기</a></button>
                `
            }// if end

            uploadBtnBox.innerHTML = html;
        })
        .catch( e => {console.log(e)})
}

onRequestFind();