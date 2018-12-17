var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");

$(document).ajaxSend(function (e, xhr, options) {
    xhr.setRequestHeader(header, token);
});

function formatDate(date, fmt) {
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '')
            .substr(4 - RegExp.$1.length));
    }
    let o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds()
    };
    for (let k in o) {
        if (new RegExp(`(${k})`).test(fmt)) {
            let str = o[k] + '';
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str
                : padLeftZero(str));
        }
    }
    return fmt;
};

function padLeftZero(str) {
    return ('00' + str).substr(str.length);
}

function showError(msg) {
    app.$message({
        showClose: true,
        message: msg,
        type: 'error',
        duration: 3000
    });
}

function showMsg(msg) {
    app.$message({
        showClose: true,
        message: msg,
        type:'success',
        duration: 3000
    });
}

function showCofirm(func,msg) {
    var s = "此操作将删除信息, 是否继续?";
    if(msg)
        s = msg;

    app.$confirm(s, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
    }).then(() => {
        if(func)
            func();
    }).catch(()=>{});
}

//支持中文参数值
function GetQueryString(name)
{
    //正则表达式过滤
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    //substr（1）：从字符串第一个位置中提取一些字符
    //match（）：在字符串内检索与正则表达式匹配的指定值，返回一个数组给r
    var r = window.location.search.substr(1).match(reg);
    //获取r数组中下标为2的值；（下标从0开始），用decodeURI（）进行解码
    if (r != null) return decodeURI(r[2]); return null;
}
