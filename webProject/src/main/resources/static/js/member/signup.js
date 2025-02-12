$(".form")
  .find("input, textarea")
  .on("keyup blur focus", function (e) {
    var $this = $(this),
      label = $this.prev("label");

    if (e.type === "keyup") {
      if ($this.val() === "") {
        label.removeClass("active highlight");
      } else {
        label.addClass("active highlight");
      }
    } else if (e.type === "blur") {
      if ($this.val() === "") {
        label.removeClass("active highlight");
      } else {
        label.removeClass("highlight");
      }
    } else if (e.type === "focus") {
      if ($this.val() === "") {
        label.removeClass("highlight");
      } else if ($this.val() !== "") {
        label.addClass("highlight");
      }
    }
  });
$(document).ready(function () {
  // 페이지 로드 시 첫 번째 탭만 표시
  $(".tab-content > div").hide();
  $("#companysignup").show();

  // 탭 클릭 시 해당 탭만 표시
  $(".tab a").on("click", function (e) {
    e.preventDefault();

    $(this).parent().addClass("active");
    $(this).parent().siblings().removeClass("active");

    let target = $(this).attr("href");
    $(".tab-content > div").hide();
    $(target).fadeIn(600);
  });

  // 비밀번호 유효성 검사
  $('input[type="password"]').on("keyup", function () {
    let password = $('input[type="password"]').eq(0).val();
    let confirmPassword = $('input[type="password"]').eq(1).val();
    let passwordMessage = $("#password-message");
    let confirmMessage = $("#confirm-message");

    let hasNumber = /[0-9]/.test(password);
    let hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
    let isValidLength = password.length >= 8;

    if (!isValidLength || !hasNumber || !hasSpecialChar) {
      passwordMessage
        .text("비밀번호는 8자 이상, 숫자 및 특수문자를 포함해야 합니다.")
        .css("color", "red");
    } else {
      passwordMessage.text("사용 가능한 비밀번호입니다.").css("color", "green");
    }

    if (confirmPassword.length > 0) {
      if (password !== confirmPassword) {
        confirmMessage
          .text("비밀번호가 일치하지 않습니다.")
          .css("color", "red");
      } else {
        confirmMessage.text("비밀번호가 일치합니다.").css("color", "green");
      }
    }
  });
});

$(".tab a").on("click", function (e) {
  e.preventDefault();

  $(this).parent().addClass("active");
  $(this).parent().siblings().removeClass("active");

  target = $(this).attr("href");

  $(".tab-content > div").not(target).hide();

  $(target).fadeIn(600);
});
