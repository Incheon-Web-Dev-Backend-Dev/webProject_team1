//
//
//
//function jobFindAll () {
//    let page = new URL(location.href).searchParams.get('page');
//
//
//    if(page == null) page = 1;
//    let key = new URL(location.href).searchParams.get('key')
//    if (key == null) key=''
//    let keyword = new URL(location.href).searchParams.get('keyword')
//    if (keyword == null) keyword=''
//    // 2. fetch option
//    const option = {method : 'GET'}
//    // 3. fetch
//    fetch(`/joboffer/findall.do?page=${page}&key=${key}&keyword=${keyword}`,option)
//    .then(r => r.json())
//    .then(d => {
//        // 4. 요청 결과 응답 자료 확인
//        console.log(d)
//        // 5. html 를 출력할 구역 dom 가져오기
//        const tbody = document.querySelector('tbody')
//        // 6. 출력할 html 를 저장하는 변수 선언
//        let html = ``
//        // 7. 응답 자료를 반복문 이용하여 하나씩 순회해서 html 누적으로 더해주기
//            // + 응답 자료에서 게시물 리스트 추출
//            // data = {data : []}
//        let jobOfferList = d.data;
//        jobOfferList.forEach(jobOffer => {
//            html += `<tr>
//                      <td> ${jobOffer.jono}</td>
//                      <td> ${jobOffer.joservice}</td>
//                      <td class="jotitle ${jobOffer.jostate? 'success' : ''}"> <a href="/job/view?jono=${jobOffer.jono}"> ${jobOffer.jotitle} </a> </td>
//                      <td> ${jobOffer.memberDto.mname}</td>
//                      <td> ${jobOffer.cdate}</td>
//                    </tr>`
//
//        });
//        // 8. 반복문 종료후 html 변수에 누적된 <tr> 출력하기
//        tbody.innerHTML = html;
//        // 9. 게시물 출력 후 페이징 버튼 생성 함수 호출
//        printPageNation(d, key, keyword)
//    })
//    .catch(e => console.log(e))
//}
//
//jobFindAll();
//
//// [2] 페이징버튼 생성하는 함수
//function printPageNation (d, key, keyword) {
//    let page = d.page; // 현재페이지
//    let totalpage = d.totalpage;
//    let startbtn = d.startbtn;
//    let endbtn = d.endbtn;
//    // (1) 어디에
//    const pagebox = document.querySelector('.pagebox')
//    // (2) 무엇을
//    let html = ``
//    // 이전 버튼, 현재 페이지에서 -1 차감한 페이지 이동, 만약에 0이면 1페이지로 고정
//    html += `<li class="page-item"><a class="page-link" href="/job/list?page=${page <= 1? 1 : page-1}&key=${key}&keyword=${keyword}">Previous</a></li>`
//    // 페이징 버튼, 반복무 이용하여 startbtn 부터 endbtn 까지
//    for(let index = startbtn; index <= endbtn; index++){
//    // 만약 현재 페이지와 버튼번호가 같다면 .active 부트스트랩 클래스 부여
//    html += `<li class="page-item"><a class="page-link ${page==index?'active' : ''}" href="/job/list?page=${index}&key=${key}&keyword=${keyword}">${index}</a></li>`}
//    // 다음 버튼, 현재 페이지에서 +1 증가한 페이지 이동, 만약에 전체페이지수보다 크면 전체페이지 수로 고정
//    html += `<li class="page-item"><a class="page-link" href="/job/list?page=${page >= totalpage? totalpage : page+1}&key=${key}&keyword=${keyword}">Next</a></li>`
//    // (3) 출력
//    pagebox.innerHTML = html
//}
//
//// [3] 검색 버튼을 클릭 했을때 함수
//const onSearch = ( ) => {
//    // 1. 선택한 검색필드 와 입력받은 검색어 가져오기
//
//    const key = document.querySelector('.key').value
//    const keyword = document.querySelector('.keyword').value
//
//    location.href = `/job/list?page=1&key=${ key }&keyword=${ keyword }`
//}
//function getMyInfo () {
//    fetch('/member/myinfo.do',{method : 'GET'})
//    .then(r => r.json())
//    .then(d => {
//        console.log(d)
//        if (d.role == 'company'){
//            document.querySelector('.companyButton').innerHTML +=
//                    `<a href="/job/write"><button class="btn btn-primary" type="button">글쓰기</button></a>
//                     <a href="/job/mylist"><button class="btn btn-primary" type="button">내가 쓴 글 보기</button></a>`
//        }
//    })
//    .catch(e => console.log(e))
//}
//
//getMyInfo();





let currentPage = 1;
let isLastPage = false;
let isLoading = false;

function loadJobs() {
    if (isLoading || isLastPage) return;
    isLoading = true;

    let key = new URL(location.href).searchParams.get('key') || '';
    let keyword = new URL(location.href).searchParams.get('keyword') || '';

    fetch(`/joboffer/findall.do?page=${currentPage}&key=${key}&keyword=${keyword}`)
        .then(r => r.json())
        .then(d => {
            const tbody = document.querySelector('tbody');
            let html = ``;
            let jobOfferList = d.data;

            jobOfferList.forEach(jobOffer => {
                html += `<tr>
                    <td>${jobOffer.jono}</td>
                    <td>${jobOffer.joservice}</td>
                    <td class="jotitle ${jobOffer.jostate ? 'success' : ''}">
                        <a href="/job/view?jono=${jobOffer.jono}">${jobOffer.jotitle}</a>
                    </td>
                    <td>${jobOffer.memberDto.mname}</td>
                    <td>${jobOffer.cdate}</td>
                </tr>`;
            });

            tbody.insertAdjacentHTML('beforeend', html);

            if (currentPage >= d.totalpage) {
                isLastPage = true;
            } else {
                currentPage++;
            }
            isLoading = false;
        })
        .catch(e => {
            console.log(e);
            isLoading = false;
        });
}

const onSearch = () => {
    const key = document.querySelector('.key').value;
    const keyword = document.querySelector('.keyword').value;

    document.querySelector('tbody').innerHTML = '';
    currentPage = 1;
    isLastPage = false;
    history.replaceState(null, '', `/job/list?key=${key}&keyword=${keyword}`);
    loadJobs();
}

window.addEventListener('scroll', () => {
    if (window.innerHeight + window.scrollY >= document.body.offsetHeight - 100) {
        loadJobs();
    }
});

loadJobs();



function getMyInfo () {
    fetch('/member/myinfo.do',{method : 'GET'})
    .then(r => r.json())
    .then(d => {
        console.log(d)
        if (d.role == 'company'){
            document.querySelector('.companyButton').innerHTML += 
                    `<a href="/job/write"><button class="btn btn-primary" type="button">글쓰기</button></a>
                     <a href="/job/mylist"><button class="btn btn-primary" type="button">내가 쓴 글 보기</button></a>`
        }
    })
    .catch(e => console.log(e))
}

getMyInfo();
