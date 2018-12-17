	var dataFuc = function dataFuc() {
		return {
			dialogAddVisible:false,
			centerDialogVisible:false,
			templateDialogVisible:false,
            templateCustomerId:"",
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
            	   recordStartDate:'',
            	   value1:'',
            	   value2:'',
            	   dataType:'',
	          }]
            },
            query: { /*查询条件*/
                baseMapID: '',
                sensorID:'',
            },
            options: [{
  	          value: '',
  	          label: '全部'
  	        }, {
  	          value: 0,
  	          label: '原始数据'
  	        }, {
  	          value: 1,
  	          label: '整编数据'
  	        }],
  	      options2: [{
  	          value: '',
  	          label: '全部'
  	        }, {
  	          value: 0,
  	          label: '测点'
  	        }, {
  	          value: 1,
  	          label: '代号'
  	        }, {
	          value: 2,
  	          label: '开合度'
  	        }, {
  	          value: 3,
  	          label: '温度'
  	        }],
	  	    customerList: {
	  	       list: [{
  	  	    	customerID: '',
  	  	    	customerName: ''
  	  	        }]
	  	    },
	  	  sensorList: {
	  		  list: [{
	  			sensorID: '',
	  			sensorName: '',
	  			sensorCode: ''
  	  	        }]
	  	    },
            dataType:'',
            dateValue:'',
            customerID:'',
            selectName:'',
            selectValue:'',
            addSensorName:'',
            addDate:'',
            addValue1:'',
            addValue2:'',
            addDataType:0,
            val:1
            

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
			getSensorList:getSensorList,
			addSensorData:addSensorData,
			deleteSensorData:deleteSensorData,
			editSensorData:editSensorData,
			saveSensorData:saveSensorData,
			reset:reset,
            init:init,
            uploadData:uploadData,
            downloadTemplate:downloadTemplate,
			
		},
	})
	
	function fetchData() {
        var pageModel = {};
        var currentPageIndex = this.pageModel.currentPage;
        var data = {
           currentPage: this.pageModel.currentPage, 
           pageSize: this.pageModel.pagesize,
           dataType: this.dataType,
           dateValueStart: this.dateValue[0],
           dateValueEnd: this.dateValue[1],
           customerID: this.customerID,
			baseMapID:this.query.baseMapID,
			sensorID:this.query.sensorID,
           selectName: this.selectName,
           selectValue: this.selectValue
        };
        $.ajax({
           data: data,
           type: "POST",
           url: contextRoot+"dataManager/getList",
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
    function init() {
        debugger
        if($("#customerID").val()!=''){
            app.query.customerID = $("#customerID").val();
        }
        if($("#customerID").val()!=''){
            app.customerID = parseInt($("#customerID").val());
        }
        if($("#sensorID").val()!=''){
            app.query.sensorID = $("#sensorID").val();
        }
    }
    app.init();

    app.fetchData();

	function getSensorList(){
		var sensorList = {};
		$.ajax({
	           type: "GET",
	           url: contextRoot+"dataManager/getSensorList",
	           async: false,
	           success: function (result) {
	               if (result.code == 200) {
	            	   console.log(result.data);
	            	   sensorList = result.data;
	               }else {
	                   alert(result.msg);
	               }
	           },
	           error: function (data) {
	           }
	        });
		this.sensorList = sensorList;
	}
	app.getSensorList();
	
	/*function choose(){
		for (var i = 0; i<this.sensorList.list.length;i++){
			 if (this.sensorList.list[i].sensorID == this.addSensorName){
				 app.sensorCode = this.sensorList.list[i].sensorCode;
			 }
		}
	}*/
	
	function handleSizeChange(size) {
        this.pageModel.pagesize = size;
     }
	
	function handleCurrentChange(val) {
	       this.pageModel.currentPage = val;
	       app.val = val;
	       app.fetchData();
	}
	
	function addSensorData(){
		if('' == this.addSensorName){
			  app.$alert('测点不能为空', '提示', {
	 	          	confirmButtonText: '确定',
	 	          	confirmButtonClass: 'btn-line cur',
	 	          });
			 return false;
		}
		if('' == this.addDate){
			  app.$alert('时间不能为空', '提示', {
	 	          	confirmButtonText: '确定',
	 	          	confirmButtonClass: 'btn-line cur',
	 	          });
			 return false;
		}
		var sensorID = this.addSensorName;
		var dateValueStart = this.addDate;
		var value1 = this.addValue1;
		var value2 = this.addValue2;
		var dataType = this.addDataType;
		var data = {
			sensorID: sensorID,
			dateValueStart: dateValueStart,
			value1: value1,
			value2: value2,
			dataType: dataType
  		};
		$.ajax({
				data : data,
				type : "POST",
				url: contextRoot+"dataManager/addSensorData",
				async : false,
				success : function(result) {
					if (result.code == 200) {
						app.$message({
	            	          message:result.msg,
	            	          type: 'success'
	            	        });
					}else {
						app.$message.error(result.msg)
					}
				}
		});
		app.pageModel.currentPage = app.val;
		app.fetchData();
		this.addSensorName = "";
		this.addDate = "";
		this.addValue1 = "";
		this.addValue2 = "";
		this.addDataType = 0;
		this.dialogAddVisible = false;
	}
	
	function deleteSensorData(index, row){
		console.log(row)
		var e = this;
	    app.$confirm('此操作将永久删除该记录, 是否继续?', '提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        cancelButtonClass: 'btn-line',
        confirmButtonClass: 'btn-line cur',
	    }).then(() => {
	        	var sensorDataID = row.sensorDataID;
	    		var data = { 
	    				sensorDataID: sensorDataID
	    				};
	    		$.ajax({
	    	           data: data,
	    	           type: "GET",
	    	           url: contextRoot+"dataManager/deleteSensorData",
	    	           async: false,
	    	           success: function (result) {
	    	               if (result.code == 200) {
	    	            	   e.$message({
	    	            	          message:result.msg,
	    	            	          type: 'success'
	    	            	        });
	    	            	   
	    					}else {
	    						e.$message.error(result.msg)
	    					}
	    	           },
	    	       });
	    		//删除数据成功后，重新查询一次数据
	    		app.pageModel.currentPage = app.val;
	    		app.fetchData();
	        }).catch(() => {
	          this.$message({
	            type: 'info',
	            message: '已取消删除'
	          });          
	        });
	}
	
	function editSensorData(index,row){
		app.isShow.pop(app.isShow[index]);
    	app.isShow[index]=true;
	}
	
	function saveSensorData(index,row){
		var sensorDataID = row.sensorDataID;
		var sensorID = row.sensorID;
		var value1 = row.value1;
		var value2 = row.value2;
		var data = {
			sensorDataID: sensorDataID,
			sensorID: sensorID,
			value1:value1,
			value2:value2
		};
		$.ajax({
			data : data,
			type : "POST",
			url: contextRoot+"dataManager/updateSensorData",
			async : false,
			success : function(result) {
				if (result.code == 200) {
					app.$message({
            	          message:result.msg,
            	          type: 'success'
            	        });
				}else if(result.code == 201){
				app.$message.error({
          	          message:result.msg,
        	          type: 'fail'
        	        });
				}else {
				app.$message.error(result.msg)
				}
			}
		});
		app.isShow.pop(app.isShow[index]);
		app.fetchData();
	}
	
	function reset(){
		this.customerID = "";
		this.dataType = "";
		this.selectName = "";
		this.selectValue = "";
		this.dateValue = "";
	}
	
	function uploadData(){
		var form = document.getElementById("fileUpload");
		var postData = new FormData(form);
		var file = document.getElementById("file");
		$.ajax({
			data : postData,
			type : "POST",
			url : contextRoot + 'dataManager/uploadData',
			contentType: false,             
	        processData: false,
			success : function(result) {
				if (result.code == 200) {
					app.$message({
            	          message:result.msg,
            	          type: 'success'
            	        });
					app.fetchData();
				}else if(result.code == 403){
					app.$message.error({
          	          message:result.msg,
        	          type: 'fail'
        	        });
				}else{
					app.$message.error({
	          	          message:result.msg,
	        	          type: 'fail'
	        	        });
				}
			}
		});
		this.centerDialogVisible = false;
		//上传完成后清空文件
		this.$refs.upload.clearFiles();
	}

	function  downloadTemplate() {
		if(!app.templateCustomerId){
			showError("请选择客户");
			return;
		}
		var url = contextRoot + "dataStatistics/excelTemplate?cid="+app.templateCustomerId;
        window.open(url,'_blank');
        this.templateDialogVisible = false;
    }
	
