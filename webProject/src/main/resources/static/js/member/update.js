// 사용자 정보와 역할을 저장할 변수
let currentUserInfo = null;
let memberFiles = [];

// DOM이 완전히 로드된 후 실행
document.addEventListener('DOMContentLoaded', function() {
    // 내 정보 로드
    getMyInfo();
    
    // 이벤트 리스너 등록
    setupEventListeners();
});

// 이벤트 리스너 설정 함수
function setupEventListeners() {
    // 비밀번호 변경 토글 버튼
    const changePwdBtn = document.getElementById('changePwdBtn');
    const passwordChangeForm = document.getElementById('passwordChangeForm');
    
    if (changePwdBtn && passwordChangeForm) {
        changePwdBtn.addEventListener('click', function() {
            if (passwordChangeForm.style.display === 'none') {
                passwordChangeForm.style.display = 'block';
                changePwdBtn.textContent = '취소';
            } else {
                passwordChangeForm.style.display = 'none';
                changePwdBtn.textContent = '변경하기';
                // 비밀번호 입력 필드 초기화
                document.getElementById('mpwd').value = '';
                document.getElementById('mpwdConfirm').value = '';
                document.getElementById('confirmMessage').textContent = '';
            }
        });
    }
    
    // 비밀번호 유효성 검사
    const mpwd = document.getElementById('mpwd');
    if (mpwd) {
        mpwd.addEventListener('input', function() {
            validatePassword();
        });
    }
    
    // 비밀번호 확인 일치 검사
    const mpwdConfirm = document.getElementById('mpwdConfirm');
    if (mpwdConfirm) {
        mpwdConfirm.addEventListener('input', function() {
            checkPasswordMatch();
        });
    }
    
    // 주소 찾기 버튼
    const searchAddrBtn = document.getElementById('searchAddrBtn');
    if (searchAddrBtn) {
        searchAddrBtn.addEventListener('click', execDaumPostcode);
    }
    
    // 취소 버튼
    const cancelBtn = document.getElementById('cancelBtn');
    if (cancelBtn) {
        cancelBtn.addEventListener('click', function() {
            if (confirm('수정을 취소하시겠습니까?')) {
                window.location.href = '/member/mypage';
            }
        });
    }
    
    // 파일 관리 버튼
    const manageFilesBtn = document.getElementById('manageFilesBtn');
    if (manageFilesBtn) {
        manageFilesBtn.addEventListener('click', function() {
            showFileListModal(true);
        });
    }
    
    // 모달 닫기 버튼
    const closeModalBtn = document.querySelector('.modal .close');
    if (closeModalBtn) {
        closeModalBtn.addEventListener('click', function() {
            const modal = document.getElementById('fileModal');
            if (modal) {
                modal.style.display = 'none';
            }
        });
    }
    
    // 모달 외부 클릭 시 닫기
    window.addEventListener('click', function(event) {
        const modal = document.getElementById('fileModal');
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
    
    // 파일 업로드 폼
    const fileUploadForm = document.getElementById('fileUploadForm');
    if (fileUploadForm) {
        fileUploadForm.addEventListener('submit', function(event) {
            event.preventDefault();
            uploadFile();
        });
    }
    
    // 프로필 이미지 업로드
    const profileUpload = document.getElementById('profileUpload');
    if (profileUpload) {
        profileUpload.addEventListener('change', function(event) {
            uploadProfileImage(event);
        });
    }
    
    // 회원정보 수정 폼
    const updateForm = document.getElementById('updateForm');
    if (updateForm) {
        updateForm.addEventListener('submit', function(event) {
            event.preventDefault();
            updateMemberInfo();
        });
    }
}

// [1] 마이페이지에서 (로그인된) 내정보 불러오기
function getMyInfo() {
    // 1. fetch 이용한 내 정보 요청과 응답 받기
    fetch('/member/myinfo', { method: 'GET' })
        .then(response => response.json())
        .then(data => {
            if (data) { // 응답 결과가 존재하면
                // 사용자 정보 저장
                currentUserInfo = data;
                
                // 입력 필드 채우기
                const memailInput = document.querySelector('.memailInput');
                if (memailInput) memailInput.value = data.memail;
                
                const mnameInput = document.querySelector('.mnameInput');
                if (mnameInput) mnameInput.value = data.mname;
                
                const mphoneInput = document.querySelector('.mphoneInput');
                if (mphoneInput) mphoneInput.value = data.mphone;
                
                // 주소 처리
                const maddrInput = document.querySelector('.maddrInput');
                if (maddrInput) maddrInput.value = data.maddr;
                
                // 주소가 있다면 주소 필드도 파싱하여 채우기
                if (data.maddr) {
                    // 주소 파싱은 복잡할 수 있으므로 간단히 표시
                    const addressField = document.getElementById('address');
                    if (addressField) addressField.value = data.maddr;
                }
                
                // 프로필 이미지 가져오기
                getProfileImage(data.mno);
                
                // 업체/마스터인 경우 첨부 파일 추가 정보 표시
                if (data.role === "company" || data.role === "master") {
                    const additionalFileSection = document.getElementById('additionalFileSection');
                    if (additionalFileSection) {
                        additionalFileSection.style.display = 'block';
                    }
                    
                    // 첨부 파일 정보 가져오기
                    getMemberFiles(data.mno);
                }
            }
        }).catch(e => { console.log('사용자 정보 로드 오류:', e); });
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

// 비밀번호 유효성 검사
function validatePassword() {
    const password = document.getElementById('mpwd').value;
    const pwdHelp = document.getElementById('pwdHelp');
    const confirmInput = document.getElementById('mpwdConfirm');
    const regex = /^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d]{8,20}$/; // 영어+숫자 포함, 8~20자
    
    if (!pwdHelp || !confirmInput) return;
    
    if (password.length === 0) {
        pwdHelp.className = "form-text";
        pwdHelp.textContent = "비밀번호는 영어와 숫자를 포함하고, 8~20자 이내여야 합니다.";
        confirmInput.disabled = true;
        confirmInput.value = "";
    } else if (regex.test(password)) {
        pwdHelp.className = "form-text success-text";
        pwdHelp.textContent = "✅ 유효한 비밀번호입니다.";
        confirmInput.disabled = false;
    } else {
        pwdHelp.className = "form-text error-text";
        pwdHelp.textContent = "❌ 비밀번호는 영어와 숫자를 포함하고, 8~20자 이내여야 합니다.";
        confirmInput.disabled = true;
        confirmInput.value = "";
    }
    
    // 비밀번호 확인도 재검사
    checkPasswordMatch();
}

// 비밀번호 확인 일치 검사
function checkPasswordMatch() {
    const password = document.getElementById('mpwd').value;
    const passwordConfirm = document.getElementById('mpwdConfirm').value;
    const confirmMessage = document.getElementById('confirmMessage');
    
    if (!confirmMessage) return;
    
    if (!passwordConfirm) {
        confirmMessage.textContent = "";
        return;
    }
    
    if (password === passwordConfirm) {
        confirmMessage.textContent = "✅ 비밀번호가 일치합니다.";
        confirmMessage.className = "form-text success-text";
    } else {
        confirmMessage.textContent = "❌ 비밀번호가 일치하지 않습니다.";
        confirmMessage.className = "form-text error-text";
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
            
            // 전체 주소 값 업데이트
            updateFullAddress();
        }
    }).open();
}

