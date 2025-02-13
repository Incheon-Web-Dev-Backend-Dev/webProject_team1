// signup.js

function selectRole(role, event) {
    document.getElementById('selectedRole').value = role;

    // 모든 박스의 배경색 초기화
    const boxes = document.querySelectorAll('.role-box');
    boxes.forEach(box => {
        box.style.backgroundColor = ''; // 초기화
    });

    // 선택된 박스 강조
    event.currentTarget.style.backgroundColor = '#e0e0e0';
}

document.getElementById('signupForm').addEventListener('submit', function(event) {
    const password = document.getElementById('mpwd').value;
    const passwordConfirm = document.getElementById('mpwdConfirm').value;

    // 비밀번호 확인
    if (password !== passwordConfirm) {
        event.preventDefault(); // 폼 제출 방지
        alert('비밀번호가 일치하지 않습니다.'); // 경고 메시지
        return;
    }

    // 비밀번호 유효성 검사
    const regex = /^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d]{1,20}$/; // 영어 대소문자, 숫자 포함, 20자 이내
    if (!regex.test(password)) {
        event.preventDefault(); // 폼 제출 방지
        alert('비밀번호는 영어와 숫자를 포함하고, 20자 이내여야 합니다.'); // 경고 메시지
        return;
    }

    // 모든 입력값을 서버로 전송하는 부분
    const formData = new FormData(event.target); // 폼 데이터 생성
    fetch('/api/signup', { // 서버 API 엔드포인트로 POST 요청
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.ok) {
            return response.json(); // JSON 응답 처리
        }
        throw new Error('회원가입 실패');
    })
    .then(data => {
        alert('회원가입이 완료되었습니다!'); // 성공 메시지
        // 필요에 따라 리다이렉션 또는 다음 단계 처리
    })
    .catch(error => {
        console.error(error);
        alert('오류가 발생했습니다. 다시 시도해 주세요.'); // 오류 처리
    });

    event.preventDefault(); // 기본 폼 제출 방지
});

// 비밀번호 유효성 검사
document.getElementById('mpwd').addEventListener('input', function() {
    const password = this.value;
    const confirmMessage = document.getElementById('confirmMessage');
    const regex = /^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d]{1,20}$/; // 영어 대소문자, 숫자 포함, 20자 이내

    if (regex.test(password)) {
        confirmMessage.textContent = ''; // 메시지 초기화
        document.getElementById('mpwdConfirm').disabled = false; // 비밀번호가 유효하면 활성화
    } else {
        confirmMessage.textContent = '비밀번호는 영어와 숫자를 포함하고, 20자 이내여야 합니다.';
        confirmMessage.style.color = 'red';
        document.getElementById('mpwdConfirm').disabled = true; // 비밀번호가 유효하지 않으면 비활성화
        document.getElementById('mpwdConfirm').value = ''; // 비밀번호 확인 입력란 초기화
    }
});

// 비밀번호 확인 실시간 검사
document.getElementById('mpwdConfirm').addEventListener('input', function() {
    const password = document.getElementById('mpwd').value;
    const passwordConfirm = this.value;
    const confirmMessage = document.getElementById('confirmMessage');

    if (password === passwordConfirm) {
        confirmMessage.textContent = '비밀번호가 일치합니다.';
        confirmMessage.style.color = 'green';
    } else {
        confirmMessage.textContent = '비밀번호가 일치하지 않습니다.';
        confirmMessage.style.color = 'red';
    }
});
