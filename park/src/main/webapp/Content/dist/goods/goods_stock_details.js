!function(e){var t={opts:{URL:"/good/getGoodsStockDetails"},init:function(){this.initEvents(),e.datetimepicker.setLocale("zh"),e("#createTimeStart").datetimepicker({timepicker:!1,lang:"zh",format:"Y-m-d",defaultDate:new Date}),e("#createTimeEnd").datetimepicker({timepicker:!1,lang:"zh",format:"Y-m-d",defaultDate:new Date})},initEvents:function(){var t=this;e(".goods-filter").on("click",function(a){a.preventDefault();var i=e("#goods_filter_form").serialize();location.assign(t.opts.URL+"?"+i)}),e(".page-first, .page-prev, .page-index, .page-next, .page-last").on("click",function(a){a.preventDefault();var i=location.search,n=e(this).attr("data-index");i?i.indexOf("page=")==-1?location.assign(t.opts.URL+"?"+i+"&page="+n):location.assign(t.opts.URL+"?"+i.replace(/(page=)\d+/,"$1"+n)):location.assign(t.opts.URL+"?page="+n)})}};t.init()}(jQuery);