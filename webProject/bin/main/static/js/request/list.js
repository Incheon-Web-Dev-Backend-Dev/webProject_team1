// 페이징 변수 설정
let currentPage = 0;
const pageSize = 10;
let isLoading = false;
let hasMoreData = true;

// 요청글 목록 불러오기 함수 (페이징 적용)
const loadRequestList = () => {
    if (isLoading || !hasMoreData) return;
    
    isLoading = true;
    showLoadingIndicator();
    
    console.log(`페이지 ${currentPage} 로딩 중...`);
    fetch(`/request/paginated?page=${currentPage}&size=${pageSize}&sort=reqno&direction=desc`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            hideLoadingIndicator();
            
            // 데이터가 없거나 빈 배열인 경우
            if (!data.content || data.content.length === 0) {
                hasMoreData = false;
                showNoMoreDataMessage();
                return;
            }
            
            // 요청 결과 렌더링
            renderRequestCards(data.content);
            
            // 페이지 번호 증가
            currentPage++;
            
            // 마지막 페이지인지 확인
            if (data.last) {
                hasMoreData = false;
            }
            
            isLoading = false;
        })
        .catch(error => {
            console.error('Fetch error:', error);
            hideLoadingIndicator();
            isLoading = false;
            showErrorMessage('데이터를 불러오는 중 오류가 발생했습니다.');
        });
};

// 요청 카드 렌더링 함수
const renderRequestCards = (requests) => {
    // 1. 출력할 컨테이너 가져오기
    const reqCardContent = document.querySelector(".reqListCardBox");
    
    // 2. 출력할 HTML 생성
    let html = '';
    
    // 3. 데이터 반복하며 카드 생성
    requests.forEach(request => {
        // 탈퇴한 회원의 요청서는 표시하지 않음
        if (request.mno > 0) {
            // reqstate가 false인 경우 마감된 요청서
            const isClosedClass = !request.reqstate ? "closed" : "";
            
            html += `
                <div class="card ${isClosedClass}">
                    <div class="card-body">
                        <div class="card-content cardbox">
                            <div>
                                <h6 class="card-subtitle mb-2 text-body-secondary">${request.reqdatetime || ''}</h6>
                                <h5 class="card-title"><a href="/request/view?reqno=${request.reqno}">${request.reqtitle}</a></h5>
                            </div>
                            <div class="card-link receivedEstimates">
                                ${loginMemberInfo && loginMemberInfo.role === "requester" ? 
                                    `<span>들어온 견적서</span>
                                     <a href="/estimate/list?reqno=${request.reqno}" class="card-link">${request.estimateCount || 0}건</a>`
                                    : ''
                                }
                            </div>
                        </div>
                    </div>
                </div>               
            `;
        }
    });
    
    // 4. 첫 페이지인 경우 컨테이너 초기화, 아니면 기존 내용에 추가
    if (currentPage === 0) {
        reqCardContent.innerHTML = html;
    } else {
        reqCardContent.innerHTML += html;
    }
};

// 로딩 인디케이터 표시/숨김 함수
const showLoadingIndicator = () => {
    const loadingIndicator = document.getElementById('loading-indicator');
    if (loadingIndicator) {
        loadingIndicator.style.display = 'block';
    }
};

const hideLoadingIndicator = () => {
    const loadingIndicator = document.getElementById('loading-indicator');
    if (loadingIndicator) {
        loadingIndicator.style.display = 'none';
    }
};

// 더 이상 데이터가 없음을 표시하는 함수
const showNoMoreDataMessage = () => {
    const noMoreDataElem = document.getElementById('no-more-posts');
    if (noMoreDataElem) {
        noMoreDataElem.style.display = 'block';
    }
};

// 오류 메시지 표시 함수
const showErrorMessage = (message) => {
    // 기존 에러 메시지가 있다면 제거
    const existingError = document.querySelector('.error-message');
    if (existingError) {
        existingError.remove();
    }
    
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error-message';
    errorDiv.textContent = message;
    
    const container = document.querySelector('.listPage');
    if (container) {
        container.appendChild(errorDiv);
        
        // 3초 후 메시지 제거
        setTimeout(() => {
            errorDiv.remove();
        }, 3000);
    }
};

// 스크롤 이벤트 핸들러
const handleScroll = () => {
    if (isLoading || !hasMoreData) return;
    
    const scrollY = window.scrollY;
    const visibleHeight = window.innerHeight;
    const pageHeight = document.documentElement.scrollHeight;
    const bottomOfPage = scrollY + visibleHeight >= pageHeight - 300;
    
    if (bottomOfPage) {
        loadRequestList();
    }
};

