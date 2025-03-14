// * 썸머노트 실행
$(document).ready(function() {
    $('#summernote').summernote({
      height : 500 , // 썸머노트 게시판의 높이조절 속성
      lang : 'ko-KR', // 썸머노트 메뉴 한글화 속성
      placeholder : '내용 입력해주세요' // 입력 전에 가이드라인 제공 속성
    });
  });


function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 기본적으로 주소를 변수에 담음
            var addr = '';
            var extraAddr = '';

            // 사용자가 선택한 주소 타입에 따라 주소를 가져옴
            if (data.userSelectedType === 'R') { // 도로명 주소
                addr = data.roadAddress;
            } else { // 지번 주소
                addr = data.jibunAddress;
            }

            // 도로명 주소일 경우 참고항목 추가
            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                document.getElementById("extraAddress").value = extraAddr; // 참고항목 입력
            } else {
                document.getElementById("extraAddress").value = ''; // 지번 주소인 경우 참고항목 비우기
            }

            // 우편번호와 주소를 해당 필드에 넣기
            document.getElementById('postcode').value = data.zonecode; // 우편번호
            document.getElementById("address").value = addr; // 주소 입력

            // 상세주소는 빈 칸으로 두고 포커스를 상세주소 입력란으로 이동
            document.getElementById("detailAddress").value = ''; // 상세주소는 자동 입력되지 않음
            document.getElementById("detailAddress").focus(); // 상세주소 필드로 포커스 이동

            // 디버깅을 위한 로그 출력
            console.log("우편번호: " + data.zonecode);
            console.log("주소: " + addr);
            console.log("참고 주소: " + extraAddr);

            // 지도 표시
            var mapContainer = document.getElementById('map');
            if (mapContainer) {
                mapContainer.style.display = "block"; // 지도 표시를 위해 강제로 보이게 설정
            }

            var mapOption = {
                center: new daum.maps.LatLng(37.537187, 127.005476), // 기본 위치
                level: 5 // 확대 레벨
            };

            // 지도 객체 생성
            var map = new daum.maps.Map(mapContainer, mapOption);
            var geocoder = new daum.maps.services.Geocoder();
            var marker = new daum.maps.Marker({
                position: new daum.maps.LatLng(37.537187, 127.005476), // 기본 위치
                map: map
            });

            // 주소로 좌표 변환 후 지도에 표시
            geocoder.addressSearch(data.address, function(results, status) {
                if (status === daum.maps.services.Status.OK) {
                    var result = results[0]; // 첫 번째 결과 사용
                    var coords = new daum.maps.LatLng(result.y, result.x);
                    // 위도와 경도를 변수에 저장
                    document.querySelector('.latitude').value = result.y; // 위도
                    document.querySelector('.longitude').value = result.x; // 경도               
                    // 지도 중심을 변환된 좌표로 변경
                    map.setCenter(coords);
                    marker.setPosition(coords); // 마커를 변환된 좌표로 이동
                }
            });
        }
    }).open();
}


const updateView = () => {
    // 이전 페이지의 URL을 가져옵니다.
    const referrerUrl = document.referrer;

    // URLSearchParams를 사용하여 쿼리 파라미터를 추출합니다.
    const referrerParams = new URLSearchParams(new URL(referrerUrl).search);

    // jono 파라미터 값을 가져옵니다.
    const jono = referrerParams.get('jono');

    // 결과를 출력합니다.
    console.log("이전 페이지 URL : " + referrerUrl);
    console.log('이전 페이지의 jono: ' + jono);  // 이제 정상적으로 'jono' 값을 출력합니다.

    fetch(`/joboffer/find.do?jono=${jono}`,{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        console.log(d);
        document.querySelector('.jotitleValue').value = `${d.jotitle}`;
        document.querySelector('.joserviceValue').innerHTML += `<option selected>${d.joservice}</option>`;
        document.querySelector('#address').value = `${d.joaddr}`;
        document.querySelector('#detailAddress').value = `${d.detailaddr}`;
        document.querySelector('.longitude').value = `${d.longitude}`;
        document.querySelector('.latitude').value = `${d.latitude}`;
        $('#summernote').summernote('code', `${d.jocontent}`);
    })
    .catch(console.log(e))
}

updateView();

function jobUpdate(){
    // 이전 페이지의 URL을 가져옵니다.
    const referrerUrl = document.referrer;

    // URLSearchParams를 사용하여 쿼리 파라미터를 추출합니다.
    const referrerParams = new URLSearchParams(new URL(referrerUrl).search);
    
    // jono 파라미터 값을 가져옵니다.
    const jono = referrerParams.get('jono');

    let jotitleValue = document.querySelector('.jotitleValue');
    let joservice = $("select[id=joserviceSelect] option:selected").text();   // 선택된 option의 value 값이 아닌 그에 해당하는 text를 받아옴
    let jocontentValue = document.querySelector('.jocontentValue');

    let jotitle = jotitleValue.value;
    let jocontent = jocontentValue.value;

    let joaddr = document.querySelector('#address').value;
    let detailaddr = document.querySelector('#detailAddress').value;
    let longitude = document.querySelector('.longitude').value;
    let latitude = document.querySelector('.latitude').value;

    // 3. 유효성 검사
    // 모든 요소는 필수선택 조건이어야 한다.
    if(jotitle.trim() === '') {
        alert('제목을 입력해주세요.');
        return false;
    } else if(joservice === '선택하기') {
        alert('정리/수납 요청 공간을 선택해주세요.');
        return false;
    } else if(joaddr.trim() === ''){
        alert('주소를 입력해주세요')
        return false;
    }else if(jocontent.trim() === '') {
        alert('요청 내용을 입력해주세요.');
        return false;
    }


    // 4. 입력받은 값들 서버에 보낼 객체 만들기
    const jobofferDto = {
        jono : jono,
        jotitle : jotitle,
        joservice : joservice,
        joaddr : joaddr,
        detailaddr : detailaddr,
        longitude : longitude,
        latitude : latitude,
        jocontent : jocontent
    }

    // 5. fetch
    const option = {
        method : 'PUT',
        headers : { 'Content-Type' : 'application/json' },
        body : JSON.stringify( jobofferDto )
    }
    
    let result = confirm('수정 하시겠습니까?');
    if( result == false ) { return; }

    fetch('/joboffer/update.do', option)
        .then(r=> r.json())
        .then(data =>{
            console.log(data)
            if( data == true ){
                alert("수정 성공")
                location.href='/'; // 요청서 개별 조회 페이지 만들면 링크 수정하기 
            } else {
                alert("수정 실패")
            }
        })
        .catch(e=> {console.log(e)})
};