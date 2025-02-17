console.log("onEstimateWrite open!")

// 이전 페이지의 URL을 가져옵니다.
const referrerUrl = document.referrer;

// URLSearchParams를 사용하여 쿼리 파라미터를 추출합니다.
const referrerParams = new URLSearchParams(new URL(referrerUrl).search);

// reqno 파라미터 값을 가져옵니다.
const reqno = referrerParams.get('reqno');

// 결과를 출력합니다.
console.log("이전 페이지 URL : " + referrerUrl);
console.log('이전 페이지의 reqno: ' + reqno);  // 이제 정상적으로 'reqno' 값을 출력합니다.

// 1. 견적서 올리기
const onEstimateWrite = () => {
    // 요청글 상세페이지 아직 안만들어져서 임시 주석, reqno 임의값 설정
    const reqno = referrerParams.get('reqno'); 
    
    // 1. input dom 가져오기
    let esttitleValue = document.querySelector('.esttitleValue')
    let estcontentValue = document.querySelector('.estcontentValue')
    let estcashValue = document.querySelector('.estcashValue')
    
    if (!esttitleValue || !estcontentValue || !estcashValue) {
      alert('입력칸이 비어있습니다.');
      return false;
    }
    
    // 2. dom value 값 가져오기
    let esttitle = esttitleValue.value; console.log(esttitle);
    let estcontent = estcontentValue.value; console.log(estcontent);
    let estcash = estcashValue.value; console.log(estcash);

    // *유효성 검사(모든 요소 빈칸 X)
    if(esttitle === ''){
      alert('견적서 제목을 입력해주세요')
      return false;
    } else if(estcontent === ''){
      alert('견적서 내용을 입력해주세요')
      return false;
    } else if(estcash === ''){
      alert('견적을 입력해주세요')
      return false;
    }

    // 3. 객체화
    const estdto = {
          esttitle : esttitle,
          estcontent : estcontent,
          estcash : estcash,
          reqno : reqno
        }

    // *
    let result = confirm("견적 등록하시겠습니까?")
    if(result == false){return;}

    // 4. fetch
    const option = {
      method : 'POST',
      headers : { 'Content-Type' : 'application/json' },
      body : JSON.stringify(estdto)
    }
    fetch('/estimate/write.do' , option)
    .then(r=>r.json())
    .then(data => {
      console.log(data)
      if(data == true){
        alert("견적서 업로드 성공!")
        location.href='/'; // 해당 요청서로 이동하기 추후 수정 
      }else{
        alert("요청서 업로드에 실패했습니다.")
      }
    })
    .catch(e=>{console.log(e)})
  }





//========================================== 참고 효과 js =======================================================//
const alertPlaceholder = document.getElementById('liveAlertPlaceholder');

const appendAlert = (message, type) => {
  // 기존 알람이 있다면 추가하지 않음
  if (alertPlaceholder.children.length > 0) return;

  message = message.replace('불이익', '<span class="warning-text">불이익</span>');

  const wrapper = document.createElement('div');
  wrapper.innerHTML = [
    `<div class="alert alert-${type} alert-dismissible" role="alert" style="font-size:13px; background-color:#D4A373; color: white;">`,
    `   <div>${message}</div>`,
    '   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>',
    '</div>'
  ].join('');

  alertPlaceholder.append(wrapper);
};

const alertTrigger = document.getElementById('liveAlertBtn');
if (alertTrigger) {
  alertTrigger.addEventListener('click', () => {
    appendAlert('견적서 작성된 견적과 실제 견적이 일치하지 않을 경우 불이익을 받을 수 있습니다.', 'success');
  });
}


// 스타일 정의
const style = document.createElement('style');
style.innerHTML = `
  .warning-text {
    color: red;
    font-weight: bold;
  }
`;
document.head.appendChild(style);

// 숫자 함수
function comma(str) {
    str = String(str);
    return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
} // 콤마 붙이는 함수

function uncomma(str) {
    str = String(str);
    return str.replace(/[^\d]+/g, '');
} // 콤마를 때는 함수
    
function inputNumberFormat(obj) {
    obj.value = comma(uncomma(obj.value));
} // 숫자만 사용할 수 있는 함수(+콤마)
    
function onlynumber(str) {
	str = String(str);
	return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g,'$1 원');
}
