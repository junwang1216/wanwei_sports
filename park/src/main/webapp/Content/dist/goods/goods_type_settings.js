!function(o){var t={init:function(){this.initEvents()},initEvents:function(){o(".good-type-add").on("click",function(t){t.preventDefault(),o("#good_type_id").val(""),o("#good_type_name").val(""),o("#good_type_remark").val("")}),o("#save_good_type").on("click",function(t){t.preventDefault();var e=o("#good_type_form"),i=e.serialize();return!("submitting"==e.attr("submitting")||!e.valid())&&(e.attr("submitting","submitting"),void o.post("/good/saveGoodType",i,function(t){e.attr("submitting",""),1==t.code?(o("#tips_success_modal").modal({show:!0,backdrop:!1}),setTimeout(function(){o("#tips_success_modal").modal("hide"),location.reload()},3e3)):(console.log(t.message||"商品类别设置保存失败, 请稍后重试"),alert(t.message||"商品类别设置保存失败, 请稍后重试"))}))}),o(".good-type-list").on("click",".type-item",function(t){t.preventDefault();var e=o(this).attr("data-id");o.post("/good/goodTypeInfo",{goodTypeId:e},function(t){var e=t.data;1==t.code?(o("#good_type_id").val(e.goodTypeId),o("#good_type_name").val(e.goodTypeName),o("#good_type_remark").val(e.goodTypeDescribe)):(console.log(t.message||"商品类别信息查询失败, 请稍后重试"),alert(t.message||"商品类别信息查询失败, 请稍后重试"))})})}};t.init()}(jQuery);