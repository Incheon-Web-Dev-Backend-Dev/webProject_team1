console.log('request/veiw.js open');

// 개별 요청서 조회
const onRequestFind = () => {
    // 1 .쿼리스트링(요청서 번호)조회
    const urlParams = new URLSearchParams(window.location.search); console.log(urlParams);
    const reqno = urlParams.get('reqno');
    
    console.log(reqno)

    // 2. fetch
    fetch(`/request/find.do?reqno=${reqno}`)
        .then(r => r.json())
        .then(data => {
            console.log(data);
            document.querySelector('.rtitlebox').innerHTML = data.reqtitle; 
            document.querySelector('.rcdate').innerHTML = data.reqdatetime;
            document.querySelector('.rrolebox').innerHTML = data.reqrole == '1'? "업체(2인 이상)" : "개인 (1인)";
            document.querySelector('.rspacebox').innerHTML = data.reqspace;
            document.querySelector('.rbigareabox').innerHTML = data.reqbigarea;
            document.querySelector('.rsmallareabox').innerHTML = data.reqsmallarea;
            document.querySelector('.rcontentbox').innerHTML = data.reqcontent;
            document.querySelector()
        })
        .catch( e => {console.log(e)})
}

onRequestFind();