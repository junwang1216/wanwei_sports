!function(e,t){var a={tpl:{BlockBooking:function(){return'<tr class="reservations-list-item" data-start="#BOOKING_START_DATE#" data-end="#BOOKING_END_DATE#" data-site="#BOOKING_SITE#"><td>#BOOKING_SPORT#</td><td>#BOOKING_START_DATE# ~ #BOOKING_END_DATE#</td><td>#BOOKING_WEEK#</td><td>#BOOKING_START_TIME# ~ #BOOKING_END_TIME#</td><td>#BOOKING_AREA#</td><td><a href="javascript:;" class="btn btn-danger reservations-delete"><span class="glyphicon glyphicon-trash"></span></a></td></tr>'}},opts:{data:{name:"散客",mobile:"",memberId:"0",opType:"2",reserveType:"1",reserveModel:"2",siteReserveDateList:[]}},init:function(){e.datetimepicker.setLocale("zh"),e("#reservations_batch_startDate").datetimepicker({timepicker:!1,lang:"zh",format:"Y-m-d",minDate:0,value:t().format("YYYY-MM-DD")}),e(".start-date-select").on("click",function(t){t.preventDefault(),e("#reservations_batch_startDate").datetimepicker("show")}),e("#reservations_batch_endDate").datetimepicker({timepicker:!1,lang:"zh",format:"Y-m-d",minDate:0,value:t().format("YYYY-MM-DD")}),e(".end-date-select").on("click",function(t){t.preventDefault(),e("#reservations_batch_endDate").datetimepicker("show")}),e("#reservations_batch_start").datetimepicker({datepicker:!1,format:"H:i",step:60,value:"06:00"}),e("#reservations_batch_end").datetimepicker({datepicker:!1,format:"H:i",step:60,value:"22:00"}),this.initEvents(),this.searchMembers(),this.changePaidMoney()},formatWeek:function(e){var a=t(e).format("e");return"0"!=a?a:7},searchMembers:function(){var t=this;e("#reservations_batch_mobile").autosuggest({url:"/member/searchMember",method:"post",queryParamName:"search",dataCallback:function(a){var r=a.data,s=[];if(1==a.code){if(r&&r.members&&r.members.length>0){for(var i=0;i<r.members.length;i++)s.push({id:r.members[i].memberMobile,label:r.members[i].memberId,value:r.members[i].memberName+"("+r.members[i].cardTypeName+")"});return s}return e("#reservations_batch_member").val("0"),e("#reservations_batch_name").val("散客"),e("#reservations_batch_opType").val("2"),t.queryMemberBalance(0),[]}return alert("搜索会员失败, 请稍后重试"),[]},onSelect:function(a){var r=a.data("label"),s=a.data("id"),i=a.data("value");e("#reservations_batch_member").val(r),e("#reservations_batch_mobile").val(s),e("#reservations_batch_opType").val("1"),e("#reservations_batch_name").val(i.replace(/\(.+\)/,"")),t.queryMemberBalance(r)}})},queryMemberBalance:function(t){return t?void e.post("/member/memberDetail",{memberId:t},function(t){var a=t.data,r=e("#reservations_paid_money").val();1==t.code?(e(".reservations-batch-cardMoney").text(a.cardBalance),e("#reservations_paid_balance").val(a.cardBalance),a.cardBalance>=r?e("#reservations_paid_money").val("0.00"):e("#reservations_paid_money").val(r-a.cardBalance)):alert(t.message||"会员余额查询失败, 请稍后重试")}):void e("#reservations_paid_balance").val("0.00")},changePaidMoney:function(){e("#reservations_paid_paySumPrice").on("change",function(t){t.preventDefault();var a=e("#reservations_paid_balance").val(),r=e(this).val();a=parseFloat(a).toFixed(2),r=parseFloat(r).toFixed(2),a>=r?e("#reservations_paid_money").val("0.00"):e("#reservations_paid_money").val(r-a)})},calculateSiteMoney:function(){var t=this;e.post("/site/calculateSiteMoney",{siteOperationJson:JSON.stringify(t.opts.data)},function(t){var a=t.data;1==t.code?(e(".reservations-batch-totalMoney").text(a.originalPrice),e(".reservations-batch-totalNum").text(a.sumNums),e("#reservations_paid_orderSumCount").val(a.sumNums),e("#reservations_paid_orderSumPrice").val(a.originalPrice),e("#reservations_paid_payCount").val(a.sumNums),e("#reservations_paid_paySumPrice").val(a.originalPrice),e("#reservations_paid_money").val(a.originalPrice)):alert(t.message||"计算金额失败, 请稍后重试")})},initEvents:function(){var t=this;e("#reservations_batch_site").on("change",function(t){t.preventDefault();var a=e(this),r='<option value="#SITEID#">#SITENAME#</option>';e.post("site/getSiteNames",{sportId:a.val(),siteStatus:"1"},function(t){var a=t.data,s="";if(1==t.code){s+='<option value="">请选择</option>';for(var i=0;i<a.siteNames.length;i++)s+=r.replace("#SITENAME#",a.siteNames[i].siteName).replace("#SITEID#",a.siteNames[i].siteId);e("#reservations_batch_siteId").html(s)}else console.log(t.message||"查询场地失败, 请稍后重试"),alert(t.message||"查询场地失败, 请稍后重试")})}),e(".reservations-list").on("click",".reservations-delete",function(a){a.preventDefault();for(var r=e(this).parents("tr.reservations-list-item"),s=r.attr("data-start"),i=r.attr("data-end"),n=r.attr("data-site"),o=t.opts.data.siteReserveDateList,c=0;c<o.length;c++)if(o[c].reserveStartDate==s&&o[c].reserveEndDate==i&&o[c].siteReserveTimeList[0].siteId==n){t.opts.data.siteReserveDateList.splice(c,1);break}r.remove(),t.calculateSiteMoney()}),e("#reservations_batch_add").on("click",function(a){function r(){var t=[],a=[];return e('input[name="reserveWeek"]:checked').each(function(){t.push(e(this).val()),a.push(e(this).attr("data-text"))}),{value:t,text:a}}a.preventDefault();var s=e("#reservations_batch_form"),i=e(".reservations-list"),n={};if(!s.valid())return!1;var o=t.opts.data;o.mobile=e("#reservations_batch_mobile").val(),o.name=e("#reservations_batch_name").val(),o.memberId=e("#reservations_batch_member").val()||"0",o.opType=e("#reservations_batch_opType").val(),n.reserveStartDate=e("#reservations_batch_startDate").val(),n.reserveEndDate=e("#reservations_batch_endDate").val(),n.reserveWeek=r().value.join(","),n.siteReserveTimeList=[{siteStartTime:e("#reservations_batch_start").val(),siteEndTime:e("#reservations_batch_end").val(),siteId:e("#reservations_batch_siteId").val()}],o.siteReserveDateList.push(n),i.append(t.tpl.BlockBooking().replace(/#BOOKING_SPORT#/g,e("#reservations_batch_site").find("option:selected").text().trim()).replace(/#BOOKING_START_DATE#/g,e("#reservations_batch_startDate").val()).replace(/#BOOKING_END_DATE#/g,e("#reservations_batch_endDate").val()).replace(/#BOOKING_START_TIME#/g,e("#reservations_batch_start").val()).replace(/#BOOKING_END_TIME#/g,e("#reservations_batch_end").val()).replace(/#BOOKING_AREA#/g,e("#reservations_batch_siteId").find("option:selected").text().trim()).replace(/#BOOKING_WEEK#/g,"("+r().text+")")),t.calculateSiteMoney()}),e("#reservations_batch_confirm").on("click",function(a){a.preventDefault();var r=e(".reservations-list");if(1==r.find("tr").size())return alert("请先加场"),!1;t.opts.data;e.post("site/saveReservationSite",{siteOperationJson:JSON.stringify(t.opts.data)},function(t){var a=t.data;1==t.code?(e("#reservations_paid_order").val(a.orderId),e("#zhifuModal").modal({backdrop:!1,show:!0})):alert(t.message||"提交预订失败, 请稍后重试")})}),e("#reservations_paid_confirm").on("click",function(t){t.preventDefault();var a=e("#reservations_paid_form"),r=a.serialize();return!("submitting"==a.attr("submitting")||!a.valid())&&(a.attr("submitting","submitting"),void e.post("/site/confirmOrder",r,function(t){a.attr("submitting",""),1==t.code?(e("#zhifuModal").modal("hide"),e("#tips_success_modal").modal({show:!0,backdrop:!1}),setTimeout(function(){e("#tips_success_modal").modal("hide"),location.reload()},3e3)):alert(t.message||"确认订单失败, 请稍后重试")}))})}};a.init()}(jQuery,moment);