// 전체 주소 업데이트
function updateFullAddress() {
    const address = document.getElementById('address').value;
    const detailAddress = document.getElementById('detailAddress').value;
    const extraAddress = document.getElementById('extraAddress').value;
    
    // 전체 주소 생성
    const fullAddress = address + " " + detailAddress + (extraAddress ? " " + extraAddress : "");
    
    // 숨겨진 maddr 필드에 전체 주소 설정
    const maddrInput = document.getElementById('maddr');
    if (maddrInput) {
        maddrInput.value = fullAddress;
    }
}

// 상세주소 입력 시 전체 주소 업데이트
document.addEventListener('DOMContentLoaded', function() {
    const detailAddress = document.getElementById('detailAddress');
    if (detailAddress) {
        detailAddress.addEventListener('input', updateFullAddress);
    }
});

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
    const profileStatus = document.getElementById('profile-status');
    
    if (profileImage) {
        profileImage.style.opacity = '0.5';
    }
    
    if (profileStatus) {
        profileStatus.textContent = '업로드 중...';
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
            if (profileStatus) {
                profileStatus.textContent = '프로필 이미지가 업데이트되었습니다.';
                profileStatus.className = 'success-text';
                
                // 3초 후 메시지 사라짐
                setTimeout(() => {
                    profileStatus.textContent = '';
                }, 3000);
            }
        } else {
            if (profileStatus) {
                profileStatus.textContent = '프로필 이미지 업로드에 실패했습니다.';
                profileStatus.className = 'error-text';
            }
        }
    })
    .catch(e => {
        console.error('프로필 이미지 업로드 오류:', e);
        if (profileStatus) {
            profileStatus.textContent = '프로필 이미지 업로드 중 오류가 발생했습니다.';
            profileStatus.className = 'error-text';
        }
    })
    .finally(() => {
        // 로딩 표시 제거
        if (profileImage) {
            profileImage.style.opacity = '1';
        }
    });
}

