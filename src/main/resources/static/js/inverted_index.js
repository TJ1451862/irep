var params = {},
    outputList,//存储词项信息
    docList,
    outputstr,
    wordsList,//存储词项在html中元素
    countterm,
    countdoc = 2;

window.onload = function () {//页面加载事件
    params["word"] = "阿坝";
    $.ajax({
        type: "GET",
        url: "../function/getword",
        contentType: "application/json",
        data: params,
        //async:false,
        success: function (r) {
            outputList = JSON.parse(r.outputstr);
            showtermlist(outputList);
            clickf("阿坝");
            console.log("加载成功");
        }
    });
};

function clickTerm(e) {//获取词项内容
    //var el1 = e.target;//当前元素，可修改（能够用此方法获取到他的子元素（就是箭头点击的元素），不能获取他本身的内容）
    var el2 = e.currentTarget;//当前元素，不可修改（能够用此方法获取到他的子元素及能获取他本身的内容）
    console.log(el2.getElementsByClassName("word").item(0));
    var word = el2.getElementsByClassName("word").item(0).innerText;
    clickf(word);

}

function clickf(word) {//获得索引列表的点击事件
    params["word"] = word;
    var tmp = document.getElementById("tbody2");
    var childs = tmp.childNodes;
    for (var x = childs.length - 1; x >= 0; x--) {
        tmp.removeChild(childs[x]);
    }
    $.ajax({
        type: "POST",
        url: "../function/getdoc",
        contentType: "application/x-www-form-urlencoded",
        data: params,
        //async:false,
        success: function (r) {
            docList = JSON.parse(r.outputstr);
            showdoclist(docList);
        }
    });

}

//获取选中的文档id
function clickDoc(e) {
    var el = e.currentTarget;
    var docid = el.getElementsByClassName("doc").item(0).innerText;
    clickd(docid);

}

function clickd(docid) {//获得文档的点击事件
    console.log(docid);
    params["docID"] = docid;
    $.ajax({
        type: "POST",
        url: "../function/showdoc",
        contentType: "application/x-www-form-urlencoded",
        data: params,
        success: function (r) {
            var outputstr = r.outputstr;
            $("#tab-1").html(outputstr);
            //$("#tab-1").display = "show";
        }
    })
}

function showtermlist(outputlist) {//为索引词表格添加内容
    console.log("为索引词表格添加内容");
    var tbody1 = document.getElementById("tbody1");
    var tabletr = new Array(outputlist.length);
    for (var i = 0; i < outputlist.length; i++) {
        tabletr[i] = document.createElement("tr");
        var word = document.createElement("td"),
            inword = document.createTextNode(outputlist[i]["term"]),
            docs = document.createElement("td"),
            indocs = document.createTextNode(outputlist[i]["docs"]),
            tf = document.createElement("td"),
            intf = document.createTextNode(outputlist[i]["tf"]);
        word.appendChild(inword);
        docs.appendChild(indocs);
        tf.appendChild(intf);
        word.className = "word";
        tabletr[i].appendChild(word);
        tabletr[i].appendChild(docs);
        tabletr[i].appendChild(tf);
        tabletr[i].onclick = this.clickTerm;
        tbody1.appendChild(tabletr[i]);
    }
    // wordsList = document.getElementsByClassName("word");
    // wordsList.item(0).parentElement.style.backgroundColor = "#00CCFF";
    $("tr").eq(2).addClass("backcolor");
    countterm = 2
    $("tr").bind("mouseover", function () {//jquery实现背景动态添加颜色
        var index = $("tr").index(this);//获取当前元素在父节点下的下标
        //console.log(index);
        $("tr").eq(index).addClass("color");//eq(index)：选择器选择带有指定下标的元素
        $("tr").bind("mouseleave", function () {
            $("tr").eq(index).removeClass("color");

        })
    })
    $("tr").bind("click", function () {//jquery实现改变行背景颜色
        var index = $("tr").index(this);
        $("tr").eq(index).addClass("backcolor");
        $("tr").eq(countterm).removeClass("backcolor");
        countterm = index;
    })
}

function showdoclist(doclist) {//为索引文档表格添加内容
    console.log("为索引文档表格添加内容");
    var tbody2 = document.getElementById("tbody2");
    var tabletr = new Array(doclist.length);
    for (var i = 0; i < doclist.length; i++) {
        tabletr[i] = document.createElement("tr");
        var docid = document.createElement("td"),
            indocid = document.createTextNode(doclist[i]["docid"]),
            tf = document.createElement("td"),
            intf = document.createTextNode(doclist[i]["tf"]),
            locate = document.createElement("td"),
            inlocate = document.createTextNode(doclist[i]["locate"]);
        docid.appendChild(indocid);
        tf.appendChild(intf);
        locate.appendChild(inlocate);
        docid.className = "doc";
        tabletr[i].appendChild(docid);
        tabletr[i].appendChild(tf);
        tabletr[i].appendChild(locate);
        tabletr[i].className = "doctr";
        tabletr[i].onclick = this.clickDoc;
        tbody2.appendChild(tabletr[i]);
    }
    $("tr").bind("mouseover", function () {//jquery实现背景动态添加颜色
        var index = $("tr").index(this);
        //console.log(index);
        $("tr").eq(index).addClass("color");
        $("tr").bind("mouseleave", function () {
            $("tr").eq(index).removeClass("color");

        })
    })
    $("tr").bind("click", function () {//jquery实现改变行背景颜色
        var index = $("tr").index(this);
        $("tr").eq(countdoc).removeClass("backcolor");
        $("tr").eq(index).addClass("backcolor");
        // $("#tab-1").show();
        countdoc = index;
    })
}