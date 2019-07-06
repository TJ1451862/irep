var storage = window.localStorage;
//倒排索引页面表格行背景色
var colorOver1= "rgb(47,82,143)"; //表格1鼠标经过时的颜色
var colorOver2= "rgb(112,173,71)"; //表格2鼠标经过时的颜色
var colorClick1="rgb(178,209,158)"; //表格1鼠标点击的颜色
var colorClick2="green"; //表格2鼠标点击的颜色
var colorNone1="rgb(146,165,198)";//表格1行默认背景颜色
var colorNone2="rgb(178,209,158)";//表格2行默认背景色
var preAnswer=["去停用词","分词","字符串","出现频率","abcd","abcd","abc","d"] //预处理页面答案，选择题给出正确选项的value值

/**
 * login.html
 */
/**
 * 登录功能
 */
$("#login").click(function () {
    var userName = $("#userName").val();
    var password = $("#password").val();

    $.ajax({
        type: "POST",
        url: "user/login",
        data: {
            "username": userName,
            "password": password
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (result) {
            if(result.code==0){
                alert(result.message)
            }else if (result.code==1){
                $(location).attr("href", "/index.html");
            }
            console.log(result);
        },
        error: function (result) {
            alert("检索出错！");
        }
    });
});

/**
 * sign.html
 */

$("#signIn").click(function () {
    var userName=$("#userName").val();
    var password=$("#password").val();
    var phone=$("#phone").val();
    var email=$("#email").val();
    var works=$("#works").val();

    $.ajax({
        type: "POST",
        url: "user/signIn",
        data: {
            "username": userName,
            "password": password,
            "phone":phone,
            "email":email,
            "works":works
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (result) {
            console.log(result);
            if(result.code!=0){
                alert(result.message)
            }else {
                $(location).attr("href", "/login.html");
            }
        },
        error: function (result) {
            alert("注册失败");
        }

    })
});


/**
 * 增减input中的数字大小
 * @type {HTMLElement}
 */
function num_jia(obj, upperLimit, stepLength) {
    var input_num = obj.previousElementSibling;
    if (input_num.value < upperLimit)
        input_num.value = parseFloat(input_num.value) + stepLength;
}

function num_jian(obj, lowerLimit, stepLength) {
    var input_num = obj.nextElementSibling;
    if (input_num.value > lowerLimit)
        input_num.value = parseFloat(input_num.value) - stepLength;
}

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
    });
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
    });

    /**
     * 获取处理结果
     */
    $("#preProcess").click(function () {
        $("#result").text("");//初始化
        var isRSW = $("input[name='isRemoveStopWord']").is(':checked') == true ? true : false;
        var aN = $("#analyzerName option:selected").val();
        var token = $("#originalFile").text();

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

// 各界面答题部分
$(".exaConfirm").click(function (){
    var correctNum=0; //正确的题数，全部答对才可进入下一步
    var Answer=[];
    // 对应各个页面的答案
    if($(this)[0].parentNode.id=="preExamination"){
        Answer=preAnswer
    }
    var $fillResults=$(".fillIn input");// 填空题填写的答案
    // 判断填空题是否正确
    for(var i=0;i<$fillResults.length;i++){
        if($fillResults[i].value!=Answer[i]){
            $fillResults[i].style.backgroundColor="red";
        }else{
            correctNum=correctNum+1;
            $fillResults[i].style.backgroundColor="";
        }
    }
    // 判断选择题是否正确
    for(var j=0;j<$(".multipleChoice").length;j++){
        var r='';
        var $choices=$(".multipleChoice:eq("+j+")").find("input");
        for(var m=0;m<$choices.length;m++){
            if($choices[m].checked){
                r=r+$choices[m].value;
            }
        }
        if(Answer[j+4]!=r){
            $(".multipleChoice:eq("+j+")").css("background","red");
        }else{
            $(".multipleChoice:eq("+j+")").css("background","");
            correctNum=correctNum+1;
        }
    }
    //如果全部答对则答题部分隐藏，实验部分出现；
    if(correctNum==1){
        $($(this)[0].parentNode).hide();
        $("#experimentPart").show();
    }

    // if($(this)[0].parentNode.id=="preExamination"){
    //     // 判断填空题是否正确
    //     for(var i=0;i<$fillResults.length;i++){
    //         if($fillResults[i].value!=Answer[i]){
    //             $fillResults[i].style.backgroundColor="red";
    //         }else{
    //             correctNum=correctNum+1;
    //             $fillResults[i].style.backgroundColor="";
    //         }
    //     }
    //     // 判断选择题是否正确
    //     for(var j=0;j<$(".multipleChoice").length;j++){
    //         var r='';
    //         var $choices=$(".multipleChoice:eq("+j+")").find("input");
    //         for(var m=0;m<$choices.length;m++){
    //             if($choices[m].checked){
    //                 r=r+$choices[m].value;
    //             }
    //         }
    //         if(Answer[j+4]!=r){
    //            $(".multipleChoice:eq("+j+")").css("background","red");
    //         }else{
    //             $(".multipleChoice:eq("+j+")").css("background","");
    //             correctNum=correctNum+1;
    //         }
    //     }
    //     //如果全部答对则答题部分隐藏，实验部分出现；
    //     if(correctNum==8){
    //         $("#preExamination").hide();
    //         $("#experimentPart").show();
    //     }
    // }
});

/**
 *  dIndex.html
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

    storage.model = model;
    if (model == "vsm") {
        $(location).attr("href", "../IRforCN/Retrieval/vectorSpaceModel.html");
    } else if (model == "bool") {
        $(location).attr("href", "../IRforCN/Retrieval/boolean.html");
    } else if (model == "pbm") {
        $(location).attr("href", "../IRforCN/Retrieval/probabilityModel.html");
    } else if (model == "lm") {
        $(location).attr("href", "../IRforCN/Retrieval/languageModel.html");
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

    var isRSW = storage.isRemoveStopWord;
    var aN = storage.analyzerName;
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
                str += "<tr style='height: 35px'  term=\"" + value.term + "\" onmouseover=\'mouseOverTable1(this)\' onmouseout=\'mouseOutTable1(this)\' onclick=\"selectInvertedIndex(this,this.getAttribute('term'))\">\n" +
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
function selectInvertedIndex(obj, term) {
    //从localStorage中获取参数
    var isRSW = storage.isRemoveStopWord;
    var aN = storage.analyzerName;
    var tab1 = document.getElementById("tbody1");
    var rows1 = tab1.rows;
    for (var c = 0; c < rows1.length; c++) {
        if (rows1[c] == obj) {
            rows1[c].style.backgroundColor = colorClick1;
            localStorage.setItem("invertedrowNo1", c);
        } else {
            rows1[c].style.backgroundColor = colorNone1;
        }
    }

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
                str += "<tr style='height: 35px'  docId=\"" + value.docId + "\" term=\"" + value.term + "\" onmouseover=\'mouseOverTable2(this)\' onmouseout=\'mouseOutTable2(this)\' onclick=\"getDoc(this,this.getAttribute('docId'),this.getAttribute('term'))\">\n" +
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

function mouseOverTable1(obj) {
    var tab1 = document.getElementById("tbody1");
    var rows1 = tab1.rows;
    var no = localStorage.getItem("invertedrowNo1");
    for (var c = 0; c < rows1.length; c++) {
        if (c != no && rows1[c] == obj) {
            rows1[c].style.backgroundColor = colorOver1;
        }
    }
}

function mouseOutTable1(obj) {
    var tab1 = document.getElementById("tbody1");
    var rows1 = tab1.rows;
    var no = localStorage.getItem("invertedrowNo1");
    for (var c = 0; c < rows1.length; c++) {
        if (c != no && rows1[c] == obj) {
            rows1[c].style.backgroundColor = colorNone1;
        }
    }
}

function mouseOverTable2(obj) {
    var tab2 = document.getElementById("tbody2");
    var rows2 = tab2.rows;
    var No = localStorage.getItem("invertedrowNo2");
    for (var d = 0; d < rows2.length; d++) {
        if (d != No && rows2[d] == obj) {
            rows2[d].style.backgroundColor = colorOver2;
        }
    }
}

function mouseOutTable2(obj) {
    var tab2 = document.getElementById("tbody2");
    var rows2 = tab2.rows;
    var No = localStorage.getItem("invertedrowNo2");
    for (var d = 0; d < rows2.length; d++) {
        if (d != No && rows2[d] == obj) {
            rows2[d].style.backgroundColor = colorNone2;
        }
    }
}

/**
 * 获取文章，并强调词项
 * @param docId 文章Id
 * @param term 待强调的词项
 */
function getDoc(obj, docId, term) {
    var tab2 = document.getElementById("tbody2");
    var rows2 = tab2.rows;
    for (var c = 0; c < rows2.length; c++) {
        if (rows2[c] == obj) {
            rows2[c].style.backgroundColor = colorClick2;
            localStorage.setItem("invertedrowNo2", c);
        } else {
            rows2[c].style.backgroundColor = colorNone2;
        }
    }

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

/**
 * vectorSpaceModel.html
 */
var json = null;
var $results = null;
var tempJson = null;
var tempSign = 0;//0:当前tempJson中存储的是tfs，1：当前tempJson中存储的是vectors
/**
 * 检索功能
 */
$("#searchForVSM").click(function () {

    $("#first").hide();
    $("#second").show();

    var query = $("#inputForVSM").val();
    var formulaID = parseInt($("input[name='formula']:checked").val());
    var smoothParam = $("#smoothParam").val();
    var analyzerName = storage.analyzerName;
    var isRemoveStopWord = storage.isRemoveStopWord;

    $("#results").text("");

    $.ajax({
        type: "POST",
        url: "vectorSpaceModel/vsmSearch",
        dataType: "json",
        data: {
            "query": query,
            "formulaId": formulaID,
            "smoothParam": smoothParam,
            "analyzerName": analyzerName,
            "isRemoveStopWord": isRemoveStopWord
        },
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (result) {
            console.log(result);
            json = result;
            $.each(result, function (index, obj) {
                var str1 = obj.content.substring(0, 90) + "......";
                $('#results').append("<a id='" + index + "' href='' class='ra'  data-toggle='modal' data-target='#myModal' onclick='getContent(this.id)' >" + obj.title + "</a>");
                $('#results').append("<br>");
                $('#results').append("<p>" + str1 + "</p>");

            });
            $results = $("#results").find("*");
        },
        error: function () {
            alert("检索出错！");
            $("#results").text("");// 清空数据
        }
    })
});

/**
 * 求idf功能
 */
$("#idf").click(function () {
    $("#results").text("");

    var query = $("#inputForVSM").val();
    var formulaID = parseInt($("input[name='formula']:checked").val());
    var smoothParam = $("#smoothParam").val();
    var analyzerName = storage.analyzerName;
    var isRemoveStopWord = storage.isRemoveStopWord;

    $.ajax({
        type: "POST",
        url: "vectorSpaceModel/idf",
        data: {
            "query": query,
            "formulaId": formulaID,
            "smoothParam": smoothParam,
            "analyzerName": analyzerName,
            "isRemoveStopWord": isRemoveStopWord
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (idf) {
            console.log(idf);
            $("#results").append(
                "<div class=\"table-responsive smallTable\">\n" +
                "    <table>\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th>词项</th>\n" +
                "                <th>idf</th>\n" +
                "                <th>词项</th>\n" +
                "                <th>idf</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <colgroup>\n" +
                "            <col width=25% />\n" +
                "            <col width=25% />\n" +
                "            <col width=25% />\n" +
                "            <col width=25% />" +
                "        </colgroup>\n" +
                "        <tbody id='tbody'></tbody>\n" +
                "    </table>\n" +
                "</div>");

            var item1, idf1;
            var select = 0;
            $.each(idf, function (index, obj) {
                if (select == 0) {
                    item1 = index;
                    idf1 = obj;
                    select = 1;
                    $('#tbody').append(
                        "<tr>\n" +
                        "    <td>" + item1 + "</td>\n" +
                        "    <td>" + idf1.toFixed(4) + "</td>\n" +
                        "</tr>\n"
                    );
                } else {
                    item1 = index;
                    idf1 = obj;
                    select = 0;
                    $('#tbody').children().last().append(
                        "<td class=\"smallTableForTd\" >" + item1 + "</td>\n" +
                        "<td class=\"smallTableForTd\" >" + idf1.toFixed(4) + "</td>\n"
                    );
                }
            });//end of each
        },
        error: function () {
            alert("检索出错！");
            $("#results").text("");// 清空数据
        }
    })//end of ajax
});

/**
 * 查询预处理结果
 */
$("#preProcessQuery").click(function () {

    var query = $("#inputForVSM").val();
    var formulaID = parseInt($("input[name='formula']:checked").val());
    var smoothParam = $("#smoothParam").val();
    var analyzerName = storage.analyzerName;
    var isRemoveStopWord = storage.isRemoveStopWord;

    $("#results").text("");//清空数据
    $.ajax({
        type: "POST",
        url: "vectorSpaceModel/ppq",
        data: {
            "query": query,
            "formulaId": formulaID,
            "smoothParam": smoothParam,
            "analyzerName": analyzerName,
            "isRemoveStopWord": isRemoveStopWord
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (result) {
            $("#results").append(
                "查询语句内容：" + query + "<br>" +
                "预处理结果：" + result.result + "<br>"
            )
        },
        error: function () {
            alert("检索出错！");
            $("#results").text("");// 清空数据
        }
    })//end of ajax
});
/**
 * 求查询的tf
 */
$("#tfOfQuery").click(function () {
    var query = $("#inputForVSM").val();
    var formulaID = parseInt($("input[name='formula']:checked").val());
    var smoothParam = $("#smoothParam").val();
    var analyzerName = storage.analyzerName;
    var isRemoveStopWord = storage.isRemoveStopWord;

    $("#results").text("");//清空数据
    $.ajax({
        type: "POST",
        url: "vectorSpaceModel/tfOfQuery",
        data: {
            "query": query,
            "formulaId": formulaID,
            "smoothParam": smoothParam,
            "analyzerName": analyzerName,
            "isRemoveStopWord": isRemoveStopWord
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (tf) {
            console.log(tf);
            $("#results").append(
                "<div class=\"table-responsive smallTable\">\n" +
                "    <table>\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th>词项</th>\n" +
                "                <th>TF</th>\n" +
                "                <th>词项</th>\n" +
                "                <th>TF</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <tbody id='tbody'></tbody>\n" +
                "    </table>\n" +
                "</div>");

            var item1, tf1;
            var select = 0;

            $.each(tf, function (index, obj) {
                if (select == 0) {
                    item1 = index;
                    tf1 = obj;
                    select = 1;
                    $('#tbody').append("<tr>\n" +
                        "<td>" + item1 + "</td>\n" +
                        "<td>" + tf1 + "</td>\n" +
                        "</tr>\n"
                    );
                } else {
                    item1 = index;
                    tf1 = obj;
                    select = 0;
                    $('#tbody').children().last().append(
                        "<td>" + item1 + "</td>\n" +
                        "<td>" + tf1 + "</td>\n"
                    );
                }
            });//end of each
        },
        error: function () {
            alert("检索出错！");
            $("#results").text("");// 清空数据
        }
    })//end of ajax
});

/**
 * 求查询向量
 */
$("#queryVector").click(function () {
    var query = $("#inputForVSM").val();
    var formulaID = parseInt($("input[name='formula']:checked").val());
    var smoothParam = $("#smoothParam").val();
    var analyzerName = storage.analyzerName;
    var isRemoveStopWord = storage.isRemoveStopWord;

    $("#results").text("");//清空数据
    $.ajax({
        type: "POST",
        url: "vectorSpaceModel/vectorOfQuery",
        data: {
            "query": query,
            "formulaId": formulaID,
            "smoothParam": smoothParam,
            "analyzerName": analyzerName,
            "isRemoveStopWord": isRemoveStopWord
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (vectors) {
            $("#results").append(
                "<div class=\"table-responsive smallTable\">\n" +
                "    <table>\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th>词项</th>\n" +
                "                <th>第几位</th>\n" +
                "                <th>值</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <colgroup>\n" +
                "            <col width=30% />\n" +
                "            <col width=30% />\n" +
                "            <col width=40% />\n" +
                "        </colgroup>\n" +
                "        <tbody id='tbody'></tbody>\n" +
                "    </table>\n" +
                "</div>");

            $.each(vectors, function (index, obj) {
                $('#tbody').append("<tr>\n" +
                    "<td>" + obj.term + "</td>\n" +
                    "<td>" + obj.num + "</td>\n" +
                    "<td>" + obj.value.toFixed(4) + "</td>\n" +
                    "</tr>\n"
                );
            });//end of each
        },
        error: function () {
            alert("检索出错！");
            $("#results").text("");// 清空数据
        }
    })//end of ajax
});
/**
 * 求各文档的TF
 */
$("#tfsOfDocs").click(function () {

    var query = $("#inputForVSM").val();
    var formulaID = parseInt($("input[name='formula']:checked").val());
    var smoothParam = $("#smoothParam").val();
    var analyzerName = storage.analyzerName;
    var isRemoveStopWord = storage.isRemoveStopWord;

    $("#results").text("");//清空数据
    $.ajax({
        type: "POST",
        url: "vectorSpaceModel/tfsOfDocs",
        data: {
            "query": query,
            "formulaId": formulaID,
            "smoothParam": smoothParam,
            "analyzerName": analyzerName,
            "isRemoveStopWord": isRemoveStopWord
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (tfs) {
            tempSign = 0;
            var docIdInVSM = $("#docIdInVSM").val();
            tempJson = tfs;
            append(docIdInVSM);
        },
        error: function () {
            alert("检索出错！");
            $("#results").text("");// 清空数据
        }
    })//end of ajax
});
/**
 * 求各文档的向量
 */
$("#vectorsOfDocs").click(function () {

    $("#results").text("");//清空数据
    var query = $("#inputForVSM").val();
    var formulaID = parseInt($("input[name='formula']:checked").val());
    var smoothParam = $("#smoothParam").val();
    var analyzerName = storage.analyzerName;
    var isRemoveStopWord = storage.isRemoveStopWord;

    $.ajax({
        type: "POST",
        url: "vectorSpaceModel/vectorsOfDocs",
        data: {
            "query": query,
            "formulaId": formulaID,
            "smoothParam": smoothParam,
            "analyzerName": analyzerName,
            "isRemoveStopWord": isRemoveStopWord
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (vectors) {
            tempSign = 1;
            tempJson = vectors;
            var docIdInVSM = $("#docIdInVSM").val();
            append(docIdInVSM);
        },
        error: function () {
            alert("检索出错！");
            $("#results").text("");// 清空数据
        }
    })//end of ajax
})//求各文档的向量
/**
 * 获取并显示下一个的文档的tf或Vector
 */
$("#plusDocId").click(function () {
    $("#results").text("");//清空数据
    var docIdInVSM = $("#docIdInVSM").val();
    append(docIdInVSM);
});
/**
 * 获取并显示上一个文档的tf或Vector
 */
$("#minusDocId").click(function () {
    $("#results").text("");//清空数据
    var docIdInVSM = $("#docIdInVSM").val();
    append(docIdInVSM);
});
/**
 * 获取当前文档的tf或Vector
 */
$("#docIdInVSM").change(function () {
    $("#results").text("");//清空数据
    var docIdInVSM = $("#docIdInVSM").val();
    if ((docIdInVSM <= 165) && (docIdInVSM >= 0)) {
        append(docIdInVSM);
    } else {
        alert("请输入0-165之间的任意整数");
    }
});
/**
 * 求相似度
 */
$("#similarity").click(function () {
    $("#results").text("");//清空数据
    var query = $("#inputForVSM").val();
    var formulaID = parseInt($("input[name='formula']:checked").val());
    var smoothParam = $("#smoothParam").val();
    var analyzerName = storage.analyzerName;
    var isRemoveStopWord = storage.isRemoveStopWord;
    $.ajax({
        type: "POST",
        url: "vectorSpaceModel/similarity",
        data: {
            "query": query,
            "formulaId": formulaID,
            "smoothParam": smoothParam,
            "analyzerName": analyzerName,
            "isRemoveStopWord": isRemoveStopWord
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (result) {
            console.log(result);
            $("#results").append(
                "<div class=\"table-responsive smallTable\">\n" +
                "    <table>\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th>ID</th>\n" +
                "                <th>文档名</th>\n" +
                "                <th>相似度</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <colgroup>\n" +
                "            <col width=10% />\n" +
                "            <col width=70% />\n" +
                "            <col width=20% />\n" +
                "        </colgroup>\n" +
                "        <tbody id='tbody'></tbody>\n" +
                "    </table>\n" +
                "</div>");
            $.each(result, function (index, obj) {
                console.log(obj);
                $('#tbody').append("<tr>\n" +
                    "<td>" + obj.docID + "</td>\n" +
                    "<td>" + obj.title + "</td>\n" +
                    "<td>" + obj.similarity.toFixed(5) + "</td>\n" +
                    "</tr>\n"
                );
            });//end of each
        },
        error: function () {
            alert("检索出错！");
            $("#results").text("");// 清空数据
        }
    })//end of ajax
});
/**
 * 按相似度降序排序
 */
$("#similarityAfterSort").click(function () {

    $("#results").text("");//清空数据
    var query = $("#inputForVSM").val();
    var formulaID = parseInt($("input[name='formula']:checked").val());
    var smoothParam = $("#smoothParam").val();
    var analyzerName = storage.analyzerName;
    var isRemoveStopWord = storage.isRemoveStopWord;
    $.ajax({
        type: "POST",
        url: "vectorSpaceModel/similarityAfterSort",
        data: {
            "query": query,
            "formulaId": formulaID,
            "smoothParam": smoothParam,
            "analyzerName": analyzerName,
            "isRemoveStopWord": isRemoveStopWord
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (result) {
            console.log(result);
            $("#results").append(
                "<div class=\"table-responsive smallTable\">\n" +
                "    <table>\n" +
                "        <thead>\n" +
                "            <tr>\n" +
                "                <th>ID</th>\n" +
                "                <th>文档名</th>\n" +
                "                <th>相似度</th>\n" +
                "            </tr>\n" +
                "        </thead>\n" +
                "        <colgroup>\n" +
                "            <col width=10% />\n" +
                "            <col width=70% />\n" +
                "            <col width=20% />\n" +
                "        </colgroup>\n" +
                "        <tbody id='tbody'></tbody>\n" +
                "    </table>\n" +
                "</div>");
            $.each(result, function (index, obj) {
                console.log(obj);
                $('#tbody').append("<tr>\n" +
                    "<td>" + obj.docID + "</td>\n" +
                    "<td>" + obj.title + "</td>\n" +
                    "<td>" + obj.similarity.toFixed(5) + "</td>\n" +
                    "</tr>\n"
                );
            });//end of each
        },
        error: function () {
            alert("检索出错！");
            $("#results").text("");// 清空数据
        }
    })//end of ajax
});


/**
 * 获取文档内容
 * @param id
 */
function getContent(id) {
    $.each(json, function (index, obj) {
        if (index == id) {
            $("#results").text("");
            $("#results").append(obj.content);
        }
    });
};

/**
 * 显示对应文档Id的TF或Vectors
 * @param docIdInVSM
 */
function append(docIdInVSM) {
    if (tempSign == 0) {
        $.each(tempJson, function (index, json) {
            if (json.docId == docIdInVSM) {
                $("#results").append(
                    "<div class=\"table-responsive smallTable\">\n" +
                    "    <table>\n" +
                    "        <thead >\n" +
                    "            <tr class=\"table-primary\">\n" +
                    "                <td colspan=\"4\" style=\"text-align:center;\"><strong>" + json.title + "</strong></td>\n" +
                    "            </tr>\n" +
                    "            <tr style='border-bottom:3px solid rgb(0,0,0)'>\n" +
                    "                <th>词项</th>\n" +
                    "                <th>TF</th>\n" +
                    "                <th>词项</th>\n" +
                    "                <th>TF</th>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "        <tbody id='tbody'></tbody>\n" +
                    "    </table>\n" +
                    "</div>"
                );
                var item1, tf1;
                var select = 0;
                $.each(json.tfs, function (index1, obj1) {
                    if (select == 0) {
                        item1 = index1;
                        tf1 = obj1;
                        select = 1;
                        $("#tbody").append(
                            "<tr>\n" +
                            "    <td>" + item1 + "</td>\n" +
                            "    <td>" + tf1.toFixed(4) + "</td>\n" +
                            "</tr>\n"
                        );
                    } else {
                        item1 = index1;
                        tf1 = obj1;
                        select = 0;
                        $("#tbody").children().last().append(
                            "<td>" + item1 + "</td>\n" +
                            "<td>" + tf1.toFixed(4) + "</td>\n"
                        );
                    }
                });//end of each
            }
        })//end of each
    } else if (tempSign == 1) {
        $.each(tempJson, function (index, json) {
            if (json.docId == docIdInVSM) {
                $("#results").append(
                    "<div class=\"table-responsive smallTable\">\n" +
                    "    <table>\n" +
                    "        <thead>\n" +
                    "            <tr class=\"table-primary\">\n" +
                    "                <td colspan=\"4\" style=\"text-align:center;\"><strong>" + json.title + "</strong></td>\n" +
                    "            </tr>\n" +
                    "            <tr>\n" +
                    "                <th>词项</th>\n" +
                    "                <th>第几位</th>\n" +
                    "                <th>值</th>\n" +
                    "            </tr>\n" +
                    "        </thead>\n" +
                    "        <colgroup>\n" +
                    "            <col width=30% />\n" +
                    "            <col width=30% />\n" +
                    "            <col width=40% />\n" +
                    "        </colgroup>\n" +
                    "        <tbody id='tbody'></tbody>\n" +
                    "    </table>\n" +
                    "</div>");
                $.each(json.vector, function (index, obj) {
                    $("#tbody").append("<tr>\n" +
                        "<td>" + obj.term + "</td>\n" +
                        "<td>" + obj.num + "</td>\n" +
                        "<td>" + obj.value.toFixed(4) + "</td>\n" +
                        "</tr>\n"
                    );
                });//end of each
            }
        })//end of each
    }
}

/**
 * 返回上一步
 */
$("#return").click(function () {
    $("#results").text("");
    $("#results").append($results);
});
/**
 * 显示介绍页面
 */
$("#introduce").click(function () {
    $("#first").show();
    $("#second").hide();
});//显示介绍界面

/**
 * 保存当前用户创建的检索器
 * 进入下一步
 */
$("#nextStepOfVSM").click(function () {
    var formulaID = parseInt($("input[name='formula']:checked").val());
    var smoothParam = $("#smoothParam").val();
    var analyzerName = storage.analyzerName;
    var isRemoveStopWord = storage.isRemoveStopWord;

    $.ajax({
        type: "POST",
        url: "vectorSpaceModel/insertResult",
        data: {
            "formulaId": formulaID,
            "smoothParam": smoothParam,
            "analyzerName": analyzerName,
            "isRemoveStopWord": isRemoveStopWord
        },
        dataType: "json",
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        traditional: true,
        success: function (result) {
            console.log(result);
            $(location).attr("href", "/IRforCN/performance.html");
        },
        error: function () {
            alert("后台错误！");
        }
    })//end of ajax


});







