<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
	
	<meta charset="UTF-8">
	<title>用电器数据实时显示</title>

	<!-- <script type="text/javascript" src="js/jquery-1.8.3.min.js"></script>  -->
	<script type="text/javascript" src="js/bootstrap-dropdown.js"></script> 
	<script type="text/javascript" src="js/jquery-latest.js" ></script>
	<script type="text/javascript" src="js/jquery-1.8.3.min.js"></script> 
	<script type="text/javascript" src="js/jquery.scrollUp.min.js"></script> 
	<script type="text/javascript" src="js/highcharts.js"></script> 
	<script type="text/javascript" src="js/exporting.js"></script> 
	<script type="text/javascript" src="js/mainUpdate.js"></script>
	
	<!--  --><script type="text/javascript" src="js/diffUpdate.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		$(".checkradio").change(function() {
			var $selectedvalue = $("input[name='check']:checked").val();
			$.ajax({
			      type: "POST",
			      url: "txtcheck.do",
			      async: false, //表示同步，如果要得到ajax处理完后台数据后的返回值，最好这样设置
			      cache: false, 
			      dataType: 'json',   //返回值类型 
			      data: {    
	                    check : $selectedvalue    
	                },    
			    }, false);  //false表示“遮罩”，前台不显示“请稍后”进度提示
		});
	});
	</script>
	
	<link rel="stylesheet" type="text/css" href="css/demo.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
	<link rel="stylesheet" type="text/css" href="css/animate-custom.css" />
	
	<style>
	#content{
		margin:auto;
     	width:100%;
     	height:400px;
	}
	#charts1{
		float:left;
		width: 50%; 
		height: 295px; 
		margin: 0 auto;
	}
	#charts2{
		float:right;
		width: 50%; 
		height: 295px; 
		margin: 0 auto;
	}
	#App-container1{
		float:left;
		width:70%;
	}
	#App-container2{
		float:right;
		width:30%;
		font-size:40px;
		left: 50%;
		top: 50%;
	}
	#App-container2 #setback {
	 	float:center;
		height:180px;
		width:100%;
		line-height:180px;  
		overflow:hidden; 
		font-family:'微软雅黑';
		font-weight:bold;
		color: #7cbcd6;
		
	}
	#App-container2 #checkradio{
	 	font-size:15px;
	 	float:center;
		width:100%;
		height:30px; 
		line-height:30px;  
		overflow:hidden; 
		font-family:'微软雅黑';
		font-weight:bold;
		color: #7cbcd6;
	}
	#App-container2 #openstate{
	 	font-size:20px;
	 	float:left;
		width:100%;
		height:20px; 
		line-height:20px;  
		overflow:hidden; 
		font-family:'微软雅黑';
		font-weight:bold;
		color: #7cbcd6;
	}
	#charts3{
		float:right;
		width: 71.43%; 
		height: 295px; 
		margin: 0 auto;
	}
	</style>
	
</head>
<body>
<div class="container">
            <!-- Codrops top bar -->
           <div class="codrops-top"><div class="clr"></div></div><!--/ Codrops top bar -->
            <header><h1>The analysis of <span>household electrical appliances' states</span></h1></header>
			<div id="content">
			  <div id="charts1" data-highcharts-chart="0"></div>
			  <div id="charts2" data-highcharts-chart="0"></div>
			  <div id="App-container1"><div id="charts3" data-highcharts-chart="0"></div></div>
			  <div id="App-container2">
				  
				  <div id="checkradio">
						<label >
							<input class="checkradio" name="check" type="radio" value="1" checked="checked">1
							<input class="checkradio" name="check" type="radio" value="2" >2
							<input class="checkradio" name="check" type="radio" value="3" >3
							<input class="checkradio" name="check" type="radio" value="4" >4
							<input class="checkradio" name="check" type="radio" value="5" >5
							<input class="checkradio" name="check" type="radio" value="0" >STOP
						</label>
				  </div>
				  
			  	  <div id="setback">学习中</div>
			  	  <div id="openstate"></div>
			  	  
			  </div>
			</div>
			 <!--  --><div id="1" style="display:none" data-highcharts-chart="0"></div><!-- 为什么我引了官方的js上面的两个图就可以同时显示 -->
</div>
</body>
</html>
