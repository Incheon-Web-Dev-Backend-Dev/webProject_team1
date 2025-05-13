// 무한 스크롤을 위한 변수들
let lastId = null;
let isLoading = false;
let hasMore = true;
const limit = 10;

// 내 구인글 불러오기 함수
function loadMyJobs() {
    // 이미 로딩 중이거나 더 불러올 데이터가 없으면 함수 종료
    if (isLoading || !hasMore) return;
    
    // 로딩 상태로 변경
    isLoading = true;
    document.getElementById('loading-indicator').style.display = 'block';
    
    // URL 파라미터 가져오기
    let key = new URL(location.href).searchParams.get('key') || '';
    let keyword = new URL(location.href).searchParams.get('keyword') || '';
    
    // API 호출
    fetch(`/joboffer/myjobs/scroll?key=${key}&keyword=${keyword}&limit=${limit}${lastId ? `&lastId=${lastId}` : ''}`)
    .then(r => r.json())
    .then(d => {
        // 로딩 표시기 숨기기
        document.getElementById('loading-indicator').style.display = 'none';
        
        // 결과가 비어있으면 더 이상 데이터 없음
        if (d.jobList.length === 0) {
            hasMore = false;
            document.getElementById('no-more-posts').style.display = 'block';
            isLoading = false;
            return;
        }
        
        // 카드 컨테이너 가져오기
        const cardContainer = document.querySelector('.jobListCardBox');
        
        // 각 구인글에 대해 카드 생성 및 추가
        d.jobList.forEach(job => {
            const card = createJobCard(job);
            cardContainer.appendChild(card);
        });
        
        // 상태 업데이트
        lastId = d.lastId;
        hasMore = d.hasMore;
        
        // 더 이상 데이터가 없으면 메시지 표시
        if (!hasMore) {
            document.getElementById('no-more-posts').style.display = 'block';
        }
        
        // 로딩 상태 해제
        isLoading = false;
    })
    .catch(e => {
        console.error('구인글 로딩 중 오류:', e);
        document.getElementById('loading-indicator').style.display = 'none';
        isLoading = false;
    });
}

// 구인글 카드 생성 함수
function createJobCard(job) {
    const card = document.createElement('div');
    card.className = `job-card ${job.jostate ? 'closed' : ''}`;
    
    // 상태 표시 뱃지 추가
    const statusBadge = job.jostate ? 
        '<div class="status-badge closed">마감</div>' : 
        '<div class="status-badge active">모집중</div>';
    
    card.innerHTML = `
        <div class="job-number">#${job.jono}</div>
        ${statusBadge}
        <div class="job-service">${job.joservice}</div>
        <div class="job-title">${job.jotitle}</div>
        <div class="job-info">
            <p class="job-writer">작성자: ${job.memberDto.mname}</p>
            <p class="job-date">작성일: ${formatDate(job.cdate)}</p>
            <p class="job-address">위치: ${job.joaddr}</p>
        </div>
        <div class="job-actions">
            <button class="btn-edit" data-jono="${job.jono}">수정</button>
            <button class="btn-status" data-jono="${job.jono}" data-state="${job.jostate}">
                ${job.jostate ? '모집재개' : '마감하기'}
            </button>
            <button class="btn-delete" data-jono="${job.jono}">삭제</button>
        </div>
    `;
    
    // 카드 클릭 이벤트 (상세 페이지로 이동하되, 버튼 영역 제외)
    card.addEventListener('click', (e) => {
        // 버튼 클릭은 상세 페이지 이동을 방지
        if (!e.target.closest('.job-actions')) {
            window.location.href = `/job/view?jono=${job.jono}`;
        }
    });
    
    // 각 버튼에 이벤트 리스너 추가
    const setupButtonEvents = () => {
        // 수정 버튼
        card.querySelector('.btn-edit').addEventListener('click', (e) => {
            e.stopPropagation();
            window.location.href = `/job/update?jono=${job.jono}`;
        });
        
        // 상태 변경 버튼 (마감/재개)
        card.querySelector('.btn-status').addEventListener('click', (e) => {
            e.stopPropagation();
            const jono = e.target.dataset.jono;
            updateJobStatus(jono);
        });
        
        // 삭제 버튼
        card.querySelector('.btn-delete').addEventListener('click', (e) => {
            e.stopPropagation();
            const jono = e.target.dataset.jono;
            deleteJob(jono);
        });
    };
    
    // 추가 후 버튼 이벤트 설정
    setTimeout(setupButtonEvents, 0);
    
    return card;
}

