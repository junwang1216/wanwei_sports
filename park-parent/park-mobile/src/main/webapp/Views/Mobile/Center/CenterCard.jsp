<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.park.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.wanwei.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="Content/style/mobile/center/center_profile.css?v=${static_resource_version}" rel="stylesheet" type="text/css">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div id="main" class="container">
        <div class="weui-cells__title">会员卡信息</div>
        <div class="weui-cells">
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <p>会员类型</p>
                </div>
                <div class="weui-cell__ft">会员卡</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <p>余额</p>
                </div>
                <div class="weui-cell__ft">2100元</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <p>截止日期</p>
                </div>
                <div class="weui-cell__ft">不限制</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <p>销售员</p>
                </div>
                <div class="weui-cell__ft">王老师</div>
            </div>
            <div class="weui-cell">
                <div class="weui-cell__bd">
                    <p>办理日期</p>
                </div>
                <div class="weui-cell__ft">2016-11-25</div>
            </div>
        </div>

        <div class="weui-btn-area">
            <a class="weui-btn weui-btn_primary" href="javascript:">充 值</a>
        </div>
    </div>
</layout:override>

<c:import url="../Shared/Layout.jsp">
    <c:param name="title" value="会员卡信息"/>
</c:import>
