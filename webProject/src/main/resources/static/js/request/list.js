// 내가 올린 견적요청서 리스트 조회 함수
const requestFindAll = () => {
    console.log("견적요청서 전체조회 함수 실행");
    fetch('/request/findall.do', {method: 'GET'})
        .then(r => {
            if (!r.ok) {
                throw new Error(`HTTP error! status: ${r.status}`);
            }
            console.log('응답 상태:', r.status);
            console.log('응답 헤더:', r.headers);
            return r.json();  // 일단 텍스트로 받아보기
        })
        .then(data => {
            // 요청 결과 응답자료 확인
            console.log(data);

            // 1. html을 출력할 구역 가져오기 
            const reqCardContent = document.querySelector(".reqListCardBox");
            
            // 2. 출력할 html을 저장하는 변수 선언 
            let html = ``;

            // 3. 응답 자료를 반복문을 이용하여 하나씩 순회해서 html 누적으로 더해서 출력하기 
            data.forEach(list => {
                // 탈퇴한 회원의 요청서는 보여주지 않기위해 조건 설정
                if (list.mno > 0) {
                    html += `
                        <div class="card ${list.reqstate ? "border-primary" : ''}" style="width: 32rem;">
                            <div class="card-body ">
                                <div class="card-content cardbox">
                                    <div>
                                        <h6 class="card-subtitle mb-2 text-body-secondary">${list.reqdatetime}</h6>
                                        <h5 class="card-title"><a href="/request/view?reqno=${list.reqno}">${list.reqtitle}</a></h5>
                                    </div>
                                    <div class="card-link receivedEstimates">
                                        ${loginMemberInfo.role === "requester" ? 
                                            `<span>들어온 견적서</span>
                                             <a href="/estimate/list?reqno=${list.reqno}" class="card-link">${list.estimateCount}건</a>`
                                            : ''
                                        }
                                    </div>
                                </div>
                            </div>
                        </div>               
                    `;
                }
            });

            // 4. 역할이 "master" 또는 "company"일 때만 상단 버튼을 추가
            if (loginMemberInfo.role === "master" || loginMemberInfo.role === "company") {
                const topButtonContainer = document.querySelector("#topButtonContainer");
                topButtonContainer.innerHTML = `
                    <button id="showMapButton" class="top-button">지도로 보기</button>
                    <button id="imHereButton" class="top-button" onclick="nowLocation()" ><i class="fa-solid fa-compass"></i></button>
                `;
                
                // "지도로 보기" 버튼 클릭 시 오프캔버스 열기 또는 닫기
                const showMapButton = document.getElementById("showMapButton");
                const offcanvasElement = document.getElementById("offcanvasScrolling");
                const offcanvas = new bootstrap.Offcanvas(offcanvasElement);

                showMapButton.addEventListener("click", function () {
                    if (offcanvasElement.classList.contains("show")) {
                        // 오프캔버스가 이미 열려있다면 닫기
                        offcanvas.hide();
                    } else {
                        // 오프캔버스를 열기
                        offcanvas.show();

                        // 오프캔버스가 열리면 카카오 지도 초기화
                        initMap();
                    }
                });
            }

            // 5. 반복문 종료 후 html 변수에 누적된 구역 출력하기
            reqCardContent.innerHTML = html;
        })
        .catch(e => { console.log(e) });
}
// 페이지 실행되면 자동으로 함수 실행
requestFindAll();

