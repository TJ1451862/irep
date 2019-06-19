var vm = new Vue({
    el:'#rrapp',
    data:{
        inputstr: "",
        outputstr:"",
        inputList:{},
        outputList:[],
        docList:[],
        trname:""
    },
    created: function(){
        params["word"]="阿坝";
        $.ajax({
            type:"GET",
            url:"../function/getword",
            contentType:"application/json",
            data:params,
            success:function (r) {
                vm.outputList = JSON.parse(r.outputstr);

            }
        });
        this._clickf("阿坝")
    },
    methods:{
        //获得选中的词项
        clickTerm:function(e){
            //var el1 = e.target;//当前元素，可修改（能够用此方法获取到他的子元素（就是箭头点击的元素），不能获取他本身的内容）
            var el2 = e.currentTarget;//当前元素，不可修改（能够用此方法获取到他的子元素及能获取他本身的内容）
            var word = el2.getElementsByClassName("word").item(0).innerText;
            this._clickf(word)

        },
        //点击事件
        _clickf:function (word) {
            params["word"] = word;
            $.ajax({
                type:"POST",
                url:"../function/getdoc",
                contentType:"application/x-www-form-urlencoded",
                data:params,
                success:function (r) {
                    vm.docList = JSON.parse(r.outputstr);
                    //console.log(vm.docList);
                }
            });
        },
        //获得选中的文档
        clickDoc:function(e){
            var el = e.currentTarget;
            var docid = el.getElementsByClassName("doc").item(0).innerText;
            this._clickd(docid);

        },
        //文档点击事件
        _clickd:function(docid){
            params["docID"]=docid;
            $.ajax({
                type:"POST",
                url:"../function/showdoc",
                contentType:"application/x-www-form-urlencoded",
                data:params,
                success:function (r) {
                    vm.outputstr = r.outputstr;
                    $("#tab-1").html(vm.outputstr);
                }
            })

        },
        //选中列表高亮
        selected:function (trname) {
            this.trname = trname;
        }
    }

})