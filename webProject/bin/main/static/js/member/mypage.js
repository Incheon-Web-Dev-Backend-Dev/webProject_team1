<<<<<<< HEAD
=======
// 사용자 정보와 역할을 저장할 변수
let currentUserInfo = null;
let memberFiles = [];
let isEditMode = false;
let passwordChanged = false;

// 페이지 로드 시 실행
document.addEventListener('DOMContentLoaded', function() {
    // 내 정보 로드
    getMyInfo();
    
    // 이벤트 리스너 등록 함수 호출
    setupEventListeners();
});

// 이벤트 리스너 설정 함수
function setupEventListeners() {
    // 수정 모드 버튼
    const editModeBtn = document.getElementById('editModeBtn');
    if (editModeBtn) {
        editModeBtn.addEventListener('click', function() {
            // 수정 모드로 전환하기 전에 비밀번호 확인
            showPwdConfirmModal();
        });
    }
    
    // 계정 탈퇴 버튼
    const deleteAccountBtn = document.getElementById('deleteAccountBtn');
    if (deleteAccountBtn) {
        deleteAccountBtn.addEventListener('click', function() {
            if (confirm('정말 계정을 탈퇴하시겠습니까? 이 작업은 되돌릴 수 없습니다.')) {
                // 탈퇴 전에 비밀번호 확인
                showPwdConfirmModal('delete');
            }
        });
    }
    
    // 비밀번호 확인 모달의 확인 버튼
    const pwdConfirmBtn = document.getElementById('pwdConfirmBtn');
    if (pwdConfirmBtn) {
        pwdConfirmBtn.addEventListener('click', function() {
            const pwd = document.getElementById('pwdConfirmInput').value;
            const mode = pwdConfirmBtn.getAttribute('data-mode') || 'edit';
            
            if (!pwd) {
                const pwdConfirmMessage = document.getElementById('pwdConfirmMessage');
                if (pwdConfirmMessage) {
                    pwdConfirmMessage.textContent = '비밀번호를 입력해주세요.';
                    pwdConfirmMessage.className = 'form-message error-message';
                }
                return;
            }
            
            checkPassword(pwd, mode);
        });
    }
    
    // 비밀번호 확인 모달의 취소 버튼
    const pwdCancelBtn = document.getElementById('pwdCancelBtn');
    if (pwdCancelBtn) {
        pwdCancelBtn.addEventListener('click', function() {
            hidePwdConfirmModal();
        });
    }
    
    // 비밀번호 확인 모달의 닫기 버튼
    const pwdModalClose = document.querySelector('#pwdConfirmModal .close');
    if (pwdModalClose) {
        pwdModalClose.addEventListener('click', function() {
            hidePwdConfirmModal();
        });
    }
    
    // 비밀번호 변경 토글 버튼
    const togglePwdBtn = document.getElementById('togglePwdBtn');
    if (togglePwdBtn) {
        togglePwdBtn.addEventListener('click', function() {
            const passwordFields = document.getElementById('passwordFields');
            if (passwordFields) {
                if (passwordFields.style.display === 'none') {
                    passwordFields.style.display = 'block';
                    togglePwdBtn.textContent = '취소';
                } else {
                    passwordFields.style.display = 'none';
                    togglePwdBtn.textContent = '변경하기';
                    // 입력 필드 초기화
                    const currentPwd = document.getElementById('currentPwd');
                    const newPwd = document.getElementById('newPwd');
                    const confirmPwd = document.getElementById('confirmPwd');
                    if (currentPwd) currentPwd.value = '';
                    if (newPwd) newPwd.value = '';
                    if (confirmPwd) confirmPwd.value = '';
                    
                    // 메시지 초기화
                    const currentPwdMessage = document.getElementById('currentPwdMessage');
                    const pwdHelp = document.getElementById('pwdHelp');
                    const confirmMessage = document.getElementById('confirmMessage');
                    
                    if (currentPwdMessage) currentPwdMessage.textContent = '';
                    if (pwdHelp) pwdHelp.className = 'form-message';
                    if (confirmMessage) confirmMessage.textContent = '';
                    
                    // 비밀번호 변경 여부 초기화
                    passwordChanged = false;
                }
            }
        });
    }
    
    // 현재 비밀번호 확인
    const currentPwd = document.getElementById('currentPwd');
    if (currentPwd) {
        currentPwd.addEventListener('blur', function() {
            if (this.value) {
                verifyCurrentPassword(this.value);
            }
        });
    }
    
    // 새 비밀번호 유효성 검사
    const newPwd = document.getElementById('newPwd');
    if (newPwd) {
        newPwd.addEventListener('input', function() {
            validateNewPassword();
        });
    }
    
    // 비밀번호 확인 일치 검사
    const confirmPwd = document.getElementById('confirmPwd');
    if (confirmPwd) {
        confirmPwd.addEventListener('input', function() {
            checkPasswordMatch();
        });
    }
    
    // 주소 검색 버튼
    const searchAddrBtn = document.getElementById('searchAddrBtn');
    if (searchAddrBtn) {
        searchAddrBtn.addEventListener('click', function() {
            execDaumPostcode();
        });
    }
    
    // 상세주소 입력 시 주소 업데이트
    const detailAddress = document.getElementById('detailAddress');
    if (detailAddress) {
        detailAddress.addEventListener('input', function() {
            updateFullAddress();
        });
    }
    
    // 저장 버튼
    const userInfoForm = document.getElementById('userInfoForm');
    if (userInfoForm) {
        userInfoForm.addEventListener('submit', function(event) {
            event.preventDefault();
            saveUserInfo();
        });
    }
    
    // 취소 버튼
    const cancelBtn = document.getElementById('cancelBtn');
    if (cancelBtn) {
        cancelBtn.addEventListener('click', function() {
            exitEditMode();
        });
    }
    
    // 프로필 이미지 업로드
    const profileUpload = document.getElementById('profileUpload');
    if (profileUpload) {
        profileUpload.addEventListener('change', function(event) {
            uploadProfileImage(event);
        });
    }
    
    // 첨부 파일 관련 모달 처리
    const modal = document.getElementById('fileModal');
    const modalClose = document.querySelector('#fileModal .close');
    const viewFilesBtn = document.getElementById('viewFilesBtn');
    const updateFilesBtn = document.getElementById('updateFilesBtn');
    const fileUploadForm = document.getElementById('fileUploadForm');

    // 보기 버튼에 오른쪽 마진 추가
    viewFilesBtn.style.marginBottom = '10px';    

    // 모달 닫기
    if (modalClose && modal) {
        modalClose.onclick = function() {
            modal.style.display = 'none';
        };
        
        // 모달 외부 클릭 시 닫기
        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = 'none';
            } else if (event.target == document.getElementById('pwdConfirmModal')) {
                document.getElementById('pwdConfirmModal').style.display = 'none';
            }
        };
    }

    // 첨부 파일 보기 버튼 클릭
    if (viewFilesBtn) {
        viewFilesBtn.addEventListener('click', function() {
            // 파일 목록 표시 모달 열기
            showFileListModal(false);
        });
    }

    // 첨부 파일 수정 버튼 클릭
    if (updateFilesBtn) {
        updateFilesBtn.addEventListener('click', function() {
            // 파일 수정 모달 열기
            showFileListModal(true);
        });
    }

    // 파일 업로드 폼 제출 처리
    if (fileUploadForm) {
        fileUploadForm.addEventListener('submit', function(event) {
            event.preventDefault();
            
            const fileInput = document.getElementById('fileUpload');
            if (!fileInput || !fileInput.files.length) {
                alert('업로드할 파일을 선택해주세요.');
                return;
            }
            
            uploadFile(fileInput.files[0]);
        });
    }
}

