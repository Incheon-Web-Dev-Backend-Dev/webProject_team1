


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
