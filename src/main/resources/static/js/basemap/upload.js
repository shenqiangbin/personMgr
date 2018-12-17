var beforeUpload = function(file){
    debugger
    checkFileType(file);
    checkFileSize(file);
}
var onSuccess = function(response,file,fileList){
    debugger
    if(response.msg=="ok"){
        debugger
        app.addForm.filePath = response.data;
        //app.fileList = [{name:"aaa",url:"/aaa.jpg"}]
        showMsg("上传成功");
    }
    else if(response.msg == "extError"){
        showError("请上传.jpg 或 .png 类型的图片！");
    }
    else if(response.msg == "unlogin"){
        app.fileList = [];
        showError("请先登录系统！");
    }
    else{
        showError("上传失败！");
    }
}
var onError = function(err,file,fileList){
    showError("上传失败！");
}
//删除图片前
var beforeRemove = function(file,fileList){
    debugger

}
//删除图片
var onRemove = function(file,fileList){
    debugger
}
//图片超出允许上传的最多个数时
var onExceed = function(file,fileList){
    debugger
    showError("只能上传一张底图，重传请先删除原来的！");
}

function checkFileType(file) {
    var fileType = file.type;
    var fileTypes = ["image/jpeg", "image/png"];
    if (fileTypes.indexOf(fileType) == -1) {
        showError("文件类型不对,请上传 .jpg 或 .png 类型的图片！");
        return false;
    }
    return true;
}

function checkFileSize(file) {
    var fileSize = file.size/1024/1024;
    if (fileSize > 20) {
        showError("文件大小请小于20M");
        return false;
    }
    return true;
}