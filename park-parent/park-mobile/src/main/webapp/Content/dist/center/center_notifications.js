!function(t){var i={opts:{URL:"/center/notifications"},init:function(){this.initEvents()},initEvents:function(){var i=this;t(".notification-filter").on("click",function(n){n.preventDefault();var e=t("#notification_filter_form").serialize();location.assign(i.opts.URL+"?"+e)}),t(".page-first, .page-prev, .page-index, .page-next, .page-last").on("click",function(n){n.preventDefault();var e=location.search,o=t(this).attr("data-index");e?e.indexOf("page=")==-1?location.assign(i.opts.URL+"?"+e+"&page="+o):location.assign(i.opts.URL+"?"+e.replace(/(page=)\d+/,"$1"+o)):location.assign(i.opts.URL+"?page="+o)}),t(".notifications-list").on("click",".notifications-view",function(i){i.preventDefault();var n=t(this).attr("data-id");t.post("center/viewNotifications",{noteId:n},function(t){1==t.code||(console.log(t.message||"查询通知详情失败, 请稍后重试"),alert(t.message||"查询通知详情失败, 请稍后重试"))})}),t(".notifications-list").on("click",".notifications-del",function(i){i.preventDefault();var n=t(this).attr("data-id");t.post("center/deleteNotifications",{noteId:n},function(t){console.log(t),1==t.code?location.reload():(console.log(t.message||"删除通知失败, 请稍后重试"),alert(t.message||"删除通知失败, 请稍后重试"))})})}};i.init()}(jQuery);