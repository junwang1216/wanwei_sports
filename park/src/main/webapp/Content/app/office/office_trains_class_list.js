(function ($, moment) {
    var Office_Trains_Class = {
        opts: {
            URL: '/office/getTrainsClassList'
        },
        init: function () {
            this.initEvents();
        },
        initEvents: function () {
            var content = this;

            // 检索
            $(".trains-filter").on("click", function (e) {
                e.preventDefault();

                var conditions = $("#trains_filter_form").serialize();

                location.assign(content.opts.URL + '?' + conditions);
            });

            // 分页
            $(".page-first, .page-prev, .page-index, .page-next, .page-last").on("click", function (e) {
                e.preventDefault();

                var conditions = location.search;
                var pageIndex = $(this).attr("data-index");

                if (conditions) {
                    if (conditions.indexOf("page=") == -1) {
                        location.assign(content.opts.URL + '?' + conditions + '&page=' + pageIndex);
                    } else {
                        location.assign(content.opts.URL + '?' + conditions.replace(/(page=)\d+/, '$1' + pageIndex));
                    }
                } else {
                    location.assign(content.opts.URL + '?page=' + pageIndex);
                }
            });

            function setTrainsClassInfo(data) {
                $("#add_class_modal").find("#class_id").val(data.id || '');
                $("#add_class_modal").find("#class_name").val(data.className || '');
                $("#add_class_modal").find("#course_name").val(data.courseId || '');
                $("#add_class_modal").find("#class_desc").val(data.classRemark || '');
                $("#add_class_modal").find("#start_date").val(data.startTime || '');
                $("#add_class_modal").find("#end_date").val(data.endTime || '');
                $("#add_class_modal").find("#leader_teacher").val(data.leaderName || '');
                $("#add_class_modal").find("#contact_phone").val(data.leaderMobile || '');
            }

            // 查询
            $(".class-list").on("click", ".class-view", function (e) {
                e.preventDefault();

                var noteId = $(this).attr("data-id");

                $.post('office/viewTrainsClassInfo', {noteId: noteId}, function (res) {
                    var data = res.data;

                    if (res.code == 1) {
                        setTrainsClassInfo(data);
                    } else {
                        console.log(res.message || "查询班级详情失败, 请稍后重试");
                        alert(res.message || "查询班级详情失败, 请稍后重试");
                    }
                });
            });

            // 删除
            $(".class-list").on("click", ".class-delete", function (e) {
                e.preventDefault();

                var noteId = $(this).attr("data-id");

                $.post('office/deleteTrainsClassInfo', {noteId: noteId}, function (res) {
                    if (res.code == 1) {
                        location.reload();
                    } else {
                        console.log(res.message || "删除班级详情失败, 请稍后重试");
                        alert(res.message || "删除班级详情失败, 请稍后重试");
                    }
                });
            });

            // 增加通知
            $(".class-add").on("click", function (e) {
                e.preventDefault();

                setTrainsClassInfo({});
            });

            // 保存
            $(".class-confirm").on("click", function (e) {
                e.preventDefault();

                var $form = $("#class_form");
                var conditions = $form.serialize();

                if ($form.attr("submitting") === "submitting" || !$form.valid()) {
                    return false;
                }
                $form.attr("submitting", "submitting");

                $.post('office/saveTrainsClassInfo', conditions, function (res) {
                    $form.attr("submitting", "");

                    if (res.code == 1) {
                        location.reload();
                    } else {
                        alert(res.message || "班级信息保存失败, 请稍后重试");
                    }
                });
            });
        }
    };

    Office_Trains_Class.init();
})(jQuery, moment);
