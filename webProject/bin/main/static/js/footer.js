function unlogin(){
    alert("로그인을 먼저 해주세요")
}

//  로그인 정보 요청 함수
function getLoginMemailFoot(){
    

    const option = {method : 'GET'}
    let mlogBox = document.querySelector('.mlogBox')
    let html = '';

    fetch('/member/myinfo.do' , option)
        .then(response => response.json())
        .then(data=> {
            console.log(data);
            console.log("로그인 상태");
            if(data.role == "company"){
                alert("company login")
            }else if(data.role == "master"){
                alert("master login")
            }else if(data.role == "requester"){
                alert("requester login")
            }else{alert("오류발생! 관리자에게 문의바랍니다.")}
        })
        .catch(e => {console.log(e)})
}
getLoginMemailFoot();