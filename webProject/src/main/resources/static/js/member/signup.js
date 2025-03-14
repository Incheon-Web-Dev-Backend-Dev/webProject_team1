// 사업자등록번호 실시간 유효성 검사
const checkBusinessRegNumber = () => {
  const businessRegNumberInput = document.querySelector('#businessRegNumber');
  const businessRegNumber = businessRegNumberInput.value;

  // 사업자등록번호가 비어있으면 검사를 중지
  if (!businessRegNumber) {
    document.querySelector('#businessRegNumberResult').textContent = '';
    return;
  }

  // 선택된 박스 강조
  event.currentTarget.style.backgroundColor = "#5e9ce2";

  // 1. 요청 자료 만들기 (사업자번호에 '-' 있을 경우, '-' 제거)
  const data = { "b_no": [businessRegNumber.replaceAll('-', '')] };

  // 2. API URL 및 서비스 키
  const url = 'https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=';
  const serviceKey = '%2F95Bygi6tZzHEY3%2FNPjymiClQUmbL3Eox3lMEIk4hRKAXsX0owCEksZAUNh4YznGonviQ6yaWTZrmeIup6Kw7w%3D%3D';

  // 3. fetch를 사용한 API 요청
  const option = {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(data)
  };

  // 4. fetch 요청 후 응답 처리
  fetch(url + serviceKey, option)
    .then(response => response.json())
    .then((responseData) => {
      const resultArea = document.querySelector('#businessRegNumberResult');

      // status_code가 "OK"이고, match_cnt가 1 이상이면 사업자번호 일치
      if (responseData.status_code === "OK" && responseData.match_cnt > 0 && responseData.data.length > 0) {
        // 사업자등록증이 유효한 경우
        const validData = responseData.data[0]; // 첫 번째 데이터가 유효한 사업자정보
        resultArea.textContent = `✅ 유효한 사업자등록번호입니다.`;
        resultArea.style.color = 'green';
      } else if (responseData.status_code === "OK" && responseData.match_cnt === 0) {
        // 일치하는 사업자등록번호가 없을 경우
        resultArea.textContent = '❌ 일치하는 사업자등록번호가 없습니다.';
        resultArea.style.color = 'red';
      } else {
        // 응답에 오류가 있거나 데이터가 없는 경우
        resultArea.textContent = '❌ 사업자등록번호 확인에 실패했습니다. 다시 시도해 주세요.';
        resultArea.style.color = 'red';
      }
    })
    .catch((error) => {
      console.log(error);
      const resultArea = document.querySelector('#businessRegNumberResult');
      resultArea.textContent = '❌ 사업자등록번호 확인에 실패했습니다. 다시 시도해 주세요.';
      resultArea.style.color = 'red';
    });
};

      // 역할 선택에 따른 동작
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

        // 사업자등록증 입력란 가져오기
        const businessRegNumberContainer = document.getElementById("businessRegNumberContainer");

        if (role === "requester") {
          fileUploadField.style.display = "none"; // 일반 파일 업로드 숨김
          profileUploadField.style.display = "block"; // 프로필 사진 업로드만 표시
          businessRegNumberContainer.style.display = "none"; // 사업자등록증 입력란 숨김
        } else {
          fileUploadField.style.display = "block";
          profileUploadField.style.display = "block";
          businessRegNumberContainer.style.display = "none"; // 기본적으로 사업자등록증 입력란 숨김

          if (role === "company") {
            fileUploadLabel.textContent = "업체 관련 사진 첨부"; // 업체 선택 시 라벨 변경
            businessRegNumberContainer.style.display = "block"; // 업체 선택 시 사업자등록증 번호 입력란 보이기
          } else if (role === "master") {
            fileUploadLabel.textContent = "수료증, 범죄사실증명서류 첨부💾"; // 전문가 선택 시 라벨 변경
          }
        }
      }

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
            document.getElementById('postcode').value = data.zonecode; // 우편번호
            document.getElementById("address").value = addr; // 주소 입력

            // 상세주소는 빈 칸으로 두고 포커스를 상세주소 입력란으로 이동
            document.getElementById("detailAddress").value = ''; // 상세주소는 자동 입력되지 않음
            document.getElementById("detailAddress").focus(); // 상세주소 필드로 포커스 이동

            // 디버깅을 위한 로그 출력
            console.log("우편번호: " + data.zonecode);
            console.log("주소: " + addr);
            console.log("참고 주소: " + extraAddr);

            // 지도 표시
            var mapContainer = document.getElementById('map');
            if (mapContainer) {
                mapContainer.style.display = "block"; // 지도 표시를 위해 강제로 보이게 설정
            }

            var mapOption = {
                center: new daum.maps.LatLng(37.537187, 127.005476), // 기본 위치
                level: 5 // 확대 레벨
            };

            // 지도 객체 생성
            var map = new daum.maps.Map(mapContainer, mapOption);
            var geocoder = new daum.maps.services.Geocoder();
            var marker = new daum.maps.Marker({
                position: new daum.maps.LatLng(37.537187, 127.005476), // 기본 위치
                map: map
            });

            // 주소로 좌표 변환 후 지도에 표시
            geocoder.addressSearch(data.address, function(results, status) {
                if (status === daum.maps.services.Status.OK) {
                    var result = results[0]; // 첫 번째 결과 사용
                    var coords = new daum.maps.LatLng(result.y, result.x);

                    // 지도 중심을 변환된 좌표로 변경
                    map.setCenter(coords);
                    marker.setPosition(coords); // 마커를 변환된 좌표로 이동
                }
            });
        }
    }).open();
}




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
document.getElementById("signupForm")
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
    const address = document.querySelector('#address').value;
    const detailAddress = document.querySelector('#detailAddress').value;
    
    const maddr = address + detailAddress;
    formData.append('maddr',maddr)

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

