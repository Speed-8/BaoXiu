<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="en">
  <head>
  <base href="<%=basePath%>">
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
	 
	


	return true;
	
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
    
    <%@ include file="../top.jsp" %>
    


    
     <%@ include file="../left.jsp" %>
    

    
    <div class="content">
        
        <div class="header">
            
            <h1 class="page-title">${title }</h1>
        </div>
        
                

        <div class="container-fluid">
            <div class="row-fluid">
                    

<div class="well">
    
    <div id="myTabContent" class="tab-content">
      <div class="tab-pane active in" id="home">
    <form id="tab"	method="post" action="${url }" onsubmit="return checkform()">
        
        
         <label>设备信息</label>
        设备分类名:${bean.shebei.fenlei.fname }&nbsp;&nbsp;
        设备名:${bean.shebei.mingchen }&nbsp;&nbsp;
        设备编号:${bean.shebei.bianhao }&nbsp;&nbsp;
        
         <label>报修地点</label>
        <input type="text" name="didian" id="didianid" class="input-xlarge"  value="${bean.didian }" readonly="readonly">
        
        
        
        <label>故障描述</label>
        <textarea rows="7" cols="50" name="miaoshu" id="miaoshuid" readonly="readonly">${bean.miaoshu }</textarea>
        
         <label>处理情况</label>
        <select name="chuli">
        <option value="维修完成">维修完成</option>
        <option value="维修失败">维修失败</option>
        <option value="需要协助">需要协助</option>
        <option value="建议返厂">建议返厂</option>
        <option value="建议更换">建议更换</option>
        </select>
        
        
        
        <label>处理反馈</label>
        <textarea rows="7" cols="50" name="fankui" id="fankuiid" ></textarea>
        
    
    <label>操作</label>
     
  
    <input type="submit" value="提交"  /> 
     
    <input type="button" value="返回" onclick="javascript:history.go(-1);"/>
     
   
    
    </form>
      </div>
      
      
  </div>

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