>>>>>>> yimjunsu
// [1] 마이페이지에서 (로그인된) 내정보 불러오기
function getMyInfo() {
    // 1. fetch 이용한 내 정보 요청과 응답 받기
    fetch('/member/myinfo', { method: 'GET' })
        .then(response => response.json())
        .then(data => {
            if (data) { // 응답 결과가 존재하면
                // 사용자 정보 저장
                currentUserInfo = data;
                
                // 프로필 헤더 설정
                let profileHeader = '';
                if (data.role === "company") {
                    profileHeader = `<div class="profileheader">든든한 업체 ${data.mname}🏢</div>`;
                    // 업체 관련 컨트롤 표시
                    const roleSpecificControls = document.getElementById('roleSpecificControls');
                    if (roleSpecificControls) {
                        roleSpecificControls.style.display = 'block';
                    }
                } else if (data.role === "master") {
                    profileHeader = `<div class="profileheader">열혈 숨은 고수 ${data.mname}님🔥</div>`;
                    // 마스터 관련 컨트롤 표시
                    const roleSpecificControls = document.getElementById('roleSpecificControls');
                    if (roleSpecificControls) {
                        roleSpecificControls.style.display = 'block';
                    }
                } else if (data.role === "requester") {
                    profileHeader = `<div class="profileheader">반갑습니다. ${data.mname}님😁</div>`;
                }

                // 프로필 헤더 업데이트
                const profileHeaderElement = document.querySelector('.profileheader');
                if (profileHeaderElement) {
                    profileHeaderElement.innerHTML = profileHeader;
                }
                
                // 입력 필드 채우기
                const emailInput = document.getElementById('memail');
                if (emailInput) emailInput.value = data.memail;
                
                const nameInput = document.getElementById('mname');
                if (nameInput) nameInput.value = data.mname;
                
                const phoneInput = document.getElementById('mphone');
                if (phoneInput) phoneInput.value = data.mphone;
                
                const addrInput = document.getElementById('maddr');
                if (addrInput) addrInput.value = data.maddr;
                
                const roleInput = document.getElementById('mrole');
                if (roleInput) roleInput.value = getRoleDisplayName(data.role);
                
                // 프로필 이미지 가져오기
                getProfileImage(data.mno);
                
                // 업체/마스터인 경우 첨부 파일 정보 가져오기
                if (data.role === "company" || data.role === "master") {
                    getMemberFiles(data.mno);
                }
            }
        }).catch(e => { console.log('사용자 정보 로드 오류:', e); });
}

