!function(t){var o={init:function(){this.initEvents()},initEvents:function(){t(".good-type-add").on("click",function(o){o.preventDefault(),t("#good_type_id").val(""),t("#good_type_name").val(""),t("#good_type_remark").val("")}),t("#save_good_type").on("click",function(o){o.preventDefault();var e=t("#good_type_form"),i=e.serialize();return!("submitting"==e.attr("submitting")||!e.valid())&&(e.attr("submitting","submitting"),void t.post("/good/saveGoodType",i,function(o){e.attr("submitting",""),1==o.code?t.tipsSuccessAlert("商品类别设置保存成功！",function(){location.reload()}):(t.logConsole("商品类别设置保存失败",o.message),t.tipsWarningAlert("商品类别设置保存失败"))}))}),t(".good-type-list").on("click",".type-item",function(o){o.preventDefault();var e=t(this).attr("data-id");t.post("/good/goodTypeInfo",{goodTypeId:e},function(o){var e=o.data;1==o.code?(t("#good_type_id").val(e.goodTypeId),t("#good_type_name").val(e.goodTypeName),t("#good_type_remark").val(e.goodTypeDescribe)):(t.logConsole("商品类别信息查询失败",o.message),t.tipsWarningAlert("商品类别信息查询失败"))})})}};o.init()}(jQuery);