<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.park.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.wanwei.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="/Content/style/mobile/home/dashboard.css?v=${static_resource_version}" rel="stylesheet" type="text/css">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
<div id="main" class="container">
    <div class="weui-flex">
        <div class="weui-flex__item">
            <div class="placeholder">
                <img src="/Content/images/new/logo.png?v=${static_resource_version}" alt="场地预订">
            </div>
        </div>
    </div>
    <div class="weui-flex">
        <div class="weui-grids">
            <a href="/mobile/reservation/sequence" class="weui-grid">
                <div class="weui-grid__icon">
                    <img src="/Content/images/mobile/stadium.png?v=${static_resource_version}" alt="场地预订">
                </div>
                <p class="weui-grid__label">场地预订</p>
            </a>
            <a href="/mobile/reservation/orders" class="weui-grid">
                <div class="weui-grid__icon">
                    <img src="/Content/images/mobile/purchase-order.png?v=${static_resource_version}" alt="场地预订">
                </div>
                <p class="weui-grid__label">我的订单</p>
            </a>
            <a href="/mobile/center/profile" class="weui-grid">
                <div class="weui-grid__icon">
                    <img src="/Content/images/mobile/user.png?v=${static_resource_version}" alt="用户信息">
                </div>
                <p class="weui-grid__label">账户编辑</p>
            </a>
            <a href="/mobile/center/password" class="weui-grid">
                <div class="weui-grid__icon">
                    <img src="/Content/images/mobile/key.png?v=${static_resource_version}" alt="用户信息">
                </div>
                <p class="weui-grid__label">修改密码</p>
            </a>
            <a href="/mobile/center/card" class="weui-grid">
                <div class="weui-grid__icon">
                    <img src="/Content/images/mobile/cards.png?v=${static_resource_version}" alt="用户信息">
                </div>
                <p class="weui-grid__label">会员卡</p>
            </a>
            <a href="/mobile/center/about" class="weui-grid">
                <div class="weui-grid__icon">
                    <img src="/Content/images/mobile/about.png?v=${static_resource_version}" alt="系统消息">
                </div>
                <p class="weui-grid__label">关于我们</p>
            </a>
        </div>
    </div>
</div>
</layout:override>

<c:import url="../Shared/Layout.jsp">
    <c:param name="title" value="万为体育"/>
</c:import>
