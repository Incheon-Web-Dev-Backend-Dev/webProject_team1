// * 썸머노트 실행
$(document).ready(function() {
    $('#summernote').summernote({
      height : 500 , // 썸머노트 게시판의 높이조절 속성
      lang : 'ko-KR', // 썸머노트 메뉴 한글화 속성
      placeholder : '요청서 내용 입력해주세요' // 입력 전에 가이드라인 제공 속성
    });
  });

  var mapContainer = document.getElementById('map'), // 지도를 표시할 div
  mapOption = {
      center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
      level: 5 // 지도의 확대 레벨
  };

  //지도를 미리 생성
    var map = new daum.maps.Map(mapContainer, mapOption);
    //주소-좌표 변환 객체를 생성
    var geocoder = new daum.maps.services.Geocoder();
    //마커를 미리 생성
    var marker = new daum.maps.Marker({
        position: new daum.maps.LatLng(37.537187, 127.005476),
        map: map
    });

  function sample5_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = data.address; // 최종 주소 변수

            // 주소 정보를 해당 필드에 넣는다.
            document.getElementById("sample5_address").value = addr;
            // 주소로 상세 정보를 검색
            geocoder.addressSearch(data.address, function(results, status) {
                // 정상적으로 검색이 완료됐으면
                if (status === daum.maps.services.Status.OK) {

                    var result = results[0]; //첫번째 결과의 값을 활용

                    // 해당 주소에 대한 좌표를 받아서
                    var coords = new daum.maps.LatLng(result.y, result.x);
                    // 지도를 보여준다.
                    mapContainer.style.display = "block";
                    map.relayout();
                    // 지도 중심을 변경한다.
                    map.setCenter(coords);
                    // 마커를 결과값으로 받은 위치로 옮긴다.
                    marker.setPosition(coords)
                }
            });
        }
    }).open();
}

// 견적서 요청서 작성 함수
const onRequestPost = () => {

    // 1. input dom 가져오기
    let reqtitleValue = document.querySelector('.reqtitleValue'); console.log(reqtitleValue);
    let reqroleValue = document.querySelector('input[name="reqrole"]:checked'); console.log('reqroleValue', reqroleValue);
    let reqspaceValue = document.querySelector('.reqspaceValue'); console.log(reqspaceValue);
    let raddressValue = document.getElementById('raddressValue'); console.log(raddressValue);
    let reqcontentValue = document.querySelector('.reqcontentValue'); console.log(reqcontentValue);

    // 2. dom의 value 가져오기
    let reqtitle = reqtitleValue.value; console.log(reqtitle);
    let reqrole = reqroleValue ? reqroleValue.value : 0; console.log('reqrole',reqrole);
    let reqspace = reqspaceValue.value; console.log(reqspace);
    let raddress = sample5_address.value; console.log(raddress);
    let reqcontent = reqcontentValue.value; console.log(reqcontent);

    // 3. 유효성 검사
    // 모든 요소는 필수선택 조건이어야 한다.
    if(reqtitle.trim() === '') {
        alert('제목을 입력해주세요.');
        return false;
    } else if(reqrole == 0 ) {
        alert('정리/수납 요청 상대를 선택해주세요.')
        return false;
    } else if(reqspace === '선택하기') {
        alert('정리/수납 요청 공간을 선택해주세요.');
        return false;
    } else if(reqcontent.trim() === '') {
        alert('요청 내용을 입력해주세요.');
        return false;
    }


    // 4. 입력받은 값들 서버에 보낼 객체 만들기
    const requestDto = {
        reqtitle : reqtitle,
        reqrole : reqrole,
        reqspace : reqspace,
        raddress : raddress,
        reqcontent : reqcontent
    }

    // 5. fetch
    const option = {
        method : 'POST',
        headers : { 'Content-Type' : 'application/json' },
        body : JSON.stringify( requestDto )
    }

    const isUploadBtn = confirm("요청서를 올리시겠습니까?");

    if( isUploadBtn == true) {
        fetch('/request/post.do', option)
        .then(r=> r.json())
        .then(data =>{
            console.log(data)
            if( data == true ){
                alert("요청서 업로드 성공");
                location.href=`/request/list`; 
            } else {
                alert("요청서 업로드 실패")
            }
        })
        .catch(e=> {console.log(e)})
    } else return ;


}