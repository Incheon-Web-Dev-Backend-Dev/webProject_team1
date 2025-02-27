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