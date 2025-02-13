// * 썸머노트 실행
$(document).ready(function() {
    $('#summernote').summernote({
      height : 500 , // 썸머노트 게시판의 높이조절 속성
      lang : 'ko-KR', // 썸머노트 메뉴 한글화 속성
      placeholder : '요청서 내용 입력해주세요' // 입력 전에 가이드라인 제공 속성
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
    const bigAreaSelect = document.querySelector('.reqbigareaValue'); console.log(bigAreaSelect)
    const smallAreaSelect = document.querySelector('.reqsmallareaValue'); console.log(smallAreaSelect)
    
    // 시/도 선택 시 이벤트
    bigAreaSelect.addEventListener('change', function() {
        const selectedCity = this.value;
        
        // 기존 옵션 초기화
        smallAreaSelect.innerHTML = '<option selected>선택하기</option>';
        
        // 선택된 시/도의 상세 지역이 있으면 옵션 추가
        if (areaData[selectedCity]) {
            areaData[selectedCity].forEach(area => {
                const option = document.createElement('option');
                option.value = area;
                option.textContent = area;
                smallAreaSelect.appendChild(option);
            });
        }
    });
});


// 견적서 요청서 작성 함수
const onRequestPost = () => {

    // 1. input dom 가져오기
    let reqtitleValue = document.querySelector('.reqtitleValue'); console.log(reqtitleValue);
    let reqspaceValue = document.querySelector('.reqspaceValue'); console.log(reqspaceValue);
    let reqbigareaValue = document.querySelector('.reqbigareaValue'); console.log(reqbigareaValue);
    let reqsmallareaValue = document.querySelector('.reqsmallareaValue'); console.log(reqsmallareaValue);
    let reqcontentValue = document.querySelector('.reqcontentValue'); console.log(reqcontentValue);

    // 2. dom의 value 가져오기
    let reqtitle = reqtitleValue.value; console.log(reqtitle);
    let reqspace = reqspaceValue.value; console.log(reqspace);
    let reqbigarea = reqbigareaValue.value; console.log(reqbigarea);
    let reqsmallarea = reqsmallareaValue.value; console.log(reqsmallarea);
    let reqcontent = reqcontentValue.value; console.log(reqcontent);

    // 3. 유효성 검사
    // 모든 요소는 필수선택 조건이어야 한다.
    if(reqtitle.trim() === '') {
        alert('제목을 입력해주세요.');
        return false;
    } else if(reqspace === '선택하기') {
        alert('정리/수납 요청 공간을 선택해주세요.');
        return false;
    } else if(reqbigarea === '선택하기') {
        alert('시/도를 선택해주세요.');
        return false;
    } else if(reqsmallarea === '선택하기') {
        alert('시/군/구를 선택해주세요.');
        return false;
    } else if(reqcontent.trim() === '') {
        alert('요청 내용을 입력해주세요.');
        return false;
    }


    // 4. 입력받은 값들 서버에 보낼 객체 만들기
    const requestDto = {
        reqtitle : reqtitle,
        reqspace : reqspace,
        reqbigarea : reqbigarea,
        reqsmallarea : reqsmallarea,
        reqcontent : reqcontent
    }

    // 5. fetch
    const option = {
        method : 'POST',
        headers : { 'Content-Type' : 'application/json' },
        body : JSON.stringify( requestDto )
    }

    fetch('/request/post.do', option)
        .then(r=> r.json())
        .then(data =>{
            console.log(data)
            if( data == true ){
                confirm('요청서를 올리시겠습니까?')
                alert("요청서 업로드 성공")
                location.href='/'; // 요청서 개별 조회 페이지 만들면 링크 수정하기 
            } else {
                alert("요청서 업로드 실패")
            }
        })
        .catch(e=> {console.log(e)})
}