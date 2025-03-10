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
            document.querySelector('.jostate').innerHTML = '지원마감❌';
            document.querySelector('.jostate').style.color = 'coral';
            } else {
                document.querySelector('.jostate').innerHTML = '지원가능⭕';
                document.querySelector('.jostate').style.color = 'blue';
            };
        
        document.querySelector('.jocontent').innerHTML = d.jocontent;
        
        var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
        mapOption = { 
            center: new kakao.maps.LatLng(`${d.latitude}`,`${d.longitude}`), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

        var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

        // 마커가 표시될 위치입니다 
        var markerPosition  = new kakao.maps.LatLng(`${d.latitude}`, `${d.longitude}`); 

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
            position: markerPosition
        });

        // 마커가 지도 위에 표시되도록 설정합니다
        marker.setMap(map);

        document.querySelector('#joaddr').innerHTML = `주소 : ${d.joaddr}`;

        document.querySelector('.maddrbox').innerHTML = d.memberDto.maddr;
        document.querySelector('.mphonebox').innerHTML = d.memberDto.mphone;
        document.querySelector('.cdatebox').innerHTML = d.cdate;
        
        if(mno == d.memberDto.mno){
        // 만약 로그인한 memail 과 글작성자의 memail이 같으면 마감상태변경/수정/삭제 버튼 출력
        document.querySelector('.bottomMenu').innerHTML += 
        `<a href="/job/like" class="card-link">지원자 : ${d.likeCount}명</a><br/>
         <button class="buttonsel" type="button" onclick="stateUpdate()">마감상태 변경</button>
         <a href="/job/update"><button class="buttonsel" type="button">수정</button></a>
         <button class="buttonsel" btn-lg" type="button" onclick="offerDelete()">삭제</button></a>`
        }
        // 로그인한 유저의 role이 master일 때 좋아요 버튼 출력을 위해 likeFind() 함수 호출
        if(role == 'master' && d.jostate == false){
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

// 구인글 내용이 아닌 마감여부만 간단하게 변경
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

// 지금 로그인한 회원이 이 구인글에 구직신청을 했는지 확인
const likeFind = (mno, jono) => {

    fetch(`/like/find.do?mno=${mno}&jono=${jono}`,{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        // 현재 지원 상태에 따른 버튼 출력
        if (d == 0){
            // 한번도 지원한적이 없으면 likePost() 함수를 호출하는 좋아요 버튼 출력 -> DB에 한번도 저장이 안된 경우
            document.querySelector('.bottomMenu').innerHTML +=
            `<button class="buttonsel btn-lg like" onclick="likePost()" type="button">지원하기</button>`;
        } 
        if (d == 1){
            // 한번 지원했다가 취소했던 구인글인 경우 likePost()가 아닌 likeUpdate()로 신청 여부만 변경 -> 이전에 DB에 저장이 한번 됐던 경우
            document.querySelector('.bottomMenu').innerHTML +=
            `<button class="buttonsel btn-lg like" onclick="likeUpdate()" type="button">지원하기</button>`;
        }
        if (d == 2){
            // 지원한 구인글인 경우 버튼 이름을 '눌렀음'으로 변경하여 내가 지원했는지 구별가능하며 likeUpdate()로 지원취소 가능
            document.querySelector('.bottomMenu').innerHTML +=
            `<button class="buttonsel btn-lg like" onclick="likeUpdate()" type="button">지원 중</button>`;
        }
    })
    .catch(e => console.log(e))
}

const likePost = () => {
    const jono = new URL(location.href).searchParams.get('jono');
    
    const option = 
        { method : 'POST',  // 현재 구인글에 처음으로 지원하는 경우 POST로 DB에 처음 구직상태 저장
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
    // 구직신청을 한 상태이거나, 한번 취소했던 경우 PUT 으로 재지원/지원취소 가능하게 수정
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