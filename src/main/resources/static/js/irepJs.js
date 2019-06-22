var isRemoveStopWord;//是否去停用词
var analyzerName;//分词器名称

/**
 * preProcessing.html
 * @type {HTMLElement}
 */
var num_jia = document.getElementById("num-jia");
var num_jian = document.getElementById("num-jian");
var input_num = document.getElementById("docId");
num_jia.onclick = function () {
    if (input_num.value < 165)
        input_num.value = parseInt(input_num.value) + 1;
};
num_jian.onclick = function () {

    if (input_num.value <= 0) {
        input_num.value = 0;
    } else {
        input_num.value = parseInt(input_num.value) - 1;
    }
};

/**
 * preProcessing.html
 */
$(function () {
    /**
     * 获取原文件
     */
    $("#docId").change(function () {
        $("#originalFile").text("");//初始化
        var docId = $("#docId").val();
        if ((docId <= 165) && (docId >= 0)) {
            $.ajax({
                type: "POST",
                url: "preProcessing/getDoc",
                data: {"docId": docId},
                dataType: "text",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                traditional: true,
                success: function (result) {
                    console.log(result);
                    $("#originalFile").append(result);
                },
                error: function () {
                    alert("检索出错！");
                    $("#originalFile").text("");// 清空数据
                }
            })//end of ajax
        } else {
            alert("请输入0-165之间的任意整数");
        }
    })
    $("#num-jia").click(function () {
        $("#originalFile").text("");//初始化
        var docId = $("#docId").val();
        $.ajax({
            type: "POST",
            url: "preProcessing/getDoc",
            data: {"docId": docId},
            dataType: "text",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            traditional: true,
            success: function (result) {
                console.log(result);
                $("#originalFile").append(result);

            },
            error: function () {
                alert("检索出错！");
                $("#originalFile").text("");// 清空数据
            }
        })//end of ajax
    })
    $("#num-jian").click(function () {
        $("#originalFile").text("");//初始化
        var docId = $("#docId").val();
        $.ajax({
            type: "POST",
            url: "preProcessing/getDoc",
            data: {"docId": docId},
            dataType: "text",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            traditional: true,
            success: function (result) {
                console.log(result);
                $("#originalFile").append(result);
            },
            error: function () {
                alert("检索出错！");
                $("#originalFile").text("");// 清空数据
            }
        })//end of ajax
    })

    /**
     * 获取处理结果
     */
    $("#preProcess").click(function () {
        $("#result").text("");//初始化
        var isRSW = $("input[name='isRemoveStopWord']").is(':checked') == true ? true : false;
        var aN = $("#analyzerName option:selected").val();
        var token=$("#originalFile").text();
        var storage=window.localStorage;
        storage.isRemoveStopWord=isRSW;
        storage.analyzerName=aN;
        console.log(token);
        if (token!=null&&token!=""){
            $.ajax({
                type: "POST",
                url: "preProcessing/preProcess",
                data: {
                    "token": token,
                    "analyzerName": aN,
                    "isRemoveStopWord": isRSW
                },
                dataType: "text",
                contentType: "application/x-www-form-urlencoded; charset=utf-8",
                traditional: true,
                success: function (result) {
                    console.log(result);
                    $("#result").append(result);
                    $("#nextStep").show();
                },
                error: function () {
                    alert("检索出错！");
                    $("#result").text("");// 清空数据
                }
            })//end of ajax
        } else {
            alert("请选择待处理的原文件")
        }
    })
});