// 역할 이름을 표시용 이름으로 변환
function getRoleDisplayName(role) {
    switch (role) {
        case 'company': return '업체';
        case 'master': return '정리수납 전문가';
        case 'requester': return '개인의뢰인';
        default: return role;
    }
}

// 프로필 이미지 가져오기
function getProfileImage(mno) {
    fetch(`/member/profileimage/${mno}`, { method: 'GET' })
        .then(response => response.json())
        .then(data => {
            const profileImage = document.getElementById('profileImage');
            if (!profileImage) return;
            
            // 변경: 새로운 URL 구조 사용
            if (data && data.profile) {
                // 프로필 이미지 경로
                const imagePath = `/uploads/profile/${data.profile}`;
                
                // 이미지가 존재하는지 확인
                const img = new Image();
                img.onload = function() {
                    profileImage.src = imagePath;
                };
                img.onerror = function() {
                    // 이미지 로드 실패 시 기본 이미지 유지
                    console.log('프로필 이미지 로드 실패, 기본 이미지 사용');
                };
                img.src = imagePath;
            }
            // imageUrl이 있는 경우 직접 사용
            else if (data && data.imageUrl) {
                profileImage.src = data.imageUrl;
            }
        })
        .catch(e => {
            console.log('프로필 이미지 로드 오류:', e);
        });
}

// 첨부 파일 정보 가져오기
function getMemberFiles(mno) {
    fetch(`/member/files/${mno}`, { method: 'GET' })
        .then(response => response.json())
        .then(data => {
            if (data && data.length > 0) {
                memberFiles = data;
            }
        })
        .catch(e => {
            console.log('파일 정보 로드 오류:', e);
        });
}

// 비밀번호 확인 모달 표시
function showPwdConfirmModal(mode = 'edit') {
    const modal = document.getElementById('pwdConfirmModal');
    const pwdConfirmInput = document.getElementById('pwdConfirmInput');
    const pwdConfirmBtn = document.getElementById('pwdConfirmBtn');
    const pwdConfirmMessage = document.getElementById('pwdConfirmMessage');
    
    if (modal && pwdConfirmInput && pwdConfirmBtn) {
        // 모달 초기화
        pwdConfirmInput.value = '';
        if (pwdConfirmMessage) pwdConfirmMessage.textContent = '';
        
        // 모드 설정 (수정 또는 삭제)
        pwdConfirmBtn.setAttribute('data-mode', mode);
        
        // 모달 표시
        modal.style.display = 'block';
        pwdConfirmInput.focus();
    }
}

// 비밀번호 확인 모달 숨기기
function hidePwdConfirmModal() {
    const modal = document.getElementById('pwdConfirmModal');
    if (modal) {
        modal.style.display = 'none';
        
        // 입력 필드 초기화
        const pwdConfirmInput = document.getElementById('pwdConfirmInput');
        const pwdConfirmMessage = document.getElementById('pwdConfirmMessage');
        
        if (pwdConfirmInput) pwdConfirmInput.value = '';
        if (pwdConfirmMessage) pwdConfirmMessage.textContent = '';
    }
}

// 비밀번호 확인 처리
function checkPassword(password, mode) {
    const pwdConfirmMessage = document.getElementById('pwdConfirmMessage');
    
    fetch('/member/checkpwd', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ mpwd: password })
    })
    .then(response => response.json())
