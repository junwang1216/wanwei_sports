!function(t){var o={init:function(){this.initEvents();var o=t("#zhifuModal");o.find(".goods-steps").steps({enableFinishButton:!1,enablePagination:!1,enableAllSteps:!1}),this.bindPayEvents(),t(".money-num").text(this.calculateMoney())},calculateMoney:function(){var o=t(".goods-cart-list"),e=o.find(".good-count"),s=0;return e.each(function(o,e){var a=parseFloat(t(e).attr("data-money")),n=parseInt(t(e).val());s+=100*a*n}),(s/100).toFixed(2)},bindPayEvents:function(){var o=this,e=t("#zhifuModal"),s=t(".sc-ui-dialog-overlay"),a=t("#venue_goods_pay_form"),n=t("#venue_goods_paid_form"),i=t(".goods-buy-money"),d=!0;i.on("click",function(){a.find("#pay_goods_amount").val(o.calculateMoney())}),e.on("click",".user-search",function(o){o.preventDefault();var e=t.dialog({content:"数据加载...",title:"load",width:"auto",height:"auto",lock:!0});t.getJSON("/users/Search",{name:t("#pay_goods_user_name").val().trim()},function(o){var s=o.data;200==o.status?(t("#pay_goods_user_class").val(s.memberlevel),t("#pay_goods_member_number").val(s.memberid),t("#pay_goods_member_name").val(s.membername),t("#pay_goods_user_mobile").val(s.phone)):alert(o.message),e.close()})}),e.on("click",".goods-pay",function(o){o.preventDefault();var s=a.serialize();a.find(".sc-submit-tips").hide().removeClass("text-success,text-danger"),d&&a.validate().form()&&(d=!1,t.post("/goods/BuyGoods",s,function(t){200==t.status?(a.find(".sc-submit-tips").show().html("提交成功!!").addClass("text-success"),e.find(".goods-steps").steps("next",1),n.find("#pay_goods_order_no").val(t.data.orderno),n.find("#pay_goods_real_money").val(t.data.paidamount)):a.find(".sc-submit-tips").show().html(t.message).addClass("text-danger"),d=!0}).fail(function(t){console.log(t),a.find(".sc-submit-tips").show().html("网络异常, 提交预订失败!!").addClass("text-danger"),d=!0}))}),e.on("click",".goods-pay-confirm",function(o){o.preventDefault();var i=n.serialize();n.find(".sc-submit-tips").hide().removeClass("text-success,text-danger"),d&&n.validate().form()&&(d=!1,t.post("/goods/submitGoodsConfirmBuy",i,function(t){200==t.status?(n.find(".sc-submit-tips").show().html("提交成功, 3秒后自动关闭!!").addClass("text-success"),setTimeout(function(){e.hide(),s.hide()},3e3)):a.find(".sc-submit-tips").show().html(t.message).addClass("text-danger"),d=!0}).fail(function(t){console.log(t),a.find(".sc-submit-tips").show().html("网络异常, 提交预订失败!!").addClass("text-danger"),d=!0}))}),e.find(".goods-steps-close").on("click",function(t){t.preventDefault(),e.hide(),s.hide()})},initEvents:function(){var o=this,e=t(".goods-cart-list");e.on("click",".goods-remove",function(o){o.preventDefault();var e=t(this);t.post("/good/deleteCart",{shoppingId:e.attr("data-sid")},function(t){1==t.code?location.reload():alert(t.message||"移除购物车失败, 请稍后重试")})}),e.on("click",".goods-plus",function(e){e.preventDefault();var s=t(this),a=parseInt(s.parents(".input-group").find(".good-count").val());t.post("/good/addGoodsToCart",{goodId:s.attr("data-id"),amount:1},function(e){1==e.code?(s.parents(".input-group").find(".good-count").val(++a),t(".money-num").text(o.calculateMoney())):alert(e.message||"增加数量失败, 请稍后重试")})}),e.on("click",".goods-minus",function(e){e.preventDefault();var s=t(this),a=parseInt(s.parents(".input-group").find(".good-count").val());return!(a<=1)&&void t.post("/good/addGoodsToCart",{goodId:s.attr("data-id"),amount:-1},function(e){1==e.code?(s.parents(".input-group").find(".good-count").val(--a),t(".money-num").text(o.calculateMoney())):alert(e.message||"减少数量失败, 请稍后重试")})})}};o.init()}(jQuery);