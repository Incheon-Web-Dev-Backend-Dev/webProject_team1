// login 때 역할 버튼 js
$(document).ready(function() {
    // 버튼 클릭 시 active 클래스를 토글
    $('.role-btn').click(function() {
      // 모든 버튼에서 active 클래스 제거
      $('.role-btn').removeClass('active');
      
      // 클릭된 버튼에만 active 클래스 추가
      $(this).addClass('active');
    });
  });
  