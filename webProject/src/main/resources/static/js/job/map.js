// 전역 변수로 map을 선언하여 여러 함수에서 공유할 수 있도록 합니다.
let map;
let markers = []; // 마커들을 저장할 배열
let infowindows = []; // 인포윈도우들을 저장할 배열


const jobMap = () => {
  				
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div
	mapOption = {
		center: new kakao.maps.LatLng(37.5665851 , 126.9782038), // 지도의 중심좌표
			level: 6, // 지도의 확대 레벨
			mapTypeId : kakao.maps.MapTypeId.ROADMAP // 지도종류
		}; 

	// 지도를 생성한다 
	map = new kakao.maps.Map(mapContainer, mapOption); 

};

const jobSearch = () => {
	
	let joservice = $("select[id=joserviceSelect] option:selected").text();   // 선택된 option의 value 값이 아닌 그에 해당하는 text를 받아옴

	fetch(`/joboffer/map.do?joservice=${joservice}`, {method : 'GET'})
	.then(r => r.json())
	.then(d => {
		console.log(d);

		// 기존에 마커가 있으면 모두 제거
		removeMarkers();

		d.forEach(joboffer => {
			// 지도에 마커를 생성하고 표시한다
			var marker = new kakao.maps.Marker({
				position: new kakao.maps.LatLng(`${joboffer.latitude}`, `${joboffer.longitude}`), // 마커의 좌표
				map: map // 마커를 표시할 지도 객체
			});

					
			// 마커를 배열에 저장
			markers.push(marker);


			// 마커 위에 표시할 인포윈도우를 생성한다
			var infowindow = new kakao.maps.InfoWindow({
				content : `<div id="info">${joboffer.jotitle}</div>` // 인포윈도우에 표시할 내용
			});

			// 인포윈도우를 배열에 저장
			infowindows.push(infowindow);

			// 인포윈도우를 지도에 표시한다
			infowindow.open(map, marker);

			// 마커에 클릭 이벤트를 등록한다 (우클릭 : rightclick)
			kakao.maps.event.addListener(marker, 'click', function() {
				let result = confirm('해당 공고를 보시겠습니까?')
				if (result == false){return;}
				if (result == true){location.href=`/job/view?jono=${joboffer.jono}`}
			});
		
		});
	})
	.catch(e => console.log(e))
}

// 지도에 표시된 마커들과 인포윈도우들을 모두 제거하는 함수
const removeMarkers = () => {
    // 마커와 인포윈도우가 존재하면 제거
    if (markers.length > 0) {
        markers.forEach((marker, index) => {
            marker.setMap(null); // 마커를 지도에서 제거
            infowindows[index].close(); // 해당 인포윈도우를 닫는다
        });
        markers = []; // 마커 배열 초기화
        infowindows = []; // 인포윈도우 배열 초기화
    }
};

jobMap();
