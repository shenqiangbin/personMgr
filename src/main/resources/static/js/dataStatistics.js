	var dataFuc = function dataFuc() {
		return {
			exportDataVisible:false,
			visualizationVisible:false,
			isShow:[],
			pageModel: { 
               currentPage: 1,
               totalCount: 1,
               totalPage: 1,
               pagesize:10,
               currentPageIndex: 1,
               list: [{
            	   sensorName:'',
            	   sensorCode:'',
            	   createTime:'',
            	   value1:'',
            	   value2:'',
	          }]
            },
	  	    customerList: {
	  	       list: [{
  	  	    	customerID: '',
  	  	    	customerName: ''
  	  	        }]
	  	    },
            dateValue:'',
            customerID:'',
            flag:0,

		}
	};
	
	var app = new Vue({
		el : '#app',
		data : dataFuc,
		methods : {
			fetchData: fetchData,
			getCustomerList:getCustomerList,
			handleSizeChange:handleSizeChange,
			handleCurrentChange:handleCurrentChange,
			exportData:exportData,
			kshChart:kshChart
			
		},
	})
	
	function fetchData(flag) {
		app.flag = flag;
        var pageModel = {};
        var currentPageIndex = this.pageModel.currentPage;
        var data = {
           currentPage: this.pageModel.currentPage, 
           pageSize: this.pageModel.pagesize,
           dateValueStart: this.dateValue[0],
           dateValueEnd: this.dateValue[1],
           customerID: this.customerID,
           flag:flag
        };
        $.ajax({
           data: data,
           type: "POST",
           url: contextRoot+"dataStatistics/getList",
           async: false,
           success: function (result) {
               if (result.code == 200) {
            	   console.log(result.data);
                   pageModel = result.data;
               }else {
                   alert(result.msg);
               }
           },
           error: function (data) {
           }
        });
        this.pageModel = pageModel;
        this.pageModel.currentPageIndex = currentPageIndex;
        this.pageModel.currentPage = 1;
		this.pageModel.pagesize = 10;
	}
	
	app.fetchData(0);
	
	function getCustomerList(){
		var customerList = {};
		$.ajax({
	           type: "GET",
	           url: contextRoot+"dataManager/getCustomerList",
	           async: false,
	           success: function (result) {
	               if (result.code == 200) {
	            	   console.log(result.data);
	            	   customerList = result.data;
	               }else {
	                   alert(result.msg);
	               }
	           },
	           error: function (data) {
	           }
	        });
		this.customerList = customerList;
	}
	app.getCustomerList();
	
	function handleSizeChange(size) {
        this.pageModel.pagesize = size;
     }
	
	function handleCurrentChange(val) {
	       this.pageModel.currentPage = val;
	       app.fetchData(0);
	}
	
	function exportData(){
		var data = {
		   flag:app.flag,
           dateValueStart: this.dateValue[0],
           dateValueEnd: this.dateValue[1]
        };
		$.ajax({
	           type: "POST",
	           data: data,
	           url: contextRoot+"dataStatistics/excel",
	           async: false,
	           success: function (result) {
	               if (result.code == 200) {
	            	   showMsg("导出成功！");
	               }else {
	                   alert(result.msg);
	               }
	           },
	           error: function (data) {
	           }
	        });
		this.dateValue = "";
		this.exportDataVisible = false;
	}
	
	function kshChart(){
		this.visualizationVisible = true;
		var ajaxdata = {flag:app.flag};
		Vue.nextTick(function(){
		   var dom = document.getElementById("kshChart");
		   var myChart = echarts.init(dom);
		   var app = {};
		   option = null;
		   option = {
			        title: {
			            text: '',
			            top:10,
			            left:10
			        },
			        tooltip : {
			            trigger: 'item',
			            formatter: "{a} <br/>{b} : {c}次"
			         },
			        toolbox: {
			            show : true,
			            top:10,
			            right:10,
			            feature : {
			                mark : {show: true},
			                magicType : {show: true, type: ['line', 'bar']},
			                restore : {show: true},
			                saveAsImage : {show: true}
			            }
			        },
			        grid:{
			            top:60,
			            right:70,
			            bottom:30,
			            left:60
			        },
			        legend: {
			            top:32,
			            left:'center',
			            data:['开合度(mm)','温度(℃)']
			        },
			        calculable : true,
			        xAxis : [
			            {
			                type : 'category',
			                data : []
			            }
			        ],
			        yAxis : [
			            {
			                type: 'value',
			                name:"开\n合\n度\n︵\nmm\n︶",
			                nameLocation:"center",
			                nameGap:35,
			                nameRotate:0,
			                nameTextStyle:{
			                    fontSize: 14,
			                },
			                //默认以千分位显示，不想用的可以在这加一段
			                axisLabel : {   //调整左侧Y轴刻度， 直接按对应数据显示
			                    show:true,
			                    showMinLabel:true,
			                    showMaxLabel:true,
			                    formatter: function (value) {
			                        return value;
			                    }
			                }
			            },
			            {
			                type: 'value',
			                name:"温\n度\n︵\n℃\n︶",
			                nameLocation:"center",
			                nameGap:35,
			                nameRotate:0,
			                nameTextStyle:{
			                    fontSize: 14,
			                },
			                //默认以千分位显示，不想用的可以在这加一段
			                axisLabel : {   //调整左侧Y轴刻度， 直接按对应数据显示
			                    show:true,
			                    showMinLabel:true,
			                    showMaxLabel:true,
			                    formatter: function (value) {
			                        return value;
			                    }
			                }
			            }
			        ],
			        series : [
			            {
			                name:'开合度(mm)',
			                type:'line',
			                smooth: true,
			                yAxisIndex: 0,
			                data:[],
			                itemStyle : { normal: {label : {show: true}}},
			            },
			            {
			                name:'温度(℃)',
			                type:'line',
			                smooth: true,
			                yAxisIndex: 1,
			                data:[],
			                itemStyle : { normal: {label : {show: true}}},
			            }
			        ]
			    };
		   $.ajax({
			    type: "post",
			    data: ajaxdata,
				async:false,//重要的关健点在于同步和异步的参数，让ajax请求执行完后再往下执行
			    url: contextRoot+'dataStatistics/kshData',
			    dataType: "json",
			    success: function (result) {
			    	 if (result.code == 200) {
			    		 var dataObj = result.data.list;
			    		 for (var i=0;i<dataObj.length;i++){
			    			 var recordStartDate = dataObj[i].recordStartDate;
			    			 var value1 = dataObj[i].value1;
			    			 var value2 = dataObj[i].value2;
			    			 option["xAxis"][0].data.push(recordStartDate);
			    			 option["series"][0].data.push(value1);
			    			 option["series"][1].data.push(value2);
			    		 }
			           }
			    	 else {
			        	   this.$message.error('服务器错误！');
			           }
			    }
			});
			
			if (option && typeof option === "object") {
			    myChart.setOption(option, true);
			}
		})
	}
	
