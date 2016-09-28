$(function () {

    $(document).ready(function() { 
    	var $selected = $("input[name='check']:checked").val();
        Highcharts.setOptions({                                                     
            global: {                                                               
                useUTC: false                                                       
            }                                                                       
        });                                                                         

        var chart1;  
        chart1 = new Highcharts.Chart({                                               
            chart: { 
            	renderTo: 'charts1', 
                type: 'spline',                                                     
                animation: Highcharts.svg, // don't animate in old IE               
                marginRight: 10,                                                    
                events: {                                                           
                    load: function() {                                              
                                                                                    
                        // set up the updating of the chart each second             
                        var series = this.series[0];                                
                        setInterval(function() {                                    
                            var x = (new Date()).getTime(), // current time         
                                y = reload();//Math.random();                                  
                            series.addPoint([x, y], true, true);                    
                        }, 1000);                                                   
                    }                                                               
                }                                                                   
            },                                                                      
            title: {                                                                
                text: ' 选定用电器视在功率变化 ' ,
                style:{
                	  color: '#808080',
                	  fontSize: '18px',
                	  fontFamily:'微软雅黑',
                	  //fontWeight:'bold'
                	}
            },                                                                      
            xAxis: {
            	title: {                                                            
                    text: 'Time (h:m:s)' ,
                    style:{
                  	  color: '#808080',
                  	  fontFamily:'微软雅黑',
                  	  //fontWeight:'bold'
                  	}
                },
                type: 'datetime',                                                   
                tickPixelInterval: 100                                              
            },                                                                      
            yAxis: {                                                                
                title: {                                                            
                    text: 'Apparent Power' ,
                    style:{
                  	  color: '#808080',
                  	  fontFamily:'微软雅黑',
                  	  //fontWeight:'bold'
                  	}  
                }, 
                tickPixelInterval: 50,
                plotLines: [{                                                       
                    value: 0,                                                       
                    width: 1,                                                       
                    color: '#808080'                                                
                }]                                                                  
            },                                                                      
            tooltip: {                                                              
                formatter: function() {                                             
                        return '<b>'+ '主线路电能' +'</b><br/>'+                
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                        Highcharts.numberFormat(this.y, 2);                         
                }                                                                   
            },                                                                      
            legend: {                                                               
                enabled: false                                                      
            },                                                                      
            exporting: {                                                            
                enabled: false                                                      
            },                                                                      
            series: create()                                                                    
        });  
       /// 
        
        
        
        var chart2;  
        chart2 = new Highcharts.Chart({                                               
            chart: { 
            	renderTo: 'charts2',                                                                
                type: 'spline',                                                     
                animation: Highcharts.svg, // don't animate in old IE               
                marginRight: 10,                                                    
                events: {                                                           
                    load: function() {                                              
                                                                                    
                        // set up the updating of the chart each second             
                        var series = this.series[0];                                
                        setInterval(function() {                                    
                            var x = (new Date()).getTime(), // current time         
                                y = reloaddiff();//Math.random();                                  
                            series.addPoint([x, y], true, true);                    
                        }, 1000);                                                   
                    }                                                               
                }                                                                   
            },                                                                      
            title: {                                                                
                text: ' 主线路电能差分变化 ' ,
                style:{
                	  color: '#808080',
                	  fontSize: '18px',
                	  fontFamily:'微软雅黑',
                	  //fontWeight:'bold'
                	}
            },                                                                      
            xAxis: {
            	title: {                                                            
                    text: 'Time (h:m:s)' ,
                    style:{
                  	  color: '#808080',
                  	  fontFamily:'微软雅黑',
                  	  //fontWeight:'bold'
                  	}
                },
                type: 'datetime',                                                   
                tickPixelInterval: 100                                              
            },                                                                      
            yAxis: {                                                                
                title: {                                                            
                    text: 'difference' ,
                    style:{
                  	  color: '#808080',
                  	  fontFamily:'微软雅黑',
                  	  //fontWeight:'bold'
                  	}  
                }, 
                tickPixelInterval: 50,
                plotLines: [{                                                       
                    value: 0,                                                       
                    width: 1,                                                       
                    color: '#808080'                                                
                }]                                                                  
            },                                                                      
            tooltip: {                                                              
                formatter: function() {                                             
                        return '<b>'+ '主线路电能差分' +'</b><br/>'+                
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                        Highcharts.numberFormat(this.y, 2);                         
                }                                                                   
            },                                                                      
            legend: {                                                               
                enabled: false                                                      
            },                                                                      
            exporting: {                                                            
                enabled: false                                                      
            },                                                                      
            series: creatediff()                                                                    
        }); 
        
      //////
        
        
        var chart3;  
        chart3 = new Highcharts.Chart({                                               
            chart: { 
            	renderTo: 'charts3',                                                                
                type: 'spline',                                                     
                animation: Highcharts.svg, // don't animate in old IE               
                marginRight: 10,                                                    
                events: {                                                           
                    load: function() {                                              
                                                                                    
                        // set up the updating of the chart each second             
                        var series = this.series[0];                                
                        setInterval(function() {                                    
                            var x = (new Date()).getTime(), // current time         
                                y = reloadApp();//Math.random();                                  
                            series.addPoint([x, y], true, true);                    
                        }, 1000);                                                   
                    }                                                               
                }                                                                   
            },                                                                      
            title: {                                                                
                text: ' 某用电器视在功率变化 ' ,
                style:{
                	  color: '#808080',
                	  fontSize: '18px',
                	  fontFamily:'微软雅黑',
                	  //fontWeight:'bold'
                	}
            },                                                                      
            xAxis: {
            	title: {                                                            
                    text: 'Time (h:m:s)' ,
                    style:{
                  	  color: '#808080',
                  	  fontFamily:'微软雅黑',
                  	  //fontWeight:'bold'
                  	}
                },
                type: 'datetime',                                                   
                tickPixelInterval: 100                                              
            },                                                                      
            yAxis: {                                                                
                title: {                                                            
                    text: 'Apparent Power' ,
                    style:{
                  	  color: '#808080',
                  	  fontFamily:'微软雅黑',
                  	  //fontWeight:'bold'
                  	}  
                }, 
                tickPixelInterval: 50,
                plotLines: [{                                                       
                    value: 0,                                                       
                    width: 1,                                                       
                    color: '#808080'                                                
                }]                                                                  
            },                                                                      
            tooltip: {                                                              
                formatter: function() {                                             
                        return '<b>'+ '用电器'+'</b><br/>'+                
                        Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) +'<br/>'+
                        Highcharts.numberFormat(this.y, 2);                         
                }                                                                   
            },                                                                      
            legend: {                                                               
                enabled: false                                                      
            },                                                                      
            exporting: {                                                            
                enabled: false                                                      
            },                                                                      
            series: createApp()                                                                    
        }); 
        
        
        /////
      
        
        
    });                                                                                 
});      

