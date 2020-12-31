<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.util.Util"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Util.init(request);

%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>设备报修管理系统</title>
    <meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" type="text/css" href="lib/bootstrap/css/bootstrap.css">
    
    <link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
    <link rel="stylesheet" href="lib/font-awesome/css/font-awesome.css">

    <script src="lib/jquery-1.7.2.min.js" type="text/javascript"></script>

    <!-- Demo page code -->

    <style type="text/css">
        #line-chart {
            height:300px;
            width:800px;
            margin: 0px auto;
            margin-top: 1em;
        }
        .brand { font-family: georgia, serif; }
        .brand .first {
            color: #ccc;
            font-style: italic;
        }
        .brand .second {
            color: #fff;
            font-weight: bold;
        }
    </style>

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="lib/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="../assets/ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="../assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="../assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="../assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="../assets/ico/apple-touch-icon-57-precomposed.png">
    
    
    <script language="javascript" type="text/javascript">

function checkform()
{
	 
	 if (document.getElementById('usernameid').value=="")
	{
		alert("用户名不能为空");
		return false;
	}
	var valid=/^\w+$/;
	if(!valid.test(document.getElementById('usernameid').value)){
		alert("用户名必须是数字、字母或下划线");
		return false;
	}

	if (document.getElementById('passwordid').value=="")
	{
		alert("密码不能为空");
		return false;
	}
	if (document.getElementById('passwordid').value.length<6)
	{
		alert("密码长度必须大于6位");
		return false;
	}
	if (document.getElementById('password2id').value != document.getElementById('passwordid').value)
	{
		alert("确认密码与密码不一致");
		return false;
	}	 
	if (document.getElementById('xingmingid').value=="")
	{
		alert("姓名不能为空");
		return false;
	}
	
	
	
	form1.submit();
	
}

</script>
    
  </head>

  <!--[if lt IE 7 ]> <body class="ie ie6"> <![endif]-->
  <!--[if IE 7 ]> <body class="ie ie7 "> <![endif]-->
  <!--[if IE 8 ]> <body class="ie ie8 "> <![endif]-->
  <!--[if IE 9 ]> <body class="ie ie9 "> <![endif]-->
  <!--[if (gt IE 9)|!(IE)]><!--> 
  <body class=""> 
  <!--<![endif]-->
    
    <div class="navbar">
        <div class="navbar-inner">
                <ul class="nav pull-right">
                    
                </ul>
               <a class="brand" href="login.jsp"><span class="second">设备报修管理系统</span></a>
        </div>
    </div>
    


    

    
        <div class="row-fluid">
    <div class="dialog">
        <div class="block">
            <p class="block-heading">设备报修管理系统普通用户注册</p>
            <div class="block-body">
                <form name="form1" method="post" action="method!register" >
                    <label>用户名</label>
                    <input type="text" class="span12" name="username" id="usernameid">
                    <label>密码</label>
                    <input type="password" class="span12" name="password" id="passwordid">
                    <label>确认密码</label>
                    <input type="password" class="span12" name="password2" id="password2id">
                    
                    <label>姓名</label>
                    <input type="text" class="span12" name="xingming" id="xingmingid">
                  
                    <label>性别</label>
                    <select name="xingbie">
                    <option>男</option>
                    <option>女</option>
                    </select>
                    <label>电话</label>
                    <input type="text" class="span12" name="dianhua">
                 
                    <label>生日</label>
                    <input type="text" class="span12" name="shengri">
                    
                     <a href="login.jsp" class="btn btn-primary pull-right">返回</a>
                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    
                    <a href="javascript:void(0)" onclick=" return checkform()" class="btn btn-primary pull-right">注册</a>
                   
                  
                   
                   
                    <div class="clearfix"></div>
                </form>
            </div>
        </div>
       
    </div>
</div>


    


    <script src="lib/bootstrap/js/bootstrap.js"></script>
    <script type="text/javascript">
        $("[rel=tooltip]").tooltip();
        $(function() {
            $('.demo-cancel-click').click(function(){return false;});
        });
    </script>
    
  </body>
</html>