// 현재 위치 기반 가까운 요청서 목록 출력
const nowLocation = () => {
    // 사용자의 현재 위치 좌표 조회
    navigator.geolocation.getCurrentPosition(function(position) {
        const userLatitude = position.coords.latitude;
        const userLongitude = position.coords.longitude;
        
        // 위치 정보를 서버로 전달
        fetch('/request/location', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({latitude: userLatitude, longitude: userLongitude})
        })
        .then(response => response.json())
        .then(data => {
            // 성공적으로 위치 전송 완료
            console.log('위치 정보 전송 완료:', data);
            
            // 그 후 가까운 요청서 목록 조회
            return fetch(`/request/near?latitude=${userLatitude}&longitude=${userLongitude}`, {
                method: 'GET',
            });
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            console.log('가까운 요청서 데이터:', data);
            
            // 기존 목록 초기화 및 가까운 순서대로 정렬된 목록 표시
            const reqCardContent = document.querySelector(".reqListCardBox");
            if (reqCardContent) {
                reqCardContent.innerHTML = '';
                
                let html = '';
                data.forEach(request => {
                    if (request.mno > 0) {
                        // reqstate가 false인 경우 마감된 요청서
                        const isClosedClass = !request.reqstate ? "closed" : "";
                        
                        html += `
                            <div class="card ${isClosedClass}">
                                <div class="card-body">
                                    <div class="card-content cardbox">
                                        <div>
                                            <h6 class="card-subtitle mb-2 text-body-secondary">${request.reqdatetime || ''}</h6>
                                            <h5 class="card-title">
                                                <a href="/request/view?reqno=${request.reqno}">${request.reqtitle}</a>
                                            </h5>
                                            <p class="card-text">${request.reqcontent || ''}</p>
                                            <p class="card-text">요청서 위치: ${request.raddress || ''} - 거리: ${request.distance ? request.distance.toFixed(2) : '?'} km</p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        `;
                    }
                });
                
                reqCardContent.innerHTML = html;
                
                // 페이지 초기화 (이제는 가까운 순서로 정렬된 목록이므로 무한 스크롤 비활성화)
                hasMoreData = false;
                
                // 더 이상 데이터 없음 메시지 제거
                const noMoreDataElem = document.getElementById('no-more-posts');
                if (noMoreDataElem) {
                    noMoreDataElem.style.display = 'none';
                }
            }
        })
        .catch(error => {
            console.error('Fetch error:', error);
            showErrorMessage('가까운 요청서를 불러오는 중 오류가 발생했습니다.');
        });
    }, function(error) {
        console.error('위치 정보를 가져오는 데 실패했습니다:', error);
        showErrorMessage('위치 정보를 가져오는 데 실패했습니다. 위치 권한을 확인해주세요.');
    });
};

// 카카오 지도 초기화 함수 (기존 코드 유지)
let map;
function initMap() {
    kakao.maps.load(function() {
        const container = document.getElementById("map");
        const options = {
            center: new kakao.maps.LatLng(37.5665, 126.9780),
            level: 3
        };

        map = new kakao.maps.Map(container, options);

        // 마커 클러스터러 생성
        var clusterer = new kakao.maps.MarkerClusterer({
            map: map,
            averageCenter: true,
            minLevel: 8
        });

        // Geolocation API를 이용해 현재 위치 받아오기
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(function(position) {
                const lat = position.coords.latitude;
                const lon = position.coords.longitude;

                const currentPosition = new kakao.maps.LatLng(lat, lon);
                map.setCenter(currentPosition);

                const markerImage = new kakao.maps.MarkerImage(
                    '/img/custom_marker.png',
                    new kakao.maps.Size(40, 40),
                    {
                        offset: new kakao.maps.Point(20, 40)
                    }
                );

                const marker = new kakao.maps.Marker({
                    position: currentPosition,
                    map: map,
                    title: "현재 위치",
                    image: markerImage
                });

            }, function(error) {
                console.error("현재 위치를 가져오는 데 실패했습니다.", error);
            });
        } else {
            console.error("Geolocation을 지원하지 않는 브라우저입니다.");
        }

        // 요청글 데이터 가져와서 지도에 표시
        fetch('/request/findall.do', { method: 'GET' })
            .then(r => r.json())
            .then(responseData => {
                console.log('지도에 표시할 데이터:', responseData);

                let markers = responseData.map(data => {
                    const marker = new kakao.maps.Marker({
                        position: new kakao.maps.LatLng(data.latitude, data.longitude)
                    });
                    
                    kakao.maps.event.addListener(marker, 'click', function() {
                        const infoWindow = new kakao.maps.InfoWindow({
                            content: `<a href="/request/view?reqno=${data.reqno}">${data.reqtitle}</a>`
                        });
                        infoWindow.open(map, marker);
                    });
                    return marker;
                });
                
                clusterer.addMarkers(markers);
            })
            .catch(error => {
                console.error("좌표 데이터 가져오기 실패:", error);
            });
    });
}

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', () => {
    // 오프캔버스 버튼 이벤트 등록
    const offcanvasToggleButton = document.getElementById("offcanvasToggleButton");
    if (offcanvasToggleButton) {
        // 버튼 보이게 설정
        offcanvasToggleButton.removeAttribute("hidden");
        
        // 오프캔버스가 표시될 때 지도 초기화
        const offcanvasElement = document.getElementById("offcanvasScrolling");
        if (offcanvasElement) {
            offcanvasElement.addEventListener('shown.bs.offcanvas', function () {
                initMap();
            });
        }
    }
    
    // 위치순으로 보기 버튼 이벤트 등록
    const locationButton = document.getElementById("locationButton");
    if (locationButton) {
        locationButton.addEventListener('click', nowLocation);
    }
    
    // 초기 데이터 로드
    loadRequestList();
    
    // 스크롤 이벤트 리스너 추가
    window.addEventListener('scroll', handleScroll);
});