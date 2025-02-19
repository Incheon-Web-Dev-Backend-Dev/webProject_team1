function selectRole(role, event) {
  document.getElementById("selectedRole").value = role;

  // 모든 역할 박스의 배경색 초기화
  document.querySelectorAll(".role-box").forEach((box) => {
    box.style.backgroundColor = "";
  });

  // 선택된 박스 강조
  event.currentTarget.style.backgroundColor = "#e0e0e0";

  // 파일 업로드 관련 요소 가져오기
  const fileUploadField = document.querySelector('input[name="uploadFile"]').closest(".mb-3");
  const profileUploadField = document.querySelector('input[name="profile"]').closest(".mb-3");
  const fileUploadLabel = document.querySelector('label[for="mfile"]'); // 파일 업로드 필드의 라벨

  if (role === "requester") {
    fileUploadField.style.display = "none"; // 일반 파일 업로드 숨김
    profileUploadField.style.display = "block"; // 프로필 사진 업로드만 표시
  } else {
    fileUploadField.style.display = "block";
    profileUploadField.style.display = "block";

    if (role === "company") {
      fileUploadLabel.textContent = "업체 관련 사진 첨부"; // 업체 선택 시 라벨 변경
    } else if (role === "master") {
      fileUploadLabel.textContent = "수료증, 범죄사실증명서류 첨부"; // 전문가 선택 시 라벨 변경
    }
  }
}

// 페이지 로드 시 파일 업로드 필드 및 비밀번호 확인창 비활성화
document.addEventListener("DOMContentLoaded", function () {
  document.querySelector('input[name="uploadFile"]').closest(".mb-3").style.display = "none";
  document.querySelector('input[name="profile"]').closest(".mb-3").style.display = "none";
  document.getElementById("mpwdConfirm").disabled = true; // 비밀번호 확인창 비활성화
});

// 비밀번호 유효성 검사
document.getElementById("mpwd").addEventListener("input", function () {
  const password = this.value;
  const confirmMessage = document.getElementById("confirmMessage");
  const confirmInput = document.getElementById("mpwdConfirm");
  const regex = /^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d]{8,20}$/; // 영어+숫자 포함, 8~20자

  if (password.length === 0) {
    confirmMessage.textContent = ""; // 비밀번호가 없으면 메시지 초기화
    confirmInput.disabled = true; // 비밀번호 확인창 비활성화
    confirmInput.value = ""; // 비밀번호 확인 입력 초기화
  } else if (regex.test(password)) {
    confirmMessage.textContent = ""; // 메시지 초기화
    confirmInput.disabled = false; // 비밀번호가 유효하면 비밀번호 확인창 활성화
  } else {
    confirmMessage.textContent = "비밀번호는 영어와 숫자를 포함하고, 8~20자 이내여야 합니다.";
    confirmMessage.style.color = "red";
    confirmInput.disabled = true; // 비밀번호 확인창 비활성화
    confirmInput.value = ""; // 비밀번호 확인 입력 초기화
  }
});

// 비밀번호 확인 실시간 검사
document.getElementById("mpwdConfirm").addEventListener("input", function () {
  const password = document.getElementById("mpwd").value;
  const passwordConfirm = this.value;
  const confirmMessage = document.getElementById("confirmMessage");

  if (password === passwordConfirm) {
    confirmMessage.textContent = "✅ 비밀번호가 일치합니다.";
    confirmMessage.style.color = "green";
  } else {
    confirmMessage.textContent = "❌ 비밀번호가 일치하지 않습니다.";
    confirmMessage.style.color = "red";
  }
});
// 이메일 중복 검사 함수
function checkEmailDuplicate() {
  const email = document.getElementById("memail").value;
  const emailErrorMessage = document.getElementById("emailErrorMessage");

  // 이메일이 비어있으면 중복 검사를 진행하지 않음
  if (email === "") {
    emailErrorMessage.textContent = "";
    return;
  }

  // 중복 검사 요청 (fetch)
  fetch(`/member/checkemail.do?email=${encodeURIComponent(email)}`)
    .then((response) => response.json()) // JSON 응답 처리
    .then((data) => {
      if (data) {
        emailErrorMessage.textContent = "❌ 이미 사용 중인 이메일입니다.";
        emailErrorMessage.style.color = "red";
      } else {
        emailErrorMessage.textContent = "✅ 사용 가능한 이메일입니다.";
        emailErrorMessage.style.color = "green";
      }
    })
    .catch((error) => {
      console.error("이메일 중복 검사 오류:", error);
      emailErrorMessage.textContent = "❌ 중복 검사에 실패했습니다. 다시 시도해 주세요.";
      emailErrorMessage.style.color = "red";
    });
}

// 버튼 클릭 시 중복 검사 호출
document.getElementById("checkEmailBtn").addEventListener("click", checkEmailDuplicate);

// 회원가입 폼 제출 시 유효성 검사 및 서버 요청
document
  .getElementById("signupForm")
  .addEventListener("submit", function (event) {
    event.preventDefault(); // 기본 제출 방지 (fetch로 직접 처리할 것이므로)

    const selectedRole = document.getElementById("selectedRole").value;
    const password = document.getElementById("mpwd").value;
    const passwordConfirm = document.getElementById("mpwdConfirm").value;

    // 역할 선택 확인
    if (!selectedRole) {
      alert("역할을 선택해야 회원가입이 가능합니다.");
      return;
    }

    // 비밀번호 확인
    if (password !== passwordConfirm) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    // 비밀번호 유효성 검사
    const regex = /^(?=.*[a-zA-Z])(?=.*\d)[A-Za-z\d]{8,20}$/; // 영어+숫자 포함, 8~20자
    if (!regex.test(password)) {
      alert("비밀번호는 영어와 숫자를 포함하고, 8~20자 이내여야 합니다.");
      return;
    }


    // 서버에 회원가입 요청
    const formData = new FormData(event.target); // 폼 데이터 생성

    fetch("/member/signup.do", {
      method: "POST",
      body: formData,
    })
      .then((response) => {
        if (response.ok) {
          return response.json(); // JSON 응답 처리
        }
        throw new Error("회원가입 실패");
      })
      .then((data) => {

        // 필요에 따라 리다이렉션 또는 다음 단계 처리
        data.uploadfile = null;
        alert("🎉 회원가입이 완료되었습니다! 환영합니다."); // 성공 메시지
        location.href = "/"; // 메인 페이지로 리다이렉션
      })
      .catch((error) => {
        console.error("회원가입 오류:", error);
        alert("❌ 회원가입에 실패했습니다. 다시 시도해 주세요."); // 실패 메시지
      });
  });
