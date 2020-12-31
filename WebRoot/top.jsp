<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="navbar">
        <div class="navbar-inner">
                <ul class="nav pull-right">
                    <c:if test="${user.role==0}">
                    <li><a href="method!changepwd3" class="hidden-phone visible-tablet visible-desktop" role="button">修改密码</a></li>
                    </c:if>
                     <c:if test="${user.role!=0}">
                    <li><a href="method!changepwd" class="hidden-phone visible-tablet visible-desktop" role="button">修改密码</a></li>
                    </c:if>
                    
                    <li><a href="method!loginout" class="hidden-phone visible-tablet visible-desktop" role="button">退出系统</a></li>
                    
                    
                    
                </ul>
                <a class="brand" href="index.jsp"><span class="second">设备报修管理系统</span></a>
        </div>
    </div>