!function(t){var i={opts:{URL:"/site/getSiteInfos"},init:function(){this.initEvents()},initEvents:function(){function i(){t("#site_id").val(""),t("#site_type").val(""),t("#site_name").val(""),t("#site_remark").val(""),t("#site_status1").prop("checked",!0)}var e=this;t(".page-first, .page-prev, .page-index, .page-next, .page-last").on("click",function(i){i.preventDefault();var n=location.search,a=t(this).attr("data-index");n?n.indexOf("page=")==-1?location.assign(e.opts.URL+n+"&page="+a):location.assign(e.opts.URL+n.replace(/(page=)\d+/,"$1"+a)):location.assign(e.opts.URL+"?page="+a)}),t(".site-add").on("click",function(e){e.preventDefault(),i(),t("#setting_modal").modal({backdrop:!1,show:!0})}),t(".site-list").on("click",".site-update",function(e){e.preventDefault();var n=t(this);i(),t.post("/site/getSiteInfo",{siteId:n.attr("data-id")},function(i){var e=i.data;1==i.code?(t.each(e,function(i,e){t("#site_form").find("*[name='"+i+"']").not(":radio").val(e)}),t("#site_form").find("input[name='siteStatus'][value='"+e.siteStatus+"']").prop("checked",!0),t("#setting_modal").modal({backdrop:!1,show:!0})):(t.logConsole("场地查询失败",i.message),t.tipsWarningAlert("场地查询失败"))})}),t(".site-confirm").on("click",function(i){i.preventDefault();var e=t("#site_form"),n=e.serialize();return!("submitting"==e.attr("submitting")||!e.valid())&&(e.attr("submitting","submitting"),void t.post("/site/saveSiteInfo",n,function(i){e.attr("submitting",""),1==i.code?t.tipsSuccessAlert("场地设置成功！",function(){location.reload()}):(t.logConsole("场地设置失败",i.message),t.tipsWarningAlert("场地设置失败"))}))})}};i.init()}(jQuery);