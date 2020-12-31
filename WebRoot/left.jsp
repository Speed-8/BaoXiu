<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="sidebar-nav">






	<a href="#accounts-menu1" class="nav-header" data-toggle="collapse">
		<i class="icon-dashboard"></i>管理菜单</a>


	<c:if test="${user.role==0}">
        
        <a href="#accounts-menu2" class="nav-header" data-toggle="collapse">
        <i class="icon-briefcase"></i>用户管理</a>
        
        <ul id="accounts-menu2" class="nav nav-list collapse <c:if test="${menu==2}">in</c:if>">
        <li><a href="method!useradd?menu=2">新增库存管理员</a></li>
             <li><a href="method!userlist?menu=2">库存管理员管理</a></li>
             
             <li><a href="method!useradd10?menu=2">新增报修管理员</a></li>
             <li><a href="method!userlist2?menu=2">报修管理员管理</a></li>
             
            <li ><a href="method!userlist3?menu=2">注册用户管理</a></li>
          
        </ul>
        
        
        <a href="#accounts-menu3" class="nav-header" data-toggle="collapse">
        <i class="icon-briefcase"></i>设备管理</a>
        
         <ul id="accounts-menu3" class="nav nav-list collapse <c:if test="${menu==3}">in</c:if>">
         	 <li><a href="method!fenleiadd?menu=3">新增设备分类</a></li>
             <li><a href="method!fenleilist?menu=3">设备分类管理</a></li>
            
             <li ><a href="method!shebeilist6?menu=3">设备查询</a></li>
          
        </ul>
        
        
        
        
        

     </c:if>
     
     
     
     <c:if test="${user.role==1}">
        
        <a href="#accounts-menu2" class="nav-header" data-toggle="collapse">
        <i class="icon-briefcase"></i>入库</a>
        
        <ul id="accounts-menu2" class="nav nav-list collapse <c:if test="${menu==2}">in</c:if>">
        <li><a href="method!rukuadd?menu=2">新设备入库</a></li>
             <li><a href="method!rukulist?menu=2">入库记录查询</a></li>

        </ul>
        
        
        <a href="#accounts-menu3" class="nav-header" data-toggle="collapse">
        <i class="icon-briefcase"></i>库存</a>
        
        <ul id="accounts-menu3" class="nav nav-list collapse <c:if test="${menu==3}">in</c:if>">
        <li><a href="method!kucunlist?menu=3">查询库存信息</a></li>

        </ul>
        
        
         <a href="#accounts-menu4" class="nav-header" data-toggle="collapse">
        <i class="icon-briefcase"></i>出库</a>
        
        <ul id="accounts-menu4" class="nav nav-list collapse <c:if test="${menu==4}">in</c:if>">
        <li><a href="method!kucunlist2?menu=4">设备出库</a></li>
        
        <li><a href="method!chukulist?menu=4">出库记录查询</a></li>

        </ul>
        
        
        <a href="#accounts-menu5" class="nav-header" data-toggle="collapse">
        <i class="icon-briefcase"></i>更换</a>
        
        <ul id="accounts-menu5" class="nav nav-list collapse <c:if test="${menu==5}">in</c:if>">

             <li><a href="method!genghuanlist3?menu=5">受理更换</a></li>
             
             <li><a href="method!genghuanlist4?menu=5">更换管理</a></li>

        </ul>
      
      <a href="#accounts-menu6" class="nav-header" data-toggle="collapse">
        <i class="icon-briefcase"></i>报废</a>
        
        <ul id="accounts-menu6" class="nav nav-list collapse <c:if test="${menu==6}">in</c:if>">

			<li><a href="method!shebeilist5?menu=6">报废管理</a></li>

           

        </ul>
        

     </c:if>
     
     
     
     
      <c:if test="${user.role==2}">
        
        <a href="#accounts-menu2" class="nav-header" data-toggle="collapse">
        <i class="icon-briefcase"></i>设备查询</a>
        
        <ul id="accounts-menu2" class="nav nav-list collapse <c:if test="${menu==2}">in</c:if>">

            <li><a href="method!shebeilist3?menu=2">设备信息查询</a></li>

        </ul>
        
        
     <a href="#accounts-menu3" class="nav-header" data-toggle="collapse">
        <i class="icon-briefcase"></i>报修</a>
        
        <ul id="accounts-menu3" class="nav nav-list collapse <c:if test="${menu==3}">in</c:if>">

			<li><a href="method!baoxiulist2?menu=3">报修信息查询</a></li>

            <li><a href="method!shebeilist4?menu=3">申请报修</a></li>

            <li><a href="method!baoxiulist?menu=3">我的报修进度管理</a></li>

        </ul>
        
        
        <a href="#accounts-menu4" class="nav-header" data-toggle="collapse">
        <i class="icon-briefcase"></i>更换</a>
        
        <ul id="accounts-menu4" class="nav nav-list collapse <c:if test="${menu==4}">in</c:if>">

			<li><a href="method!genghuanlist2?menu=4">更换信息查询</a></li>

            <li><a href="method!baoxiulist5?menu=4">申请更换</a></li>

            <li><a href="method!genghuanlist?menu=4">我的更换进度管理</a></li>

        </ul>
        
        
         
      
        

     </c:if>
     
     
     
     
     <c:if test="${user.role==3}">
        
        <a href="#accounts-menu2" class="nav-header" data-toggle="collapse">
        <i class="icon-briefcase"></i>报修</a>
        
        <ul id="accounts-menu2" class="nav nav-list collapse <c:if test="${menu==2}">in</c:if>">

             <li><a href="method!baoxiulist3?menu=2">受理报修</a></li>
             
             <li><a href="method!baoxiulist4?menu=2">报修管理</a></li>

        </ul>
        
        
        
      
        

     </c:if>
     
     
     

        <a href="method!changepwd" class="nav-header" ><i class="icon-briefcase"></i>修改密码</a>
        <a href="method!loginout" class="nav-header" ><i class="icon-briefcase"></i>退出系统</a>
    </div>
