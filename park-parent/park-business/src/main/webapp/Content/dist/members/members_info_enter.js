!function(e){var t={opts:{ToURL:"/member/bindMembersCard"},init:function(){e.datetimepicker.setLocale("zh"),e("#member_birthday").datetimepicker({timepicker:!1,lang:"zh",format:"Y-m-d",defaultDate:new Date,maxDate:0}),e(".member-birthday-select").on("click",function(t){t.preventDefault(),e("#member_birthday").datetimepicker("show")}),this.initEvents()},initEvents:function(){var t=this;e("#member_idcard").on("change",function(t){t.preventDefault();var i=e(this).val(),r=i.replace(/^\d{6}(\d{4})(\d{2})(\d{2})[0-9xX]{4}$/,"$1-$2-$3"),n=i.replace(/^\d{16}(\d{1})[0-9xX]{1}$/,"$1");e("#member_birthday").val(r),e("[name='memberSex'][value='"+(n%2?1:2)+"']").prop("checked",!0)}),e(".register-member").on("click",function(i){i.preventDefault();var r=e(this).button("loading"),n=e("#member_form"),a=n.serialize();return!("submitting"===n.attr("submitting")||!n.valid())&&(n.attr("submitting","submitting"),void e.post("member/saveMember",a,function(i){n.attr("submitting","");var a=i.data;1==i.code?e.tipsSuccessAlert("会员信息保存成功！",function(){location.assign(t.opts.ToURL+"?memberId="+a.memberId)}):(e.logConsole("会员信息保存失败",i.message),e.tipsWarningAlert("会员信息保存失败")),r.button("reset")}))})}};t.init()}(jQuery);