<<<<<<< HEAD
    .then(data =>{
        if(data != ''){ // 응답 결과가 존재하면
            let profileHeader = '';
            if (data.role === "company") {
                profileHeader = `<div class="profileheader">든든한 업체 ${data.mname}🏢</div>`;
            } else if (data.role === "master") {
                profileHeader = `<div class="profileheader">열혈 숨은 고수 ${data.mname}님🔥</div>`;
            } else if (data.role === "requester") {
                profileHeader = `<div class="profileheader">반갑습니다. ${data.mname}님😁</div>`;
            }

            // 프로필 헤더가 표시될 곳을 찾아서 내용 삽입
            document.querySelector('.profileheader').innerHTML = profileHeader;
            // 응답 결과를 각 input value에 각 정보들을 대입하기
            document.querySelector('.memailInput').value = data.memail;
            document.querySelector('.mnameInput').value = data.mname;
            document.querySelector('.mphoneInput').value = data.mphone;
            document.querySelector('.maddrInput').value = data.maddr;
=======
    .then(data => {
        if (data === true) {
            // 비밀번호 확인 성공
            hidePwdConfirmModal();
            
            if (mode === 'edit') {
                // 수정 모드로 전환
                enterEditMode();
            } else if (mode === 'delete') {
                // 계정 탈퇴 처리
                deleteAccount();
            }
        } else {
            // 비밀번호 확인 실패
            if (pwdConfirmMessage) {
                pwdConfirmMessage.textContent = '비밀번호가 일치하지 않습니다.';
                pwdConfirmMessage.className = 'form-message error-message';
            }
        }
    })
    .catch(error => {
        console.error('비밀번호 확인 오류:', error);
        if (pwdConfirmMessage) {
            pwdConfirmMessage.textContent = '서버 오류가 발생했습니다. 다시 시도해주세요.';
            pwdConfirmMessage.className = 'form-message error-message';
        }
    });
}

// 수정 모드 진입
function enterEditMode() {
    isEditMode = true;
    
    // 버튼 텍스트 변경
    const editModeBtn = document.getElementById('editModeBtn');
    if (editModeBtn) {
        editModeBtn.textContent = '수정 취소';
        editModeBtn.onclick = exitEditMode;
    }
    
    // 입력 필드 읽기 전용 해제
    const nameInput = document.getElementById('mname');
    const phoneInput = document.getElementById('mphone');
    
    if (nameInput) {
        nameInput.readOnly = false;
        nameInput.classList.add('editable');
    }
    
    if (phoneInput) {
        phoneInput.readOnly = false;
        phoneInput.classList.add('editable');
    }
    
    // 주소 관련 필드 표시
    const addrInput = document.getElementById('maddr');
    const addressGroup = document.getElementById('addressGroup');
    const addressField = document.getElementById('address');
    const detailAddressField = document.getElementById('detailAddress');
    
    if (addrInput) addrInput.style.display = 'none';
    if (addressGroup) addressGroup.style.display = 'flex';
    if (addressField) addressField.style.display = 'block';
    if (detailAddressField) detailAddressField.style.display = 'block';
    
    // 현재 주소를 주소 필드에 채우기 (간단한 처리)
    const currentAddr = addrInput ? addrInput.value : '';
    if (addressField && currentAddr) {
        // 상세주소를 제외한 부분만 설정 (간단한 처리)
        addressField.value = currentAddr.split(' ').slice(0, -1).join(' ');
    }
    
    if (detailAddressField && currentAddr) {
        // 마지막 부분을 상세주소로 설정 (간단한 처리)
        detailAddressField.value = currentAddr.split(' ').pop();
    }
    
    // 비밀번호 변경 섹션 표시
    const passwordChangeSection = document.getElementById('passwordChangeSection');
    if (passwordChangeSection) {
        passwordChangeSection.style.display = 'block';
    }
    
    // 액션 버튼 그룹 표시
    const actionButtons = document.getElementById('actionButtons');
    if (actionButtons) {
        actionButtons.style.display = 'flex';
    }
}

// 수정 모드 종료
function exitEditMode() {
    isEditMode = false;
    
    // 버튼 텍스트 복원
    const editModeBtn = document.getElementById('editModeBtn');
    if (editModeBtn) {
        editModeBtn.textContent = '수정 모드';
        editModeBtn.onclick = function() {
            showPwdConfirmModal();
        };
    }
    
    // 입력 필드 읽기 전용 복원
    const nameInput = document.getElementById('mname');
    const phoneInput = document.getElementById('mphone');
    
    if (nameInput) {
        nameInput.readOnly = true;
        nameInput.classList.remove('editable');
        nameInput.value = currentUserInfo.mname; // 원래 값 복원
    }
    
    if (phoneInput) {
        phoneInput.readOnly = true;
        phoneInput.classList.remove('editable');
        phoneInput.value = currentUserInfo.mphone; // 원래 값 복원
    }
    
    // 주소 관련 필드 숨김
    const addrInput = document.getElementById('maddr');
    const addressGroup = document.getElementById('addressGroup');
    const addressField = document.getElementById('address');
    const detailAddressField = document.getElementById('detailAddress');
    
    if (addrInput) {
        addrInput.style.display = 'block';
        addrInput.value = currentUserInfo.maddr; // 원래 값 복원
    }
    
    if (addressGroup) addressGroup.style.display = 'none';
    if (addressField) addressField.style.display = 'none';
    if (detailAddressField) detailAddressField.style.display = 'none';
    
    // 비밀번호 변경 섹션 숨김
    const passwordChangeSection = document.getElementById('passwordChangeSection');
    const passwordFields = document.getElementById('passwordFields');
    const togglePwdBtn = document.getElementById('togglePwdBtn');
    
    if (passwordChangeSection) {
        passwordChangeSection.style.display = 'none';
    }
    
    if (passwordFields) {
        passwordFields.style.display = 'none';
    }
    
    if (togglePwdBtn) {
        togglePwdBtn.textContent = '변경하기';
    }
    
    // 비밀번호 관련 입력 필드 초기화
    const currentPwd = document.getElementById('currentPwd');
    const newPwd = document.getElementById('newPwd');
    const confirmPwd = document.getElementById('confirmPwd');
    
    if (currentPwd) currentPwd.value = '';
    if (newPwd) {
        newPwd.value = '';
        newPwd.disabled = true;
    }
    if (confirmPwd) {
        confirmPwd.value = '';
        confirmPwd.disabled = true;
    }
    
    // 메시지 초기화
    const currentPwdMessage = document.getElementById('currentPwdMessage');
    const pwdHelp = document.getElementById('pwdHelp');
    const confirmMessage = document.getElementById('confirmMessage');
    
    if (currentPwdMessage) currentPwdMessage.textContent = '';
    if (pwdHelp) pwdHelp.className = 'form-message';
    if (confirmMessage) confirmMessage.textContent = '';
    
    // 액션 버튼 그룹 숨김
    const actionButtons = document.getElementById('actionButtons');
    if (actionButtons) {
        actionButtons.style.display = 'none';
    }
    
    // 비밀번호 변경 여부 초기화
    passwordChanged = false;
}

// 현재 비밀번호 확인
function verifyCurrentPassword(password) {
    const currentPwdMessage = document.getElementById('currentPwdMessage');
    const newPwd = document.getElementById('newPwd');
    
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
            // 현재 비밀번호 확인 성공
            if (currentPwdMessage) {
                currentPwdMessage.textContent = '✓ 비밀번호가 확인되었습니다.';
                currentPwdMessage.className = 'form-message success-message';
            }
            
            // 새 비밀번호 입력 활성화
            if (newPwd) {
                newPwd.disabled = false;
                newPwd.focus();
            }
        } else {
            // 현재 비밀번호 확인 실패
            if (currentPwdMessage) {
                currentPwdMessage.textContent = '✗ 비밀번호가 일치하지 않습니다.';
                currentPwdMessage.className = 'form-message error-message';
            }
            
            // 새 비밀번호 입력 비활성화
            if (newPwd) {
                newPwd.disabled = true;
                newPwd.value = '';
            }
            
            // 비밀번호 확인 입력 비활성화
            const confirmPwd = document.getElementById('confirmPwd');
            if (confirmPwd) {
                confirmPwd.disabled = true;
                confirmPwd.value = '';
            }
        }
    })
    .catch(error => {
        console.error('비밀번호 확인 오류:', error);
        if (currentPwdMessage) {
            currentPwdMessage.textContent = '✗ 서버 오류가 발생했습니다.';
            currentPwdMessage.className = 'form-message error-message';
        }
    });
}