function create() {
    var series = new Array();
    
    $.ajax({
      type: "POST",
      url: "addData.do",
      async: false, //表示同步，如果要得到ajax处理完后台数据后的返回值，最好这样设置
      cache: false, 
      dataType: 'json',   //返回值类型 
      success: function(result){
        var size = result.length;
        for(var i=0; i<size; i++) {
        	
          var name = result[i].name;
          var data = function() {
            var data = [],
              time = (new Date()).getTime(),
              j;
           
            for(j=-15; j<=0; j++) {
              data.push({
                x: (new Date()).getTime()+ j * 1000,
                y: result[i].valueY
              });
            }
            
            return data;
          }();
          series.push({"name": name, "data": data});
        }
      }
    }, false);  //false表示“遮罩”，前台不显示“请稍后”进度提示
        return series;
    
  }
function reload() {
    var value;
    
    $.ajax({
      type: "POST",
      url: "updateData.do",
      async: false, //表示同步，如果要得到ajax处理完后台数据后的返回值，最好这样设置
      cache: false, 
      dataType: 'json',   //返回值类型 
      success: function(result){
          var size = result.length;
          for(var i=0; i<size; i++) {	
            value = result[i].valueY;
          }
        }
      }, false);  //false表示“遮罩”，前台不显示“请稍后”进度提示
        //return value;
	return value;
    
  }