// 날짜 포맷팅 함수
function formatDate(dateString) {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
}

// 구인글 상태 업데이트 (마감/재개)
function updateJobStatus(jono) {
    if (!confirm('구인글 상태를 변경하시겠습니까?')) return;
    
    fetch(`/joboffer/stateupdate.do?jono=${jono}`, {
        method: 'PUT'
    })
    .then(response => {
        if (response.ok) {
            alert('상태가 변경되었습니다.');
            // 카드 상태만 업데이트하여 깜빡임 없이 바로 반영
            const card = document.querySelector(`.job-card .btn-status[data-jono="${jono}"]`).closest('.job-card');
            const statusButton = card.querySelector('.btn-status');
            const currentState = statusButton.dataset.state === 'true';
            
            // 상태 업데이트
            statusButton.dataset.state = (!currentState).toString();
            statusButton.textContent = !currentState ? '마감하기' : '모집재개';
            
            // 카드 클래스 및 상태 뱃지 업데이트
            if (!currentState) {
                card.classList.add('closed');
                card.querySelector('.status-badge').className = 'status-badge closed';
                card.querySelector('.status-badge').textContent = '마감';
            } else {
                card.classList.remove('closed');
                card.querySelector('.status-badge').className = 'status-badge active';
                card.querySelector('.status-badge').textContent = '모집중';
            }
        } else {
            alert('상태 변경 중 오류가 발생했습니다.');
        }
    })
    .catch(error => {
        console.error('상태 변경 오류:', error);
        alert('상태 변경 중 오류가 발생했습니다.');
    });
}

// 구인글 삭제
function deleteJob(jono) {
    if (!confirm('정말 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.')) return;
    
    fetch(`/joboffer/delete.do?jono=${jono}`, {
        method: 'DELETE'
    })
    .then(response => {
        if (response.ok) {
            alert('삭제되었습니다.');
            // 해당 카드만 DOM에서 제거
            const card = document.querySelector(`.job-card .btn-delete[data-jono="${jono}"]`).closest('.job-card');
            card.remove();
        } else {
            alert('삭제 중 오류가 발생했습니다.');
        }
    })
    .catch(error => {
        console.error('삭제 오류:', error);
        alert('삭제 중 오류가 발생했습니다.');
    });
}

// 검색 기능
function onSearch() {
    // 검색어와 검색 필드 가져오기
    const key = document.querySelector('.key').value;
    const keyword = document.querySelector('.keyword').value;
    
    // 상태 초기화
    lastId = null;
    hasMore = true;
    
    // 기존 결과 초기화
    document.querySelector('.jobListCardBox').innerHTML = '';
    document.getElementById('no-more-posts').style.display = 'none';
    
    // URL 업데이트 (페이지 이동 없이)
    history.replaceState(null, '', `/job/mylist?key=${key}&keyword=${keyword}`);
    
    // 새 검색 결과 로드
    loadMyJobs();
}

// 엔터 키로 검색 실행
document.addEventListener('DOMContentLoaded', () => {
    const searchInput = document.querySelector('.keyword');
    if (searchInput) {
        searchInput.addEventListener('keypress', (event) => {
            if (event.key === 'Enter') {
                onSearch();
            }
        });
    }
});

// 스크롤 이벤트 리스너
window.addEventListener('scroll', () => {
    // 페이지 하단에 도달하면 추가 데이터 로드
    const { scrollTop, scrollHeight, clientHeight } = document.documentElement;
    if (scrollTop + clientHeight >= scrollHeight - 200) {
        loadMyJobs();
    }
});

// 회원 정보 가져오기
function getMyInfo() {
    fetch('/member/myinfo', { method: 'GET' })
    .then(r => r.json())
    .then(d => {
        if (d.role == 'company') {
            document.querySelector('.companyButton').innerHTML = `
                <a href="/job/write"><button class="btn btn-primary" type="button">글쓰기</button></a>
            `;
        }
    })
    .catch(e => console.error('회원 정보 로딩 오류:', e));
}

// 초기화 함수
function init() {
    // 초기 데이터 로드
    loadMyJobs();
    
    // 회원 정보 가져오기
    getMyInfo();
    
    // URL에서 검색 조건 가져와서 폼에 설정
    const url = new URL(location.href);
    const key = url.searchParams.get('key');
    const keyword = url.searchParams.get('keyword');
    
    if (key) {
        document.querySelector('.key').value = key;
    }
    
    if (keyword) {
        document.querySelector('.keyword').value = keyword;
    }
}

// 페이지 로드 시 초기화
document.addEventListener('DOMContentLoaded', init);