// 새 비밀번호 유효성 검사
function validateNewPassword() {
    const newPwd = document.getElementById('newPwd');
    const confirmPwd = document.getElementById('confirmPwd');
    const pwdHelp = document.getElementById('pwdHelp');
    
    if (!newPwd || !confirmPwd || !pwdHelp) return;
    
    const password = newPwd.value;
    const regex = /^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d]{8,20}$/; // 영어+숫자 포함, 8~20자
    
    if (password.length === 0) {
        pwdHelp.textContent = '비밀번호는 영어와 숫자를 포함하고, 8~20자 이내여야 합니다.';
        pwdHelp.className = 'form-message';
        confirmPwd.disabled = true;
        confirmPwd.value = '';
    } else if (regex.test(password)) {
        pwdHelp.textContent = '✓ 유효한 비밀번호입니다.';
        pwdHelp.className = 'form-message success-message';
        confirmPwd.disabled = false;
    } else {
        pwdHelp.textContent = '✗ 비밀번호는 영어와 숫자를 포함하고, 8~20자 이내여야 합니다.';
        pwdHelp.className = 'form-message error-message';
        confirmPwd.disabled = true;
        confirmPwd.value = '';
    }
    
    // 비밀번호 확인도 재검사
    checkPasswordMatch();
}

// 비밀번호 확인 일치 검사
function checkPasswordMatch() {
    const newPwd = document.getElementById('newPwd');
    const confirmPwd = document.getElementById('confirmPwd');
    const confirmMessage = document.getElementById('confirmMessage');
    
    if (!newPwd || !confirmPwd || !confirmMessage) return;
    
    const password = newPwd.value;
    const passwordConfirm = confirmPwd.value;
    
    if (!passwordConfirm) {
        confirmMessage.textContent = '';
        return;
    }
    
    if (password === passwordConfirm) {
        confirmMessage.textContent = '✓ 비밀번호가 일치합니다.';
        confirmMessage.className = 'form-message success-message';
        passwordChanged = true;
    } else {
        confirmMessage.textContent = '✗ 비밀번호가 일치하지 않습니다.';
        confirmMessage.className = 'form-message error-message';
        passwordChanged = false;
    }
}