// 회원정보 수정
function updateMemberInfo() {
    // 비밀번호 변경 폼이 열려있는 경우 유효성 검사
    const passwordChangeForm = document.getElementById('passwordChangeForm');
    if (passwordChangeForm && passwordChangeForm.style.display !== 'none') {
        const mpwd = document.getElementById('mpwd').value;
        const mpwdConfirm = document.getElementById('mpwdConfirm').value;
        
        // 비밀번호가 입력되었으나 유효하지 않은 경우
        if (mpwd) {
            const regex = /^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d]{8,20}$/;
            if (!regex.test(mpwd)) {
                alert('비밀번호는 영어와 숫자를 포함하고, 8~20자 이내여야 합니다.');
                return;
            }
            
            // 비밀번호 확인이 일치하지 않는 경우
            if (mpwd !== mpwdConfirm) {
                alert('비밀번호가 일치하지 않습니다.');
                return;
            }
        }
    }
    
    // 상세주소 입력 시 전체 주소 업데이트
    updateFullAddress();
    
    // 폼 데이터 수집
    const memail = document.getElementById('memail').value;
    const mname = document.getElementById('mname').value;
    const mphone = document.getElementById('mphone').value;
    const maddr = document.getElementById('maddr').value || document.getElementById('address').value;
    const mpwd = document.getElementById('mpwd').value;
    
    // 비어있는 필수 필드 확인
    if (!mname || !mphone) {
        alert('이름과 전화번호를 입력해주세요.');
        return;
    }
    
    // 전송할 데이터 객체
    const updateData = {
        memail: memail,
        mname: mname,
        mphone: mphone,
        maddr: maddr
    };
    
    // 비밀번호가 변경된 경우에만 추가
    if (mpwd) {
        updateData.mpwd = mpwd;
    }
    
    // 업데이트 버튼 상태 변경
    const updateBtn = document.getElementById('updateBtn');
    if (updateBtn) {
        const originalText = updateBtn.textContent;
        updateBtn.disabled = true;
        updateBtn.textContent = '수정 중...';
        
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
                window.location.href = '/member/mypage';
            } else {
                alert('회원 정보 수정에 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('회원 정보 수정 오류:', error);
            alert('서버 오류가 발생했습니다. 다시 시도해주세요.');
        })
        .finally(() => {
            // 버튼 상태 복원
            if (updateBtn) {
                updateBtn.disabled = false;
                updateBtn.textContent = originalText;
            }
        });
    }
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

// 파일 업로드
function uploadFile() {
    const fileInput = document.getElementById('fileUpload');
    if (!fileInput || !fileInput.files.length) {
        alert('업로드할 파일을 선택해주세요.');
        return;
    }
    
    const formData = new FormData();
    formData.append('file', fileInput.files[0]);
    
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
    
    // 업로드 버튼 찾기
    const uploadBtn = document.querySelector('#fileUploadForm button');
    if (uploadBtn) {
        const originalText = uploadBtn.textContent;
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
            fileInput.value = '';
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
            uploadBtn.textContent = originalText || '업로드';
        }
    });
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