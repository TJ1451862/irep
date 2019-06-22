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
        var token = $("#originalFile").text();
        var storage = window.localStorage;
        storage.isRemoveStopWord = isRSW;
        storage.analyzerName = aN;
        console.log(token);
        if (token != null && token != "") {
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


/**
 * invertedIndex.html
 */
/**
 * 小试牛刀
 */
$("#invertedIndexConfirm").click(function () {

    var input1 = $("#input1").val();
    var input2 = document.getElementById("input2").value;
    var input3 = document.getElementById("input3").value;
    var input4 = document.getElementById("input4").value;
    var input5 = document.getElementById("input5").value;
    var input6 = document.getElementById("input6").value;
    var input7 = document.getElementById("input7").value;
    var input8 = document.getElementById("input8").value;
    var input9 = document.getElementById("input9").value;
    var input10 = document.getElementById("input10").value;
    var input11 = document.getElementById("input11").value;
    var input12 = document.getElementById("input12").value;
    var input13 = document.getElementById("input13").value;
    if (input1 != 1) {
        document.getElementById("input1").style.backgroundColor = 'red'
    } else {
        document.getElementById("input1").style.backgroundColor = '#ffffff'
    }
    if (input2 != 2) {
        document.getElementById("input2").style.backgroundColor = 'red'
    } else {
        document.getElementById("input2").style.backgroundColor = '#ffffff'
    }
    if (input3 != 2) {
        document.getElementById("input3").style.backgroundColor = 'red'
    } else {
        document.getElementById("input3").style.backgroundColor = '#ffffff'
    }
    if (input4 != 3) {
        document.getElementById("input4").style.backgroundColor = 'red'
    } else {
        document.getElementById("input4").style.backgroundColor = '#ffffff'
    }
    if (input5 != 2) {
        document.getElementById("input5").style.backgroundColor = 'red'
    } else {
        document.getElementById("input5").style.backgroundColor = '#ffffff'
    }
    if (input6 != 1) {
        document.getElementById("input6").style.backgroundColor = 'red'
    } else {
        document.getElementById("input6").style.backgroundColor = '#ffffff'
    }
    if (input7 != 1) {
        document.getElementById("input7").style.backgroundColor = 'red'
    } else {
        document.getElementById("input7").style.backgroundColor = '#ffffff'
    }
    if (input8 != 1) {
        document.getElementById("input8").style.backgroundColor = 'red'
    } else {
        document.getElementById("input8").style.backgroundColor = '#ffffff'
    }
    if (input9 != 3) {
        document.getElementById("input9").style.backgroundColor = 'red'
    } else {
        document.getElementById("input9").style.backgroundColor = '#ffffff'
    }
    if (input10 != 1) {
        document.getElementById("input10").style.backgroundColor = 'red'
    } else {
        document.getElementById("input10").style.backgroundColor = '#ffffff'
    }
    if (input11 != 1) {
        document.getElementById("input11").style.backgroundColor = 'red'
    } else {
        document.getElementById("input11").style.backgroundColor = '#ffffff'
    }
    if (input12 != 1) {
        document.getElementById("input12").style.backgroundColor = 'red'
    } else {
        document.getElementById("input12").style.backgroundColor = '#ffffff'
    }
    if (input13 != 3) {
        document.getElementById("input13").style.backgroundColor = 'red'
    } else {
        document.getElementById("input13").style.backgroundColor = '#ffffff'
    }
    if (input1 == 1 & input2 == 2 & input3 == 2
        & input4 == 3 & input5 == 2 & input6 == 1
        & input7 == 1 & input8 == 1 & input9 == 3
        & input10 == 1 & input11 == 1 & input12 == 1
        & input13 == 3) {

        $("#invertedIndexConfirm").hide();
        $("#createIndex").show();
    }
});

/**
 * 选择模型并跳转到下一步
 */
$('#nextStepOfInvertedIndex').click(function () {
    var model = $("input[name='modelType']:checked").val();
    var storage = window.localStorage;
    storage.model = model;
    if (model == "vsm") {
        $(location).attr("href","../IRforCN/Retrieval/vectorSpaceModel.html");
    } else if (model == "bool") {
        $(location).attr("href","../IRforCN/Retrieval/boolean.html");
    } else if (model == "pbm") {
        $(location).attr("href","../IRforCN/Retrieval/probabilityModel.html");
    } else if (model == "lm") {
        $(location).attr("href","../IRforCN/Retrieval/languageModel.html");
    }

});

// 获取fullIndex的数据
$("#createIndex").click(function () {

    // 隐藏答题部分
    $("#test").hide();
    $("#heading1").hide();
    // 显示倒排索引部分
    $("#heading2").show();
    $("#fullIndexTable").show();
    $("#indexTable").show();
    $("#selectModel").show();

    //从localStorage中获取参数
    var storage = window.localStorage;
    var isRSW = storage.isRemoveStopWord;
    var aN = storage.analyzerName
    console.log(isRSW + aN);

    //获取全体倒排索引表
    $.ajax({
        type: "POST",
        url: "invertedIndex/fullIndex",
        data: {
            "analyzerName": aN,
            "isRemoveStopWord": isRSW
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (result) {
            var str = "";
            $.each(result, function (index, value) {
                str += "<tr style='height: 35px' term=\"" + value.term + "\" onclick=\"selectInvertedIndex(this.getAttribute('term'))\">\n" +
                    "    <td width='85px' >" + value.term + "</td>\n" +
                    "    <td width='85px'>" + value.df + "</td>\n" +
                    "    <td class='award-name' width='290px'>" + value.ids + "</td>\n" +
                    "</tr>"
            });
            //向fullIndex表中添加数据
            $("#tbody1").append(str);
        },
        error: function () {
            alert("检索出错！");
            $("#tbody1").text("");// 清空数据
        }
    })//end of ajax
});

/**
 * 获取invertedIndex中的数据
 * @param term 词项
 */
function selectInvertedIndex(term) {
    //从localStorage中获取参数
    var storage = window.localStorage;
    var isRSW = storage.isRemoveStopWord;
    var aN = storage.analyzerName;

    console.log(term);
    $("#tbody2").text("");//初始化
    //获取倒排索引表
    $.ajax({
        type: "POST",
        url: "invertedIndex/invertedIndex",
        data: {
            "term": term,
            "analyzerName": aN,
            "isRemoveStopWord": isRSW
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (result) {
            console.log(result);
            var str = "";
            $.each(result, function (index, value) {
                str += "<tr style='height: 35px' docId=\"" + value.docId + "\" term=\"" + value.term + "\" onclick=\"getDoc(this.getAttribute('docId'),this.getAttribute('term'))\">\n" +
                    "     <td>" + value.docId + "</td>\n" +
                    "     <td>" + value.tf + "</td>\n" +
                    "     <td class='award-name'>" + value.locations + "</td>\n" +
                    "</tr>"
            });
            $("#tbody2").append(str);
        },
        error: function () {
            alert("检索出错！");
            $("#result").text("");// 清空数据
        }
    })//end of ajax
}

/**
 * 获取文章，并强调词项
 * @param docId 文章Id
 * @param term 待强调的词项
 */
function getDoc(docId, term) {
    $("#tab-1").text("");//初始化
    console.log(docId, term);

    //激活tab-1
    $("[href='#tab-1']").addClass("active");
    $("#tab-1").addClass("active");

    //关闭tab-2
    $("[href='#tab-2']").removeClass("active");
    $("#tab-2").removeClass("active");

    // 获取原文章
    $.ajax({
        type: "POST",
        url: "preProcessing/getDoc",
        data: {"docId": docId},
        dataType: "text",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (result) {
            var reg = new RegExp(term, "g");//构造全局替换表达式
            var str = result.replace(reg, "<span style='background-color: #b2d19e;font-weight: bold'>" + term + "</span>");
            str = "<p class=\"tip\" style=\"margin:0;font-size: 20px\">" + str + "</p>";
            $("#tab-1").append(str);
        },
        error: function () {
            alert("检索出错！");
            $("#tab-1").text("");// 清空数据
        }
    })//end of ajax
}





