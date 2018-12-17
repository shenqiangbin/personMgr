var dataFuc = function() {
    return {
        loading: true,
        menuStyle:{
            dicManagement:false,
            ztcbManagement:false,
            zstpManagement:true
        },
        query: { /*查询条件*/
            customerID:'',
            baseMapName:'',
            sensorName:''
        },
        pageModel: {
            currentPage: 1,
            currentPageIndex: 1,
            totalCount: 1,
            totalPage: 1,
            pageSize:10,
            list: []
        },
        multipleSelection: [],
        customerList: {
            list: [{
                customerID: '',
                customerName: ''
            }]
        }
    }
};
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
var fetchData = function () {
    app.loading = true;
    debugger
    var data = {
        customerID: this.query.customerID,/*单位ID*/
        baseMapName:this.query.baseMapName,
        sensorName:this.query.sensorName,
        currentPage: this.pageModel.currentPage,
        pageSize: this.pageModel.pageSize
    };
    $.ajax({
        data: data,
        type: "POST",
        url: contextRoot+"SensorManager/getSensorList",
        async: false,
        success: function (result) {
            if (result.code == 200) {
                debugger
                app.pageModel = result.data;
            } else {
                alert(result.msg);
            }
        },
        error: function (data) {
        }
    });
    app.loading = false;
};
var handleCurrentChange = function (val) {
    this.pageModel.currentPage = val;
    debugger
    app.fetchData();
};
var handleSizeChange = function handleSizeChange(size) {
    app.pageModel.pageSize = size;
    app.fetchData();
}
//关闭弹窗触发，清空表单
var closeDialog = function(){
    debugger
    app.addForm.customerID = '';
    app.addForm.filePath = '';
    app.addForm.baseMapName = '';
    app.fileList =[];
}
//新增底图
var addBaseMap = function () {
    debugger
    if(app.addForm.customerID==""){
        app.$alert("请选择单位名称！", '错误', {
            confirmButtonText: '确定'
        });
        return false;
    }
    if(app.addForm.baseMapName==""){
        app.$alert("请填写底图名称！", '错误', {
            confirmButtonText: '确定'
        });
        return false;
    }
    if(app.addForm.filePath==""){
        app.$alert("请上传底图图片！", '错误', {
            confirmButtonText: '确定'
        });
        return false;
    }
    $.ajax({
        data: {
            customerID: app.addForm.customerID,
            filePath: app.addForm.filePath,
            baseMapName: app.addForm.baseMapName
        },
        type: "POST",
        url: contextRoot+"BaseMap/addBaseMap",
        async: false,
        success: function (result) {
            if (result.code == 200) {
                app.$message({
                    message:result.msg,
                    type: 'success'
                });
                app.dialogFormVisible = false;
                app.fetchData();
            }else if(result.code == 302){
                app.$message.error(result.msg);
            }else if(result.code == 503){
                app.$message.error(result.msg);
            }else if(result.code == 301){
                app.$alert(result.msg, '底图已经存在!', {
                    confirmButtonText: '确定'
                });
            }
            else {
                app.$message.error('服务器错误！');
            }
        },
        error: function (data) {
            app.$message.error('操作失败,请刷新后重试！');
        }
    });
}
var openAddDialog = function () {
    debugger
    app.dialogTitle = '新增';
    app.dialogFormVisible = true;
}
var openEditDialog = function (index, row) {
    debugger
    app.dialogTitle = '编辑';
    app.kgssId = row.kgshipSampleID;//领域关系样本id
    app.source = row.source;
    app.target = row.target;
    app.dialogFormVisible = true;
}

//复选框状态改变
var changeFun = function(val) {
    debugger
    this.multipleSelection = val;
}
/*删除*/
var deleteById = function(index, row){
    debugger
    var e = this;
    e.$confirm('此操作将删除选择的底图(不可恢复), 是否继续?', '三思而后行', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        $.ajax({
            data: {id : row.baseMapID},
            type: "POST",
            url: contextRoot+"BaseMap/deleteBaseMapById",
            async: false,
            success: function (result) {
                if (result.code == 200) {
                    e.$message({
                        message: result.msg,
                        type: 'success'
                    });
                    e.fetchData();
                } else {
                    e.$alert(result.msg, '错误', {
                        confirmButtonText: '确定'
                    });
                }
            },
            error: function (data) {
                e.$alert('删除失败,请刷新后重试！', '错误', {
                    confirmButtonText: '确定'
                });
            }
        });
}).catch(() => {
        e.$message({
        type: 'info',
        message: '已取消删除'
    });
});

}
var toSensorView = function(index, row){
    window.open(contextRoot+"SensorManager/SensorView?baseMapID="+row.baseMapID+"&sensorID="+row.sensorID+"&customerID="+row.baseMap.customerID);
}
/*页面刷新*/
function pagerefresh()
{
    window.location.reload();
}
var init = function () {
    var roleType = $("#roleType").val();
    if(roleType=="User"){
        var customerID = parseInt($("#customerID").val());
        app.query.customerID = customerID;
    }
}
var app = new Vue({
    el: '#app',
    data: dataFuc,
    watch: {
        filterText(val) {
            this.$refs.tree2.filter(val);
        }
    },
    methods: {
        pagerefresh:pagerefresh,
        fetchData: fetchData,
        getCustomerList:getCustomerList,
        handleCurrentChange: handleCurrentChange,
        handleSizeChange:handleSizeChange,
        addBaseMap:addBaseMap,
        openAddDialog:openAddDialog,
        openEditDialog:openEditDialog,
        deleteById:deleteById,
        changeFun:changeFun,
        closeDialog:closeDialog,
        toSensorView:toSensorView,
        init:init
    },
    mounted(){
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        var str= '{ "'+header+'": "'+token+'"}';
        this.headers = eval('(' + str + ')');
    },
})
app.fetchData();
app.getCustomerList();
app.init();
$(".main-hide").scroll(function () {
    /*  alert(1);*/
    $(".top-form").addClass("shadow");
});