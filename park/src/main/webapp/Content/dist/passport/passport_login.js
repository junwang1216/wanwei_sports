!function(t){t(document).ready(function(){t("#login_form").validate({ignore:":hidden"})}),t(".login-btn").on("click",function(i){i.preventDefault();var n=t("#login_form"),e=n.serialize();return!("submitting"==n.attr("submitting")||!n.valid())&&(n.attr("submitting","submitting"),void t.post("/passport/submitUserLogin",e,function(i){n.attr("submitting",""),1==i.code?location.assign(t('[name="return_url"]').val()):(console.log(i.message||"用户登录失败, 请稍后重试"),alert(i.message||"用户登录失败, 请稍后重试"))}))})}(jQuery);