function creatediff() {
    var series = new Array();
    
    $.ajax({
      type: "POST",
      url: "addData.do",
      async: false, //表示同步，如果要得到ajax处理完后台数据后的返回值，最好这样设置
      cache: false, 
      dataType: 'json',   //返回值类型 
      success: function(result){
        var size = result.length;
        for(var i=0; i<size; i++) {
        	
          var name = result[i].name;
          var data = function() {
            var data = [],
              time = (new Date()).getTime(),
              j;
           
            for(j=-15; j<=0; j++) {
              data.push({
                x: (new Date()).getTime()+ j * 1000,
                y: result[i].valueY
              });
            }
            
            return data;
          }();
          series.push({"name": name, "data": data});
        }
      }
    }, false);  //false表示“遮罩”，前台不显示“请稍后”进度提示
        return series;
    
  }


function reloaddiff() {
    var value;
    
    $.ajax({
      type: "POST",
      url: "updateDiff.do",
      async: false, //表示同步，如果要得到ajax处理完后台数据后的返回值，最好这样设置
      cache: false, 
      dataType: 'json',   //返回值类型 
      success: function(result){
          var size = result.length;
          for(var i=0; i<size; i++) {
          	
            value = result[i].valueY;
          }
        }
      }, false);  //false表示“遮罩”，前台不显示“请稍后”进度提示
        //return value;
	return value;
    
  }


function createApp() {
    var series = new Array();
    $.ajax({
      type: "POST",
      url: "addData.do",
      async: false, //表示同步，如果要得到ajax处理完后台数据后的返回值，最好这样设置
      cache: false, 
      dataType: 'json',   //返回值类型 
      success: function(result){
        var size = result.length;
        for(var i=0; i<size; i++) {
          var name = result[i].name;
          var data = function() {
            var data = [],
              time = (new Date()).getTime(),
              j;
           
            for(j=-15; j<=0; j++) {
              data.push({
                x: (new Date()).getTime()+ j * 1000,
                y: result[i].valueY
              });
            }
            
            return data;
          }();
          series.push({"name": name, "data": data});
        }
      }
    }, false); 

        return series;
    
  }


function reloadApp() {
    var value;
    var state;
    var open;
    $.ajax({
      type: "POST",
      url: "updateApp.do",
      async: false,
      cache: false, 
      dataType: 'json',   
      success: function(result){
          var size = result.length;
          for(var i=0; i<size; i++) {
        	state=result[i].valueX;
            value=result[i].valueY;
            open=result[i].valueZ;
          }
        }
      }, false);  
    var a=document.getElementById("setback").innerHTML;
    if(state=="study")
    {
    	if(a=="学习中")
    	{
    		document.getElementById("setback").innerHTML="学习中·";
    	}
    	else if(a=="学习中·")
    	{
    		document.getElementById("setback").innerHTML="学习中··";
    	}
    	else if(a=="学习中··")
    	{
    		document.getElementById("setback").innerHTML="学习中···";
    	}
    	else{document.getElementById("setback").innerHTML="学习中";}
    }
    else
    {
    	
    	if(a=="挖掘中")
    	{
    		document.getElementById("setback").innerHTML="挖掘中·";
    	}
    	else if(a=="挖掘中·")
    	{
    		document.getElementById("setback").innerHTML="挖掘中··";
    	}
    	else if(a=="挖掘中··")
    	{
    		document.getElementById("setback").innerHTML="挖掘中···";
    	}
    	else
    	{
    		document.getElementById("setback").innerHTML="挖掘中";
    	}
    }
    if(open=="1")
    {
    	document.getElementById("openstate").innerHTML="--ON ACTION--";
    }
    else if(open=="-1")
    {
    	document.getElementById("openstate").innerHTML="--OFF ACTION--";
    }
    else if (open="0")
    {
    	document.getElementById("openstate").innerHTML="";
    }
    //
	return value;
    
  }