//========== 현재 위치 기반 가까운 대로 List 출력 ==========
function nowLocation() {
    // 사용자의 현재 위치를 가져오는 코드
    navigator.geolocation.getCurrentPosition(function(position) {
        const userLatitude = position.coords.latitude; 
        const userLongitude = position.coords.longitude;

        console.log(userLatitude, userLongitude);

        // 위치 정보를 백엔드로 전달
        fetch('/request/location', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                latitude: userLatitude,
                longitude: userLongitude
            })
        })
        .then(response => response.json())
        .then(data => {
            displayRequests(data);
        })
        .catch(error => console.error('Error:', error));

        // 두 번째 fetch 요청에 위치 정보 추가하여 서버에 요청
        fetch(`/request/near?latitude=${userLatitude}&longitude=${userLongitude}`, {
            method: 'GET',
        })
        .then(response => {
            // 응답이 정상인지 확인
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }

            // 응답 본문이 비어있는지 확인
            if (response.status === 204) {  // 204 No Content
                throw new Error('No content returned from server');
            }

            return response.json();  // 정상 응답인 경우 JSON 파싱
        })
        .then(data => {
            // 요청 결과 응답 자료 확인
            console.log(data);

            // 1. html을 출력할 구역 가져오기 
            const reqCardContent = document.querySelector(".reqListCardBox");

            // 2. 출력할 html을 저장하는 변수 선언 
            let html = ``;

            // 3. 응답 자료를 반복문을 이용하여 하나씩 순회해서 html 누적으로 더해서 출력하기 
            data.forEach(list => {
                // 탈퇴한 회원의 요청서는 보여주지 않기 위해 조건 설정
                if (list.mno > 0) {
                    html += `
                        <div class="card ${list.reqstate ? "border-primary" : ''}" style="width: 32rem;">
                            <div class="card-body">
                                <div class="card-content cardbox">
                                    <div>
                                        <h6 class="card-subtitle mb-2 text-body-secondary">${list.reqdatetime}</h6>
                                        <h5 class="card-title">
                                            <a href="/request/view?reqno=${list.reqno}">${list.reqtitle}</a>
                                        </h5>
                                        <p class="card-text">${list.reqcontent}</p>
                                        <p class="card-text">요청서 위치: ${list.raddress} - 거리: ${list.distance.toFixed(2)} km</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `;
                }
            });

            // 4. html 내용물을 해당 div에 삽입
            reqCardContent.innerHTML = html;
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });
    });
}

// ======== 카카오 지도 ========
let map;  // 전역 변수로 지도 객체 선언

function initMap() {
    // 카카오 지도 API 로드 후, 지도 객체 생성
    kakao.maps.load(function() {
        const container = document.getElementById("map"); // 지도가 표시될 div
        const options = {
            center: new kakao.maps.LatLng(37.5665, 126.9780),  // 초기 위치 (서울)
            level: 3 // 초기 줌 레벨
        };

        const map = new kakao.maps.Map(container, options); // 지도 객체 생성

        // 마커 클러스터러 생성
        var clusterer = new kakao.maps.MarkerClusterer({
            map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체 
            averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정 
            minLevel: 8 // 클러스터 할 최소 지도 레벨 
        });

        // Geolocation API를 이용해 현재 위치 받아오기
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                const lat = position.coords.latitude;  // 위도
                const lon = position.coords.longitude; // 경도

                // 현재 위치로 지도 중심 설정
                const currentPosition = new kakao.maps.LatLng(lat, lon);
                map.setCenter(currentPosition);  // 지도 중심을 현재 위치로 변경

                const markerImage = new kakao.maps.MarkerImage(
                    '/img/custom_marker.png',  // FontAwesome 아이콘을 이미지로 바꿔서 URL 넣기
                    new kakao.maps.Size(40, 40),  // 이미지 크기
                    {
                        offset: new kakao.maps.Point(20, 40)  // 이미지의 중심을 설정
                    }
                );

                // 현재 위치 마커 생성
                const marker = new kakao.maps.Marker({
                    position: currentPosition,    // 현위치 좌표
                    map: map,                     // 지도에 마커 표시
                    title: "현재 위치",           // 마커 제목
                    image: markerImage           // 마커에 커스텀 이미지 적용
                });

            }, function(error) {
                // 위치 정보를 가져오지 못했을 때 처리
                console.error("현재 위치를 가져오는 데 실패했습니다.", error);
            });
        } else {
            console.error("Geolocation을 지원하지 않는 브라우저입니다.");
        }

        // API를 호출하여 데이터를 가져오기
        fetch('/request/findall.do', { method: 'GET' })
            .then(r => r.json())
            .then(responseData => {
                console.log(responseData);

                // 마커와 클러스터 추가가
                let markers = responseData.map(data => {
                    const marker = new kakao.maps.Marker({
                        position: new kakao.maps.LatLng(data.latitude, data.longitude) // DB에서 받아온 위도, 경도 사용
                    });
                    // 마커 클릭시 이벤트
                    kakao.maps.event.addListener(marker, 'click', function() {
                        // 마커 클릭시 req title 표시 및 클릭시 상세페이지 이동
                        const infoWindow = new kakao.maps.InfoWindow({
                            content: `<a href="/request/view?reqno=${data.reqno}">${data.reqtitle}</a>
                            ` 
                        });
                        infoWindow.open(map, marker);
                    });
                    return marker;
                });
                // 클러스터 추가가
                clusterer.addMarkers(markers);
            })
            .catch(error => {
                console.error("좌표 데이터 가져오기 실패:", error);
            });
    });
}



