// * 썸머노트 실행
$(document).ready(function() {
    $('#summernote').summernote({
      height : 500 , // 썸머노트 게시판의 높이조절 속성
      lang : 'ko-KR', // 썸머노트 메뉴 한글화 속성
      placeholder : '내용 입력해주세요' // 입력 전에 가이드라인 제공 속성
    });
  });

  // 시/도별 상세 지역 데이터
const areaData = {
    "1": ["강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"],
    "2": ["가평군", "고양시", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시", "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "양평군", "여주시", "연천군", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시", "화성시"],
    "3": ["괴산군", "단양군", "보은군", "영동군", "옥천군", "음성군", "제천시", "증평군", "진천군", "청주시", "충주시"],
    "4": ["계룡시", "공주시", "금산군", "논산시", "당진시", "보령시", "부여군", "서산시", "서천군", "아산시", "예산군", "천안시", "청양군", "태안군", "홍성군"],
    "5": ["강진군", "고흥군", "곡성군", "광양시", "구례군", "나주시", "담양군", "목포시", "무안군", "보성군", "순천시", "신안군", "여수시", "영광군", "영암군", "완도군", "장성군", "장흥군", "진도군", "함평군", "해남군", "화순군"],
    "6": ["경산시", "경주시", "고령군", "구미시", "군위군", "김천시", "문경시", "봉화군", "상주시", "성주군", "안동시", "영덕군", "영양군", "영주시", "영천시", "예천군", "울릉군", "울진군", "의성군", "청도군", "청송군", "칠곡군", "포항시"],
    "7": ["거제시", "거창군", "고성군", "김해시", "남해군", "밀양시", "사천시", "산청군", "양산시", "의령군", "진주시", "창녕군", "창원시", "통영시", "하동군", "함안군", "함양군", "합천군"],
    "8": ["강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구", "연제구", "영도구", "중구", "해운대구"],
    "9": ["강화군", "계양구", "미추홀구", "남동구", "동구", "부평구", "서구", "연수구", "옹진군", "중구"],
    "10": ["남구", "달서구", "달성군", "동구", "북구", "서구", "수성구", "중구"],
    "11": ["광산구", "남구", "동구", "북구", "서구"],
    "12": ["남구", "동구", "북구", "울주군", "중구"],
    "13": ["세종특별자치"],
    "14": ["강릉시", "고성군", "동해시", "삼척시", "속초시", "양구군", "양양군", "영월군", "원주시", "인제군", "정선군", "철원군", "춘천시", "태백시", "평창군", "홍천군", "횡성군"],
    "15": ["고창군", "군산시", "김제시", "남원시", "무주군", "부안군", "순창군", "완주군", "익산시", "임실군", "장수군", "전주시", "정읍시", "진안군"],
    "16": ["서귀포시", "제주시"]
};

document.addEventListener('DOMContentLoaded', function() {
    const citySelect = document.querySelector('.jocityValue'); console.log(citySelect)
    const districtSelect = document.querySelector('.jodistrictValue'); console.log(districtSelect)
    
    // 시/도 선택 시 이벤트
    citySelect.addEventListener('change', function() {
        const selectedCity = this.value;
        
        // 기존 옵션 초기화
        districtSelect.innerHTML = '<option selected>선택하기</option>';
        
        // 선택된 시/도의 상세 지역이 있으면 옵션 추가
        if (areaData[selectedCity]) {
            areaData[selectedCity].forEach(area => {
                const option = document.createElement('option');
                option.value = area;
                option.textContent = area;
                districtSelect.appendChild(option);
            });
        }
    });
});


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
        document.querySelector('.jocityValue').innerHTML += `<option selected>${d.jocity}</option>`;
        document.querySelector('.jodistrictValue').innerHTML += `<option selected>${d.jodistrict}</option>`;
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
    let joservice = $("select[id=joserviceSelect] option:selected").text();
    let jocity = $("select[id=jocitySelect] option:selected").text();
    let jodistrict = $("select[id=jodistrictSelect] option:selected").text();
    let jocontentValue = document.querySelector('.jocontentValue');

    let jotitle = jotitleValue.value;
    let jocontent = jocontentValue.value;

    // 3. 유효성 검사
    // 모든 요소는 필수선택 조건이어야 한다.
    if(jotitle.trim() === '') {
        alert('제목을 입력해주세요.');
        return false;
    } else if(joservice === '선택하기') {
        alert('정리/수납 요청 공간을 선택해주세요.');
        return false;
    } else if(jocity === '선택하기') {
        alert('시/도를 선택해주세요.');
        return false;
    } else if(jodistrict === '선택하기') {
        alert('시/군/구를 선택해주세요.');
        return false;
    } else if(jocontent.trim() === '') {
        alert('요청 내용을 입력해주세요.');
        return false;
    }


    // 4. 입력받은 값들 서버에 보낼 객체 만들기
    const jobofferDto = {
        jono : jono,
        jotitle : jotitle,
        joservice : joservice,
        jocity : jocity,
        jodistrict : jodistrict,
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