// 다음 주소 API
function execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function(data) {
            // 기본적으로 주소를 변수에 담음
            var addr = '';
            var extraAddr = '';

            // 사용자가 선택한 주소 타입에 따라 주소를 가져옴
            if (data.userSelectedType === 'R') { // 도로명 주소
                addr = data.roadAddress;
            } else { // 지번 주소
                addr = data.jibunAddress;
            }

            // 도로명 주소일 경우 참고항목 추가
            if (data.userSelectedType === 'R') {
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                document.getElementById("extraAddress").value = extraAddr; // 참고항목 입력
            } else {
                document.getElementById("extraAddress").value = ''; // 지번 주소인 경우 참고항목 비우기
            }

            // 우편번호와 주소를 해당 필드에 넣기
            document.getElementById('zipcode').value = data.zonecode; // 우편번호
            document.getElementById("address").value = addr; // 주소 입력

            // 상세주소는 빈 칸으로 두고 포커스를 상세주소 입력란으로 이동
            document.getElementById("detailAddress").value = ''; // 상세주소는 자동 입력되지 않음
            document.getElementById("detailAddress").focus(); // 상세주소 필드로 포커스 이동
            
            // 전체 주소 업데이트
            updateFullAddress();
>>>>>>> yimjunsu
        }
    }).open();
}

<<<<<<< HEAD
// [2] 마이페이지 에서 (로그인된)회원탈퇴 요청하기
const onDelete = ( ) => {
    // * 예/아니요 형식으로 탈퇴 여부를 묻고 아니요 이면 탈퇴를 중지한다.
    let result = confirm('정말 탈퇴 하실건가요?');
    if( result == false ) { return; }
    // 1. fetch 이용한 회월탈퇴 서비스 요청 과 응답 받기
    fetch( '/member/delete.do' , { method : "DELETE"} )
    .then( response => response.json() )
    .then( data => {
        if( data == true ){ alert('탈퇴 성공'); location.href='/'; }
        else{ alert('탈퇴 실패'); }
    }).catch( e => { console.log(e); })
} // f enmd

// [3] 마이페이지에서 (로그인된)회원정보 수정버튼 클릭시 비밀번호확인

const checkPwd = () => {
    const pwd = document.querySelector('#password').value;

    fetch(`/member/checkpwd.do`, {
        method: "POST", // ✅ GET 대신 POST 사용
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ mpwd: pwd }) // ✅ URL에 노출되지 않도록 Body에 담음
    })
    .then(response => response.json())
    .then(data => {
        if (data === true) {
            window.location.href = '/member/update'; // ✅ 회원정보 수정 페이지로 이동
        } else {
            alert("비밀번호가 일치하지 않습니다.");
            window.location.reload(); // ✅ 페이지 새로고침
        }
    })
    .catch(e => console.error("비밀번호 확인 오류:", e));
};
// 비밀번호 변경 클릭시 비밀번호입력창 나오도록
document.addEventListener("DOMContentLoaded", function () {
    const changePwdBtn = document.getElementById("changePwdBtn");
    const passwordChangeForm = document.getElementById("passwordChangeForm");

    // "비밀번호 변경" 버튼 클릭 시 폼 표시
    changePwdBtn.addEventListener("click", function () {
      if (passwordChangeForm.style.display === "none" || passwordChangeForm.style.display === "") {
        passwordChangeForm.style.display = "block";
      } else {
        passwordChangeForm.style.display = "none";
      }
    });
  });


// 내 정보 수정
const onUpdate = ( ) => {
    // 1. 입력받은 input value 값 가져오기.
    let memail = document.querySelector('.memailInput').value;
    let mpwd = document.querySelector('#mpwdInput').value;
    let mname = document.querySelector('.mnameInput').value;
    let mphone = document.querySelector('.mphoneInput').value;
    let maddr = document.querySelector('.maddrInput').value;

    // 2. 객체화
    let dataObj = { memail : memail , mpwd : mpwd , mname : mname , mphone : mphone , maddr : maddr }
    // 3. fetch
    const option = {
        method : 'PUT' ,
        headers : {'Content-Type' : 'application/json'},
        body : JSON.stringify( dataObj )
    }// o end
    fetch( '/member/update.do' , option )
    .then( response => response.json() )
    .then( data => {
        if( data ){ alert('수정 성공'); location.href="/member/mypage"; }
        else{ alert('수정 실패');}
    }).catch( e => { console.log(e); } )
}// f end
=======
// 전체 주소 업데이트
function updateFullAddress() {
    const address = document.getElementById('address').value;
    const detailAddress = document.getElementById('detailAddress').value;
    const extraAddress = document.getElementById('extraAddress').value;
    const maddrInput = document.getElementById('maddr');
    
    if (!address) return;
    
    // 전체 주소 생성
    const fullAddress = address + 
                       (detailAddress ? ' ' + detailAddress : '') + 
                       (extraAddress ? ' ' + extraAddress : '');
    
    // maddr 입력 필드에 설정
    if (maddrInput) {
        maddrInput.value = fullAddress;
    }
}

