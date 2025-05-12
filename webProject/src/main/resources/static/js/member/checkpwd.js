// DOM이 완전히 로드된 후 실행
document.addEventListener('DOMContentLoaded', function() {
    // 취소 버튼 이벤트 리스너
    const cancelBtn = document.getElementById('cancelBtn');
    if (cancelBtn) {
        cancelBtn.addEventListener('click', goBack);
    }
    
    // 폼 제출 이벤트 리스너
    const passwordForm = document.getElementById('passwordForm');
    if (passwordForm) {
        passwordForm.addEventListener('submit', function(event) {
            event.preventDefault();
            checkPwd();
        });
    }
});

// 비밀번호 확인 함수
function checkPwd() {
    const password = document.getElementById('password').value;
    const errorMessage = document.getElementById('error-message');
    
    if (!password) {
        if (errorMessage) {
            errorMessage.textContent = '비밀번호를 입력해주세요.';
        }
        return;
    }
    
    // 로딩 표시
    const okBtn = document.getElementById('okBtn');
    if (okBtn) {
        const originalText = okBtn.textContent;
        okBtn.disabled = true;
        okBtn.textContent = '확인 중...';
        
        // 비밀번호 확인 요청
        fetch('/member/checkpwd', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ mpwd: password })
        })
        .then(response => response.json())
        .then(data => {
            if (data === true) {
                // 비밀번호 확인 성공
                window.location.href = '/member/update';
            } else {
                // 비밀번호 확인 실패
                if (errorMessage) {
                    errorMessage.textContent = '비밀번호가 일치하지 않습니다.';
                }
                // 비밀번호 입력 필드 초기화
                const passwordInput = document.getElementById('password');
                if (passwordInput) {
                    passwordInput.value = '';
                    passwordInput.focus();
                }
            }
        })
        .catch(error => {
            console.error('비밀번호 확인 오류:', error);
            if (errorMessage) {
                errorMessage.textContent = '서버 오류가 발생했습니다. 다시 시도해주세요.';
            }
        })
        .finally(() => {
            // 버튼 상태 복원
            if (okBtn) {
                okBtn.disabled = false;
                okBtn.textContent = originalText;
            }
        });
    }
}

// 이전 페이지로 돌아가는 함수
function goBack() {
    window.history.back();
}