window.onload = function() {
  // 클라이언트에서 로그인 상태 확인 (로컬스토리지 사용)
  let isLoggedIn = localStorage.getItem('isLoggedIn');
  console.log(isLoggedIn)

  if (!isLoggedIn) {
      location.href = "/manage/adminlogin";  // 로그인 페이지로 이동
  }
  else {
      // 로그인된 경우, 차트나 페이지를 정상적으로 보여줌
      const ctx = document.getElementById('myChart');
      
      new Chart(ctx, {
          type: 'doughnut',
          data: {
              labels: ['업체', '개인정리수납가', '일반이용자'],
              datasets: [{
                  label: '# of Votes',
                  data: [12, 19, 3],
                  borderWidth: 1
              }]
          },
          options: {
              responsive: true,  // 화면 크기에 따라 차트 크기 조정
              maintainAspectRatio: false  // aspect ratio를 유지하지 않음
          }
      });
  }
};