// 사용자 정보 저장
function saveUserInfo() {
    // 현재 입력 값 가져오기
    const nameInput = document.getElementById('mname');
    const phoneInput = document.getElementById('mphone');
    const addrInput = document.getElementById('maddr');
    
    const name = nameInput ? nameInput.value : '';
    const phone = phoneInput ? phoneInput.value : '';
    const addr = addrInput ? addrInput.value : '';
    
    // 유효성 검사
    if (!name) {
        alert('이름을 입력해주세요.');
        if (nameInput) nameInput.focus();
        return;
    }
    
    if (!phone) {
        alert('전화번호를 입력해주세요.');
        if (phoneInput) phoneInput.focus();
        return;
    }
    
    if (!addr) {
        alert('주소를 입력해주세요.');
        const detailAddress = document.getElementById('detailAddress');
        if (detailAddress) detailAddress.focus();
        return;
    }
    
    // 비밀번호 변경 여부 확인
    const currentPwd = document.getElementById('currentPwd');
    const newPwd = document.getElementById('newPwd');
    const confirmPwd = document.getElementById('confirmPwd');
    const passwordFields = document.getElementById('passwordFields');
    
    let pwd = null;
    
    // 비밀번호 필드가 표시되어 있고, 모든 입력이 있는 경우에만 비밀번호 변경
    if (passwordFields && passwordFields.style.display !== 'none' &&
        currentPwd && currentPwd.value && 
        newPwd && newPwd.value && 
        confirmPwd && confirmPwd.value) {
        
        // 비밀번호 일치 여부 확인
        if (newPwd.value !== confirmPwd.value) {
            alert('새 비밀번호가 일치하지 않습니다.');
            confirmPwd.focus();
            return;
        }
        
        // 비밀번호 유효성 검사
        const regex = /^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d]{8,20}$/;
        if (!regex.test(newPwd.value)) {
            alert('비밀번호는 영어와 숫자를 포함하고, 8~20자 이내여야 합니다.');
            newPwd.focus();
            return;
        }
        
        // 비밀번호 변경
        pwd = newPwd.value;
    }
    
    // 전송할 데이터 준비
    const updateData = {
        memail: currentUserInfo.memail,
        mname: name,
        mphone: phone,
        maddr: addr
    };
    
    // 비밀번호 변경 시 추가
    if (pwd) {
        updateData.mpwd = pwd;
    }
    
    // 저장 버튼 비활성화
    const saveBtn = document.getElementById('saveBtn');
    if (saveBtn) {
        saveBtn.disabled = true;
        saveBtn.textContent = '저장 중...';
    }
    
    // 서버에 업데이트 요청
    fetch('/member/update', {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(updateData)
    })
    .then(response => response.json())
    .then(data => {
        if (data === true) {
            alert('회원 정보가 성공적으로 수정되었습니다.');
            // 수정 모드 종료
            exitEditMode();
            // 최신 정보로 새로고침
            getMyInfo();
        } else {
            alert('회원 정보 수정에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('회원 정보 수정 오류:', error);
        alert('서버 오류가 발생했습니다. 다시 시도해주세요.');
    })
    .finally(() => {
        // 저장 버튼 상태 복원
        const saveBtn = document.getElementById('saveBtn');
        if (saveBtn) {
            saveBtn.disabled = false;
            saveBtn.textContent = '저장하기';
        }
    });
}

// 계정 탈퇴 처리
function deleteAccount() {
    fetch('/member/delete', { method: "DELETE" })
        .then(response => response.json())
        .then(data => {
            if (data) {
                alert('회원 탈퇴가 완료되었습니다.');
                location.href = '/';
            } else {
                alert('회원 탈퇴에 실패했습니다. 다시 시도해주세요.');
            }
        }).catch(e => {
            console.error('회원 탈퇴 오류:', e);
            alert('서버 오류가 발생했습니다. 다시 시도해주세요.');
        });
}

// 프로필 이미지 업로드
function uploadProfileImage(event) {
    const file = event.target.files[0];
    if (!file) return;
    
    // 이미지 파일인지 확인
    if (!file.type.startsWith('image/')) {
        alert('이미지 파일만 업로드 가능합니다.');
        return;
    }
    
    const formData = new FormData();
    formData.append('file', file);
    
    // 로딩 표시
    const profileImage = document.getElementById('profileImage');
    if (profileImage) {
        profileImage.style.opacity = '0.5';
    }
    
    fetch('/member/upload/profile', {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data) {
            // 변경: 새로운 URL 구조 사용
            const imagePath = `/uploads/profile/${data}`;
            if (profileImage) {
                profileImage.src = imagePath;
            }
            alert('프로필 이미지가 업데이트되었습니다.');
        } else {
            alert('프로필 이미지 업로드에 실패했습니다.');
        }
    })
    .catch(e => {
        console.error('프로필 이미지 업로드 오류:', e);
        alert('프로필 이미지 업로드 중 오류가 발생했습니다.');
    })
    .finally(() => {
        // 로딩 표시 제거
        if (profileImage) {
            profileImage.style.opacity = '1';
        }
    });
}

