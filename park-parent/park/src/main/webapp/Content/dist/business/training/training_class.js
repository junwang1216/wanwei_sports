!function(s){s(".class-status-select").on("click",".class-status",function(t){t.preventDefault(),location.assign("/business/training/list?classStatus="+(s(this).attr("data-status")||""))})}(Zepto);