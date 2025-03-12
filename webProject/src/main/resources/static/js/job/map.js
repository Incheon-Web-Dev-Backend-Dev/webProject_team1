const jobMap = () => {
        fetch(`/joboffer/map.do`,{method : 'GET'})
		.then(r => r.json())
		.then(d => {				
				var mapContainer = document.getElementById('map'), // 지도를 표시할 div
					mapOption = {
						center: new kakao.maps.LatLng(37.5665851 , 126.9782038), // 지도의 중심좌표
						level: 6, // 지도의 확대 레벨
						mapTypeId : kakao.maps.MapTypeId.ROADMAP // 지도종류
					}; 

				// 지도를 생성한다 
				var map = new kakao.maps.Map(mapContainer, mapOption); 

				d.forEach(joboffer => {
				// 지도에 마커를 생성하고 표시한다
				var marker = new kakao.maps.Marker({
					position: new kakao.maps.LatLng(`${joboffer.latitude}`, `${joboffer.longitude}`), // 마커의 좌표
					map: map // 마커를 표시할 지도 객체
				});

				// 마커 위에 표시할 인포윈도우를 생성한다
				var infowindow = new kakao.maps.InfoWindow({
					content : `<div id="info">${joboffer.jotitle}</div>` // 인포윈도우에 표시할 내용
				});

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

jobMap();