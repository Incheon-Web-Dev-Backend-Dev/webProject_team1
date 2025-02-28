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



// const jobFindAll = () => {
//     fetch('/joboffer/findall.do',{method : 'GET'})
//     .then(r => r.json())
//     .then(d => {
//         if(d != null){
//             const list = document.querySelector('tbody')
//             let HTML = ``

//             for (let index = 0; index <= d.length-1; index++){
//                 let joboffer = d[index]
//                 HTML += `<tr>
//                             <td> ${joboffer.jono}</td>
//                             <td> ${joboffer.joservice}</td>
//                             <td class="jotitle ${joboffer.jostate? 'success' : ''}"> <a href="/job/view?jono=${joboffer.jono}"> ${joboffer.jotitle} </a> </td>
//                             <td> ${joboffer.memberDto.mname}</td>
//                             <td> ${joboffer.cdate}</td>
//                         </tr>`
//             }
//             list.innerHTML = HTML;
//         }
//         else{document.querySelector('.table').innerHTML = '작성된 구인글이 없습니다.'}
//     })
//     .catch(e => console.log(e))
// };

// jobFindAll();

const getMyInfo = () => {
    fetch('/member/myinfo.do',{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        console.log(d)
        if (d.role == 'company'){
            document.querySelector('.bottomMenu').innerHTML += 
                    `<a href="/job/write"><button class="btn btn-primary" type="button">글쓰기</button></a>
                     <a href="/job/mylist"><button class="btn btn-primary" type="button">내가 쓴 글 보기</button></a>`
        }
    })
    .catch(e => console.log(e))
}

getMyInfo();

// [1]

const findall = () => {

        let joservice = new URL(location.href).searchParams.get('joservice')
        if (joservice == null) joservice=''
        let jocity = new URL(location.href).searchParams.get('jocity')
        if (jocity == null) jocity=''
        let jodistrict = new URL(location.href).searchParams.get('jodistrict')
        if (jodistrict == null) jodistrict=''
        // 현재 페이지의 URL 에서 매개변수 page 값 구하기
        let page = new URL(location.href).searchParams.get('page');
        if(page == null) page = 1;
        // 1. 검색필드와 검색어
        let key = new URL(location.href).searchParams.get('key')
        if (key == null) key=''
        let keyword = new URL(location.href).searchParams.get('keyword')
        if (keyword == null) keyword=''

    fetch(`/joboffer/findall.do?joservice=${joservice}&jocity=${jocity}&jodistrict=${jodistrict}&page=${page}&key=${key}&keyword=${keyword}`, {method : 'GET'})
    .then( r=> r.json())
    .then(d => {
        console.log(d)
        if(d != null){
             const list = document.querySelector('tbody')
             let HTML = ``
             let jobofferList = d.data;
             jobofferList.forEach(joboffer => {
                 HTML += `<tr>
                             <td> ${joboffer.jono}</td>
                             <td> ${joboffer.joservice}</td>
                             <td class="jotitle ${joboffer.jostate? 'success' : ''}"> <a href="/job/view?jono=${joboffer.jono}"> ${joboffer.jotitle} </a> </td>
                             <td> ${joboffer.memberDto.mname}</td>
                             <td> ${joboffer.cdate}</td>
                         </tr>`
             })
             list.innerHTML = HTML;
             printPageNation(d,joservice,jocity,jodistrict, key, keyword)
        }
            
    })
    .catch(e => console.log(e))
}

findall();


// [2] 페이징버튼 생성하는 함수
const printPageNation = (d,joservice,jocity,jodistrict, key, keyword) => {
    let page = d.page; // 현재페이지
    let totalpage = d.totalpage;
    let startbtn = d.startbtn;
    let endbtn = d.endbtn;
    // (1) 어디에
    const pagebox = document.querySelector('.pagebox')
    // (2) 무엇을
    let html = ``
    // 이전 버튼, 현재 페이지에서 -1 차감한 페이지 이동, 만약에 0이면 1페이지로 고정
    html += `<li class="page-item"><a class="page-link" href="/job/list?joservice=${joservice}&jocity=${jocity}&jodistrict=${jodistrict}&page=${page <= 1? 1 : page-1}&key=${key}&keyWord=${keyword}">Previous</a></li>`
    // 페이징 버튼, 반복무 이용하여 startbtn 부터 endbtn 까지
    for(let index = startbtn; index <= endbtn; index++){
    // 만약 현재 페이지와 버튼번호가 같다면 .active 부트스트랩 클래스 부여
    html +=
    `<li class="page-item"><a class="page-link ${page==index?'active' : ''}" href="/job/list?joservice=${joservice}&jocity=${jocity}&jodistrict=${jodistrict}&page=${index}">${index}</a></li>`}
    // 다음 버튼, 현재 페이지에서 +1 증가한 페이지 이동, 만약에 전체페이지수보다 크면 전체페이지 수로 고정
    html += `<li class="page-item"><a class="page-link" href="/job/list?joservice=${joservice}&jocity=${jocity}&jodistrict=${jodistrict}&page=${page >= totalpage? totalpage : page+1}&key=${key}&keyWord=${keyword}">Next</a></li>`
    // (3) 출력
    pagebox.innerHTML = html
}

// [3] 검색버튼
const onSearch = () => {
    let joservice = $("select[id=joserviceSelect] option:selected").text();   // 선택된 option의 value 값이 아닌 그에 해당하는 text를 받아옴
    let jocity = $("select[id=jocitySelect] option:selected").text();         // 선택된 option의 value 값이 아닌 그에 해당하는 text를 받아옴
    let jodistrict = $("select[id=jodistrictSelect] option:selected").text(); // 선택된 option의 value 값이 아닌 그에 해당하는 text를 받아옴

    const key = document.querySelector('.key').value
    const keyword = document.querySelector('.keyword').value
//
//    if(joservice === '선택하기') {joservice=null}
//    if(jocity === '선택하기') {jocity=null}
//    if(jodistrict === '선택하기') {jodistrict=null}
//
//    location.href=`list?joservice=${joservice}&jocity=${jocity}&jodistrict=${jodistrict}&page=1&key=${key}&keyword=${keyword}`

     // 빈 값일 경우 null 처리
     if(joservice === '선택하기') {joservice = null;}
     if(jocity === '선택하기') {jocity = null;}
     if(jodistrict === '선택하기') {jodistrict = null;}
     if(key === '') {key = null;}
     if(keyword === '') {keyword = null;}

     let url = `list?`;
     if (joservice) url += `joservice=${joservice}&`;
     if (jocity) url += `jocity=${jocity}&`;
     if (jodistrict) url += `jodistrict=${jodistrict}&`;
     if (key) url += `key=${key}&`;
     if (keyword) url += `keyword=${keyword}&`;

     url += `page=1`; // page는 항상 추가

     location.href = url;  // 검색 후 URL로 이동
}

