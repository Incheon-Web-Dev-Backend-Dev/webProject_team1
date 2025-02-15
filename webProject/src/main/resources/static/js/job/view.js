const getMyInfo = () => {
    fetch('/member/myinfo.do',{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        console.log(d);
        jobFind(d.mno, d.role); // 게시글 조회 함수에 로그인된 객체의 mno,role 전달
        })
    .catch(e => console.log(e))
}

getMyInfo();

const jobFind = (mno, role) => {

    const jono = new URL(location.href).searchParams.get('jono');

    fetch(`/joboffer/find.do?jono=${jono}`,{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        console.log(d)
        document.querySelector('.mnamebox').innerHTML = d.memberDto.mname;

        document.querySelector('.jotitle').innerHTML = d.jotitle;
        
        if(d.jostate == true){
            document.querySelector('.jostate').innerHTML = '마감';
            } else {document.querySelector('.jostate').innerHTML = '모집 중';};
        
        document.querySelector('.jocontent').innerHTML = d.jocontent;

        document.querySelector('.maddrbox').innerHTML = d.memberDto.maddr;
        document.querySelector('.cdatebox').innerHTML = d.cdate;
        
        if(mno == d.memberDto.mno){
        // 만약 로그인한 memail 과 글작성자의 memail이 같으면 마감상태변경/수정/삭제 버튼 출력
        document.querySelector('.bottomMenu').innerHTML = 
        `<button class="btn btn-secondary btn-lg" type="button" onclick="stateUpdate()">마감상태 변경</button>
         <a href="/job/update"><button class="btn btn-secondary btn-lg" type="button">수정</button></a>
         <button class="btn btn-secondary btn-lg" type="button" onclick="offerDelete()">삭제</button></a>`
        }
        // 로그인한 유저의 role이 master일 때 좋아요 버튼 출력
        if(role == 'master'){
            likeFind(mno, jono);
        }
    }
        )
    .catch(e => console.log(e))
}

const offerDelete = () => {
    // 현재 구인글의 jono 받아오기
    const jono = new URL(location.href).searchParams.get('jono');
    // 삭제하기전 삭제여부 확인
    let result = confirm('삭제 하시겠습니까?');
    if( result == false ) { return; }

    fetch(`/joboffer/delete.do?jono=${jono}`,{method : 'DELETE'})
    .then(r => r.json())
    .then(d => {
        console.log(d)
        if( d == true ){
            alert("삭제 성공")
            location.href='/';
        } else {'삭제 실패'}
    })
    .catch(e => {console.log(e); alert('삭제 실패')})
}

const stateUpdate = () => {
    const jono = new URL(location.href).searchParams.get('jono');
    
    let result = confirm('변경 하시겠습니까?');
    if( result == false ) { return; }
    
    fetch(`/joboffer/stateupdate.do?jono=${jono}`, {method : 'PUT'})
    .then(r => r.json())
    .then(d => {
        console.log(d)
        if( d == true ){
            alert("변경 성공");     window.location.reload();
        } else{'변경 실패'};    window.location.reload();

    })
    .catch(e => {console.log(e); alert('변경 실패')})
}

const likeFind = (mno, jono) => {

    fetch(`/like/find.do?mno=${mno}&jono=${jono}`,{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        if (d == 0){
            document.querySelector('.bottomMenu').innerHTML +=
            `<button class="btn btn-secondary btn-lg like" onclick="likePost()" type="button">좋아요</button>`;
        } 
        if (d == 1){
            document.querySelector('.bottomMenu').innerHTML +=
            `<button class="btn btn-secondary btn-lg like" onclick="likeUpdate()" type="button">좋아요</button>`;
        }
        if (d == 2){
            document.querySelector('.bottomMenu').innerHTML +=
            `<button class="btn btn-secondary btn-lg like" onclick="likeUpdate()" type="button">눌렀음</button>`;
        }
    })
    .catch(e => console.log(e))
}


const likePost = () => {
    const jono = new URL(location.href).searchParams.get('jono');
    
    const option = 
        { method : 'POST',
            headers : {'Content-Type' : 'application/json'},
            body : JSON.stringify({jono:jono})
        };

    fetch(`/like/post.do`,option)
    .then(r => r.json())
    .then(d => {
        console.log(d);
        if (d == true){alert('지원 했습니다.')
            window.location.reload();
        } else {alert('지원 실패했습니다.')}
    })
    .catch(e => console.log(e))
}

const likeUpdate = () => {
    const jono = new URL(location.href).searchParams.get('jono');

    fetch(`/like/update.do?jono=${jono}`, {method : 'PUT'})
    .then(r => r.json())
    .then(d => {
        console.log(d);
        if (d == true){alert('변경 했습니다.');
            window.location.reload();
        } else {alert('변경 실패했습니다')}
    })
    .catch(e => console.log(e))
}