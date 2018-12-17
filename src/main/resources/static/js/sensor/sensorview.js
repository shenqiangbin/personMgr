var dataFuc = function() {
    return {
        loading: true,
        model:{
            baseMapID:'',
            baseMapName:'',
            filePath:'',
        },
        menuStyle:{
            dicManagement:false,
            ztcbManagement:false,
            zstpManagement:true
        },
        query: { /*查询条件*/
            customerID:'',
            activeBaseMap: '',
            activeSensor:'',
        },
        baseMaps: [],
    //     [{
    //         title: 'Tab 1',
    //         name: '15'
    //     }, {
    //     title: 'Tab 2',
    //         name: '2'
    // }]
        sensors: [],//[{sensorCode:'SIN1-1',sensorName:'测点1'},{sensorCode:'SIN1-2',sensorName:'测点2'}]
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
function getBaseMaps(){
    $.ajax({
        type: "Post",
        data:{customerID:app.query.customerID},
        url: contextRoot+"BaseMap/getByCustomerId",
        async: false,
        success: function (result) {
            app.baseMaps = result;
        },
        error: function (data) {
        }
    });
}
var currentMarkEle = null;
function addMarkEle(){
    var str =
        "<div class='markEle' style='position: absolute;left: 0;top: 0;border:0px solid red;' onclick='setCurEle(this)'>" +
        "   <i class='bz bz1'></i><span class='dotName' ></span>" +
        "</div>";
    var newEle = $(str);
    $("#pane-"+app.query.activeBaseMap+" #box").append(newEle);
    currentMarkEle = newEle;
}
function getSensors(){
    $.ajax({
        type: "GET",
        data:{id:parseInt(app.query.activeBaseMap)},
        url: contextRoot+"SensorManager/GetByBaseMapId",
        async: false,
        success: function (result) {
            debugger
            app.sensors = result.data;
            setTimeout(function () {
                var zoom = 1;
                for(var i=0; i<result.data.length; i++){
                    var dotItem = result.data[i];
                    debugger
                    addMarkEle();
                    currentMarkEle.animate({left:dotItem.coordinateX+'px',top:dotItem.coordinateY+'px'},10);
                    currentMarkEle.attr("top",dotItem.coordinateY);
                    currentMarkEle.attr("left",dotItem.coordinateX);
                    currentMarkEle.attr("zoom",dotItem.zoom);
                    currentMarkEle.attr("baseMapID",dotItem.baseMapID);
                    currentMarkEle.attr("sensorID",dotItem.sensorID);
                    currentMarkEle.find(".dotName").html(dotItem.sensorName);
                    zoom = dotItem.zoom;
                }
                ele.height(height * zoom);
                ele.width(width * zoom);
            },1000);
        },
        error: function (data) {
        }
    });
}
function setCurEle(obj){
    var baseMapID = $(obj).attr('baseMapID');
    var sensorID = $(obj).attr('sensorID');
    app.query.activeSensor = sensorID;
    window.open(contextRoot+"dataManager/index?baseMapID="+baseMapID+"&sensorID="+sensorID+"&customerID="+app.query.customerID);
}
function tabClick(activeTab){
    debugger
    app.query.activeBaseMap = activeTab.name;
    getData();
    getSensors();
}

var fetchData = function () {
    getBaseMaps();
    if(app.baseMaps.length>0){
        app.query.activeBaseMap = app.baseMaps[0].baseMapIDStr;
    }
    getData();
    getSensors();
};
var clickSensor = function(sensor){
    debugger
    app.query.activeSensor = sensor.sensorID;
    window.open(contextRoot+"dataManager/index?baseMapID="+sensor.baseMapID+"&sensorID="+sensor.sensorID+"&customerID="+app.query.customerID);
}
var init = function () {
    debugger
    app.query.activeBaseMap = $("#baseMapID").val();
    app.query.customerID = parseInt($("#customerID").val());
    app.query.activeSensor = $("#sensorID").val();
}
var ele = $("#imgEle");
var height = 0;
var width = 0;
function getData() {
    var id = app.query.activeBaseMap;
    $.ajax({
        type: "get",
        url: contextRoot + "BaseMap/GetById",
        data: { id:id },
        async: true,
        success: function (result) {
            console.log(result);
            if (result.code == 200) {
                app.model = result.data;
                setTimeout(function () {
                    height = ele.height();
                    width = ele.width();
                },500);
            }else {
                showError(result.msg);
            }
        },
        error: function () {
            showMsg("图片信息获取失败");
        }
    });


}
var app = new Vue({
    el: '#app',
    data: dataFuc,
    methods: {
        fetchData: fetchData,
        getCustomerList:getCustomerList,
        init:init,
        getBaseMaps:getBaseMaps,
        getSensors:getSensors,
        tabClick:tabClick,
        clickSensor:clickSensor,
        getData:getData
    }
})
app.getCustomerList();
app.init();
app.getData();
app.getBaseMaps();
app.getSensors();
$(".main-hide").scroll(function () {
    /*  alert(1);*/
    $(".top-form").addClass("shadow");
});