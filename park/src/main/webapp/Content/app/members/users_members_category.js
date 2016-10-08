(function ($) {
    $('#card_type_form').validate({
        ignore: ":hidden"
    });

    // 筛选会员类型
    $(".card-type-filter").on("click", function (e) {
        e.preventDefault();

        var conditions = $("#card_filter_form").serialize();

        location.assign('/member/getMemberCarTypes?' + conditions);
    });

    // 增加会员类型
    $(".card-type-add").on("click", function (e) {
        e.preventDefault();

        $("#card_type_id").val("");
        $("#card_type_name").val("");
        $("#card_type_month").val(0);
        $("#card_type_discount").val(0);
        $("#card_type_money").val(0);
        $("#card_type_overdraw").val(0);
        $("#card_payment_type").val(1);
        $("#card_type_ahead").val(0);
        $("#cardTypeStatus1").prop("checked", true);
    });

    // 支付类型改变
    $("#card_payment_type").on("change", function (e) {
        e.preventDefault();

        var val = $(this).val();

        if (val == 1) {
            $(".card-type-overdraw").hide();
        } else {
            $(".card-type-overdraw").show();
        }
    });

    var ajaxLock = false;
    $("#save_card_type").on("click", function (e) {
        e.preventDefault();

        if (ajaxLock) {
            return false;
        }
        ajaxLock = true;
        
        //未选择的星期，后面的开始时间和结束变为不可提交，防止数据混乱。
        opWeekTime(ajaxLock);
        
        var conditions = $("#card_type_form").serialize();
        
        $.post('member/saveMemberCardType', conditions, function (res) {
            if (res.code == 1) {
                location.reload();
            } else {
                alert(res.message);
                ajaxLock = false;
                //如果添加失败，则变为可用，以便用户重新操作。
                opWeekTime(ajaxLock);
            }
        });
    });
    
    function opWeekTime(disabled){
    	$.each($("input[name='cardTypeWeek']"), function(index, item){
        	if(!$(this).is(":checked")){
        		$(this).parent().parent().find("option").prop("disabled", disabled);
        	}
        });
    }

    function renderCardTypeData(data) {
    	$.each(data, function(key, item){
        	$("#card_type_form").find("input[name='"+key+"']").not(":radio").not(":checkbox").val(item);
        	$("#card_type_form").find("select[name='"+key+"']").not(".timeWeek").val(item);
        });

        if (data.cardTypeStatus) {
            $("input[name='cardTypeStatus'][value='" + data.cardTypeStatus + "']").prop("checked", true);
        }
        
        //时间处理
        var timeStart = data.cardTypeTimeStart.split(",");
        var timeEnd = data.cardTypeTimeEnd.split(",");
        var selectIndex = 0;
        $.each($("input[name='cardTypeWeek']"), function(index, item){
   		 	if(data.cardTypeWeek.indexOf($(this).val()) >= 0){
        		$(this).prop("checked", true);
        		$(this).parent().parent().find("select[name='cardTypeTimeStart']").val(timeStart[selectIndex]);
        		$(this).parent().parent().find("select[name='cardTypeTimeEnd']").val(timeEnd[selectIndex]);
    			selectIndex++;
        	}else{
        		$(this).prop("checked", false);
        	}
        });
    }

    // 卡类型详情
    $(".card-type-list").on("click", ".type-item", function (e) {
        e.preventDefault();

        var id = $(this).attr("data-id");

        $.post('member/getMemberCarType', {cardTypeId: id}, function (res) {
            if (res.code == 1) {
                renderCardTypeData(res.data);
            } else {
                alert(res.message);
                ajaxLock = false;
            }
        });
    });
})(jQuery);
