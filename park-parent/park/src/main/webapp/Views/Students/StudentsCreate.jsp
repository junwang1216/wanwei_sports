<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ page import="com.park.layout.Blocks" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- JSTL表达式（判断，循环，输出） --%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> <%-- 方法表达式（字符串截取，替换） --%>
<%@ taglib uri="http://www.wanwei.com/tags/tag" prefix="layout" %>

<layout:override name="<%=Blocks.BLOCK_HEADER_CSS%>">
    <link href="Content/style/common/style.min.css?v=${static_resource_version}" rel="stylesheet" type="text/css">
</layout:override>

<layout:override name="<%=Blocks.BLOCK_HEADER_SCRIPTS%>">
    <script src="Content/lib/jquery/jquery.validate/jquery.validate.js?v=${static_resource_version}"></script>
    <script src="Content/lib/jquery/jquery.validate.unobtrusive/jquery.validate.unobtrusive.js?v=${static_resource_version}"></script>
    <script src="Content/app/students/students_create.js?v=${static_resource_version}"></script>
    <script>
        // 表单校验配置
        $(document).ready(function () {
            $('#student_form').validate({
                ignore: ":hidden"
            });
        });
    </script>
</layout:override>

<layout:override name="<%=Blocks.BLOCK_BODY%>">
    <div class="container-fluid" style="text-align: left">
        <form id="student_form" class="form-horizontal" novalidate onsubmit="return false;">
            <div class="panel panel-default">
                <div class="panel-heading">学生办卡</div>
                <div class="panel-body">
                    <div class="alert alert-info text-center" role="alert">办卡押金：<span class="text-danger">${cardDeposit}</span>元</div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="card_no" class="col-sm-4 control-label">
                                <span class="text-danger">*</span> 学生卡号
                            </label>

                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="card_no" name="cardNo"
                                       placeholder="学生卡号" value="${cardNo}" autocomplete="off"
                                       data-val="true" data-val-required="学生卡号不能为空" readonly>
                                <div data-valmsg-for="cardNo" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="student_degree" class="col-sm-4 control-label">
                                <span class="text-danger">*</span> 所在班级
                            </label>

                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="student_degree" name="studentGrade"
                                           placeholder="年级" autocomplete="off"
                                           data-val="true" data-val-required="所在年级不能为空"
                                           data-val-regex-pattern="^[0-9]\d*$"
                                           data-val-regex="所在年级格式错误">
                                    <span class="input-group-addon">年级</span>
                                </div>
                                <div data-valmsg-for="studentGrade" data-valmsg-replace="true"></div>
                            </div>

                            <div class="col-sm-4">
                                <div class="input-group">
                                    <input type="text" class="form-control" id="student_class" name="studentClass"
                                           placeholder="班级" autocomplete="off"
                                           data-val="true" data-val-required="所在班级不能为空"
                                           data-val-regex-pattern="^[0-9]\d*$"
                                           data-val-regex="所在班级格式错误">
                                    <span class="input-group-addon">班级</span>
                                </div>
                                <div data-valmsg-for="studentClass" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="student_mobile" class="col-sm-4 control-label">
                                <span class="text-danger">*</span> 联系手机
                            </label>

                            <div class="col-sm-8 input-parent-magnifier">
                                <input type="text" class="form-control input-element-magnifier" id="student_mobile"
                                       name="studentMobile" placeholder="请输入手机号码" autocomplete="off"
                                       data-val="true" data-val-required="手机号码不能为空"
                                       data-val-regex-pattern="^1\d{10}$"
                                       data-val-regex="手机号码格式错误" maxlength="11">
                                <div data-valmsg-for="studentMobile" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label for="student_name" class="col-sm-4 control-label">
                                <span class="text-danger">*</span> 学生姓名
                            </label>

                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="student_name" name="studentName"
                                       placeholder="请输入学生姓名" autocomplete="off"
                                       data-val="true" data-val-required="学生姓名不能为空"
                                       data-val-regex-pattern="^[A-Za-z\u4e00-\u9fa5][A-Za-z0-9\u4e00-\u9fa5_]{1,9}$"
                                       data-val-regex="学生姓名长度只能2~12个字符">
                                <div data-valmsg-for="studentName" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="student_sex1" class="col-sm-4 control-label">
                                <span class="text-danger">*</span> 学生性别
                            </label>

                            <div class="col-sm-8">
                                <label class="radio-inline">
                                    <input type="radio" name="studentSex" id="student_sex1" value="1" checked> 男
                                </label>
                                <label class="radio-inline">
                                    <input type="radio" name="studentSex" id="student_sex2" value="2"> 女
                                </label>
                                <div data-valmsg-for="studentSex" data-valmsg-replace="true"></div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-12">
                        <div class="form-group">
                            <label for="student_address" class="col-sm-2 control-label">联系人地址</label>

                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="student_address" name="studentAddress"
                                       placeholder="请输入联系地址">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="student_remark" class="col-sm-2 control-label">备注</label>

                            <div class="col-sm-10">
                                <textarea class="form-control" id="student_remark" name="studentRemark" rows="3"
                                      placeholder="备注"></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="text-center">
                                <button type="button" class="btn btn-success register-student">
                                    <span class="glyphicon glyphicon-ok"></span>  注册 & 办卡
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</layout:override>

<c:import url="../Shared/Layout.jsp">
    <c:param name="title" value="学生办卡"/>
</c:import>
