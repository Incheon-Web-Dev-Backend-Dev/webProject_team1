console.log('request/veiw.js open');

// 카카오 지도
// ------------------------------------------------

// ------------------------------------------------

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

            // 1). 버튼 조건부 출력할 html을 저장하고 출력할 구역도 가져오기
            let html = ``;
            const uploadBtnBox = document.querySelector(".uploadBtnBox");
            


            // 2). 글의 내용을 받아와서 그대로 출력
            document.querySelector('.rtitlebox').innerHTML = data.reqtitle; 
            document.querySelector('.rcdate').innerHTML = data.reqdatetime;
            document.querySelector('.rrolebox').innerHTML = data.reqrole == '1'? "업체(2인 이상)" : "개인 (1인)";
            document.querySelector('.rspacebox').innerHTML = data.reqspace;
            document.querySelector('.raddress').innerHTML = data.raddress;
            // 카카오 지도
            var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
                mapOption = { 
                    center: new kakao.maps.LatLng(`${data.latitude}`, `${data.longitude}`), // 지도의 중심좌표
                    level: 3 // 지도의 확대 레벨
                };  

            var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

            // 마커가 표시될 위치입니다 
            var markerPosition  = new kakao.maps.LatLng(`${data.latitude}`, `${data.longitude}`); 

            // 마커를 생성합니다
            var marker = new kakao.maps.Marker({
                position: markerPosition
            });

            // 마커가 지도 위에 표시되도록 설정합니다
            marker.setMap(map);
            // 카카오 지도 끝
            document.querySelector('.rcontentbox').innerHTML = data.reqcontent;
            document.querySelector('.rdeadlinebox').innerHTML = data.deadLineTime; // 요청글 마감시간
            


            console.log("data.reqrole", data.reqrole);

            // 3). 유저 정보(role)에 따라서 button 보이기 여부
            if(loginMemberInfo.role === "master" || loginMemberInfo.role === "company"){
                html += `
                    <button class="btn btn-primary"><a href="/estimate/write">이 요청글에 견적서 올리기</a></button>
                `
            }// if end

            uploadBtnBox.innerHTML = html;
        })
        .catch( e => {console.log(e)})
}

onRequestFind();





