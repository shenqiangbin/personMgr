
var restart = function restart(){
    var e = this;
    var ids = [];
    var dicIdsStr;
    if(app.multipleSelection.length>0){
        for(var i = 0;i<app.multipleSelection.length;i++){
            console.log(i);
            console.log(app.multipleSelection[i].sensorDataID);
            ids.push(app.multipleSelection[i].sensorDataID);
        }
        dicIdsStr = ids.join(",");
    }else{
        e.$alert('请选择要重置的信息!', '提示', {
            confirmButtonText: '确定',
            confirmButtonClass: 'btn-line cur',
        });
        return false;
    }
    e.$confirm('此操作将解除全部选中警告, 是否继续?', '提示',{
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        cancelButtonClass: 'btn-line',
        confirmButtonClass: 'btn-line cur',
    }).then(() => {
        var ajaxdata = {customerIds:dicIdsStr};
    $.ajax({
        data: ajaxdata,
        type: "POST",
        url: contextRoot+"monitorManager/removeAllAlerts",
        async: false,
        success: function (result) {
            if (result.code == 200) {
                console.log("haha, SUC!")
                app.fetchData(0);
            }else{
                _this.$message({
                    showClose: true,
                    message: result.msg,
                    type: 'warning'
                });
            }
        },
        error: function (data) {
            e.$alert('重置失败,请刷新后重试！', '错误', {
                confirmButtonText: '确定',
                confirmButtonClass: 'btn-line cur',
            });
        }
    });
}).catch(() => {
        this.$message({
            type: 'info',
            message: '已取消重置'
        });
});

}

var dataFuc = function dataFuc() {
    return {
        dialogAddVisible:false,
        isCfgUse:true,
        isShow:[],
        multipleSelection:[],
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
                dataType:'',
            }]
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

        states: [{
            value: '',
            label: '全部'
        }, {
            value: 1,
            label: '正常'
        }, {
            value: 2,
            label: '异常'
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
        sensorDataID:'',

        alertStatus:'',

        isTmpAlert:true,
        isOpnAlert:true,
        isNulAlert:true,
        tmpUp:'',
        tmpDown:'',
        opnUp:'',
        opnDown:'',


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
        editSensorData:editSensorData,
        saveSensorData:saveSensorData,
        reset:reset,
        removeAlert:removeAlert,
        updateAlrtCfg:updateAlrtCfg,
        changeFun:changeFun,
        restart:restart,

    },
})

// function tableRowClassName({row, rowIndex}) {
//     if (rowIndex === 1) {
//         return 'warning-row';
//     }
// }
function removeAlert(p) {
    console.log(this);
    var pageModel = {};
    var currentPageIndex = this.pageModel.currentPage;
    var data = {
        currentPage: this.pageModel.currentPage,
        pageSize: this.pageModel.pagesize,
        dataType: this.dataType,
        dateValueStart: this.dateValue[0],
        dateValueEnd: this.dateValue[1],
        customerID: this.customerID,
        selectName: this.selectName,
        selectValue: this.selectValue,
        recordStartDate: this.recordStartDate,
        recordEndDate: this.recordEndDate,
        customerName: this.customerName,
        createTime: this.createTime,
        alertStatus: this.alertStatus,
        sensorDataID: p
    };
    $.ajax({
        data: data,
        type: "POST",
        url: contextRoot+"monitorManager/removeAlert",
        async: false,
        success: function (result) {
            if (result.code == 200) {
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

// var changeFun = function changeFun(val){
//     this.multipleSelection = val;
// }




function updateAlrtCfg() {
    var data = {
        isTmpAlert:this.isTmpAlert,
        isOpnAlert:this.isOpnAlert,
        isNulAlert:this.isNulAlert,
        tmpUp:this.tmpUp,
        tmpDown:this.tmpDown,
        opnUp:this.opnUp,
        opnDown:this.opnDown
    };
    $.ajax({
        data: data,
        type: "POST",
        url: contextRoot+"monitorManager/updateAlrtCfg",
        async: false,
        success: function (result) {
            if (result.code == 200) {
                // console.log(result.data)
            }else {
                alert(result.msg);
            }
        },
        error: function (data) {
        }
    });
    this.dialogAddVisible = false;
}

function fetchData(e) {
    var pageModel = {};
    var currentPageIndex = this.pageModel.currentPage;
    var data = {
        currentPage: this.pageModel.currentPage,
        pageSize: this.pageModel.pagesize,
        dataType: this.dataType,
        dateValueStart: this.dateValue[0],
        dateValueEnd: this.dateValue[1],
        customerID: this.customerID,
        selectName: this.selectName,
        selectValue: this.selectValue,
        recordStartDate: this.recordStartDate,
        recordEndDate: this.recordEndDate,
        customerName: this.customerName,
        createTime: this.createTime,
        alertStatus: this.alertStatus
    };
    $.ajax({
        data: data,
        type: "POST",
        url: contextRoot+"monitorManager/getList",
        async: false,
        success: function (result) {
            if (result.code == 200) {
                // console.log(result.data)
                pageModel = result.data;
                if (result.msg == "user") {
                    app.isCfgUse = false;
                }
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

    var cid = GetQueryString("cid");
    if(cid)
        app.customerID = parseInt(cid);
}
app.getCustomerList();

app.fetchData(0);

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
    app.fetchData();
}

function addSensorData(){
    var sensorID = this.addSensorName;
    var dateValueStart = this.addDate[0];
    var dateValueEnd = this.addDate[1];
    var value1 = this.addValue1;
    var value2 = this.addValue2;
    var dataType = this.addDataType;
    var data = {
        sensorID: sensorID,
        dateValueStart: dateValueStart,
        dateValueEnd: dateValueEnd,
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
    app.fetchData();
    this.addSensorName = "";
    this.addDate = "";
    this.addValue1 = "";
    this.addValue2 = "";
    this.addDataType = 0;
    this.dialogAddVisible = false;
}

// function deleteSensorData(index, row){
//     console.log(row)
//     var e = this;
//     e.$confirm('此操作将永久删除该记录, 是否继续?', '提示',{
//         confirmButtonText: '确定',
//         cancelButtonText: '取消',
//         cancelButtonClass: 'btn-line',
//         confirmButtonClass: 'btn-line cur',
//     }).then(() => {
//         var sensorDataID = row.sensorDataID;
//     var data = {
//         sensorDataID: sensorDataID
//     };
//     $.ajax({
//         data: data,
//         type: "GET",
//         url: contextRoot+"dataManager/deleteSensorData",
//         async: false,
//         success: function (result) {
//             if (result.code == 200) {
//                 e.$message({
//                     message:result.msg,
//                     type: 'success'
//                 });
//
//             }else {
//                 e.$message.error(result.msg)
//             }
//         },
//     });
//     //删除数据成功后，重新查询一次数据
//     app.fetchData();
// }).catch(() => {
//         this.$message({
//             type: 'info',
//             message: '已取消删除'
//         });
// });
// }

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
    this.alertStatus = "";
}

function changeFun(val){
    // alert(1)
    app.multipleSelection = val;
}