(function ($) {
    var Teachers_Create = {
        init: function () {
            $.datetimepicker.setLocale('zh');

            // 表单时间控件设置
            $('#member_birthday').datetimepicker({
                timepicker: false,
                lang: "zh",
                format:'Y-m-d',
                defaultDate: new Date(),
                maxDate: 0
            });

            $(".member-birthday-select").on("click", function (e) {
                e.preventDefault();

                $('#member_birthday').datetimepicker("show");
            });

            this.initEvents();
        },
        initEvents: function () {
            // 注册信息
            $(".register-member").on("click", function (e) {
                e.preventDefault();

                var $form = $("#member_form");
                var conditions = $form.serialize();

                if ($form.attr("submitting") == "submitting" || !$form.valid()) {
                    return false;
                }
                $form.attr("submitting", "submitting");

                $.post('/member/saveMember', conditions, function (res) {
                    var data = res.data;

                    if (res.code != 1) {
                        console.log(res.message || "教师信息注册失败, 请稍后重试");
                        alert(res.message || "教师信息注册失败, 请稍后重试");
                        return;
                    }

                    $('[name="memberId"]').val(data.memberId);
                    conditions = $form.serialize();
                    $.post('/member/saveMemberCar', conditions, function (res) {
                        $form.attr("submitting", "");

                        if (res.code == 1) {
                            $("#tips_success_modal").modal({show: true, backdrop: false});
                            setTimeout(function () {
                                $("#tips_success_modal").modal("hide");
                                location.assign('/teachers/list');
                            }, 3000);
                        } else {
                            console.log(res.message || "教师绑卡失败, 请稍后重试");
                            alert(res.message || "教师绑卡失败, 请稍后重试");
                        }
                    });
                });
            });
        }
    };

    Teachers_Create.init();
})(jQuery);