// 추가 파일 업로드
function uploadFile(file) {
    if (!file) {
        alert('업로드할 파일을 선택해주세요.');
        return;
    }
    
    // 이미지 파일인지 확인
    if (!file.type.startsWith('image/')) {
        alert('이미지 파일만 업로드 가능합니다.');
        return;
    }
    
    const formData = new FormData();
    formData.append('file', file);
    
    // 역할에 따른 API 엔드포인트 결정
    let uploadUrl;
    if (currentUserInfo.role === 'company') {
        uploadUrl = '/member/upload/company';
    } else if (currentUserInfo.role === 'master') {
        uploadUrl = '/member/upload/master';
    } else {
        alert('파일을 업로드할 수 없는 역할입니다.');
        return;
    }
    
    // 업로드 버튼 비활성화
    const uploadBtn = document.querySelector('#fileUploadForm button');
    if (uploadBtn) {
        uploadBtn.disabled = true;
        uploadBtn.textContent = '업로드 중...';
    }
    
    // 업로드 요청
    fetch(uploadUrl, {
        method: 'POST',
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        if (data) {
            alert('파일이 업로드되었습니다.');
            // 파일 목록 새로고침
            getMemberFiles(currentUserInfo.mno);
            // 모달 다시 열기
            setTimeout(() => showFileListModal(true), 1000);
            
            // 파일 입력 필드 초기화
            const fileInput = document.getElementById('fileUpload');
            if (fileInput) fileInput.value = '';
        } else {
            alert('파일 업로드에 실패했습니다.');
        }
    })
    .catch(e => {
        console.error('파일 업로드 오류:', e);
        alert('파일 업로드 중 오류가 발생했습니다.');
    })
    .finally(() => {
        // 업로드 버튼 상태 복원
        const uploadBtn = document.querySelector('#fileUploadForm button');
        if (uploadBtn) {
            uploadBtn.disabled = false;
            uploadBtn.textContent = '업로드';
        }
    });
}

// 파일 목록/수정 모달 표시
function showFileListModal(enableUpload) {
    const modal = document.getElementById('fileModal');
    const fileList = document.getElementById('fileList');
    const fileUploadSection = document.getElementById('fileUploadSection');
    
    if (!modal || !fileList) return;
    
    fileList.innerHTML = ''; // 목록 초기화
    
    if (memberFiles.length === 0) {
        fileList.innerHTML = '<p>첨부된 파일이 없습니다.</p>';
    } else {
        // 파일 목록 렌더링
        memberFiles.forEach(file => {
            const fileItem = document.createElement('div');
            fileItem.className = 'file-item';
            
            // 파일 경로 결정 (업체 또는 마스터에 따라) - 변경: 새로운 URL 구조 사용
            let filePath;
            if (currentUserInfo.role === 'company') {
                filePath = `/uploads/company/${file.mfname}`;
            } else if (currentUserInfo.role === 'master') {
                filePath = `/uploads/master/${file.mfname}`;
            }
            
            fileItem.innerHTML = `
                <img src="${filePath}" alt="첨부파일">
                <div class="file-info">
                    <div class="file-name">${file.mfname.split('-').pop()}</div>
                    <div class="file-date">업로드: ${formatDate(file.cdate)}</div>
                </div>
                <div class="file-action">
                    <button class="view-btn" data-path="${filePath}">보기</button>
                    ${enableUpload ? `<button class="delete-btn" data-id="${file.mfno}">삭제</button>` : ''}
                </div>
            `;
            
            fileList.appendChild(fileItem);
        });
        
        // 보기 버튼 이벤트 처리
        document.querySelectorAll('.view-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                window.open(this.getAttribute('data-path'), '_blank');
            });
        });
        
        // 삭제 버튼 이벤트 처리 (수정 모드일 때만)
        if (enableUpload) {
            document.querySelectorAll('.delete-btn').forEach(btn => {
                btn.addEventListener('click', function() {
                    const fileId = this.getAttribute('data-id');
                    deleteFile(fileId, this.closest('.file-item'));
                });
            });
        }
    }
    
    // 업로드 섹션 표시/숨김
    if (fileUploadSection) {
        fileUploadSection.style.display = enableUpload ? 'block' : 'none';
    }
    
    // 모달 표시
    modal.style.display = 'block';
}

// 파일 삭제 처리
function deleteFile(fileId, fileElement) {
    if (!confirm('이 파일을 삭제하시겠습니까?')) return;
    
    fetch(`/member/file/delete/${fileId}`, { method: 'DELETE' })
        .then(response => response.json())
        .then(data => {
            if (data) {
                // UI에서 파일 항목 제거
                fileElement.remove();
                
                // 파일 목록에서도 제거
                memberFiles = memberFiles.filter(file => file.mfno != fileId);
                
                const fileList = document.getElementById('fileList');
                if (memberFiles.length === 0 && fileList) {
                    fileList.innerHTML = '<p>첨부된 파일이 없습니다.</p>';
                }
                
                alert('파일이 삭제되었습니다.');
            } else {
                alert('파일 삭제에 실패했습니다.');
            }
        })
        .catch(e => {
            console.error('파일 삭제 오류:', e);
            alert('파일 삭제 중 오류가 발생했습니다.');
        });
}

// 날짜 포맷팅 함수
function formatDate(dateString) {
    if (!dateString) return '날짜 정보 없음';
    
    try {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    } catch (e) {
        console.error('날짜 포맷팅 오류:', e);
        return dateString;
    }
}
>>>>>>> yimjunsu
