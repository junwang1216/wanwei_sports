!function(e){var r={init:function(){this.initEvents(),e.post("/member/getNewCardNo",function(r){var a=r.data;1==r.code?e("#newCardNo").val(a.newCardNo):alert(r.message||"新会员卡号生成失败, 请稍后重试")})},calculateRefreshMoney:function(){var r=e("#refresh_money"),a=e("#refresh_send"),t=parseFloat(r.val()||"0.00"),m=parseFloat(a.val()||"0.00");return(t+m).toFixed(2)},initEvents:function(){var r=this;e("#keywords").autosuggest({url:"/member/searchMember",method:"post",queryParamName:"search",dataCallback:function(e){var r=e.data,a=[];if(1==e.code){if(r&&r.members&&r.members.length>0){for(var t=0;t<r.members.length;t++)a.push({id:r.members[t].memberId,label:r.members[t].cardNo,value:r.members[t].memberName+"("+r.members[t].memberMobile+","+r.members[t].cardNo+")"});return a}return alert("没有搜索到会员"),[]}return alert("搜索会员失败, 请稍后重试"),[]},onSelect:function(r){var a=r.data("label");e("#card_no").val(a),location.assign("/member/getMembersCardRefresh?cardNo="+a)}}),e(".member-card-filter").on("click",function(r){r.preventDefault();var a=e("#member_card_form"),t=a.serialize();return!!a.valid()&&void e.post("member/searchMember",t,function(e){var r=e.data;1==e.code?r&&r.members&&r.members.length>0?location.assign("/member/getMembersCardRefresh?cardNo="+r.members[0].cardNo):alert("没有搜索到会员"):alert("搜索会员失败, 请稍后重试")})}),e("#refresh_money, #refresh_send").on("change",function(a){a.preventDefault(),e(".refresh-total-money").text(r.calculateRefreshMoney())}),e(".refresh-card-submit").on("click",function(r){r.preventDefault();var a=e("#refresh_card_form"),t=a.serialize();return""===e("#refresh_cardId").val()?(alert("请先选择会员卡"),!1):!("submitting"==a.attr("submitting")||!a.valid())&&(a.attr("submitting","submitting"),void e.post("/member/memberCardBuBan",t,function(r){a.attr("submitting",""),1==r.code?(e("#refreshModal").modal({backdrop:!1,show:!0}),setTimeout(function(){e("#refreshModal").modal("hide"),location.reload()},3e3)):(console.log(r.message||"会员补办失败, 请稍后重试"),alert(r.message||"会员补办失败, 请稍后重试"))}))})}};r.init()}(jQuery);