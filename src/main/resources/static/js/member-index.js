var Index={
    rid:"", //当前会议室
    rname:"", //当前会议室名称
    weekNum:0,

    //tab日期 和 时间段数组
    tabTitleArr:[], //表头
    timePeriodArr:["07:00","07:30","08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30","12:00","12:30","13:00","13:30","14:00","14:30","15:00","15:30","16:00","16:30","17:00","17:30","18:00","18:30","19:00"],


    //初始化datetimepicker
    initDatetimepicker:function(){
        var nowDate=Global.dateToFormat(new Date()).substr(0,10)
        document.getElementById("datetime").value=nowDate
        $("#datetime").datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh-CN',//显示中文
            minView:"month",
            autoclose:true
        }).on("hide",function(e){
            //关闭选择时间面板触发
            var date=e.currentTarget.value
            Index.weekNum=0
            console.log(date)
            //获取这一周的选定时间段
            Index.getRoomTime(Index.rid,Index.weekNum)
        });
    },
    //绑定鼠标选择时间事件
    selectTime:function(){
        var contentTab=document.getElementsByClassName("date_content")[0]
        var isMouseDown=false
        var startPosition={} //初始鼠标点击坐标
        
        contentTab.getElementsByTagName("tbody")[0].onmousedown=function(event){
            isMouseDown=true	
            //清空之前选择的$td
         	$(contentTab).find("td").removeClass("mSelected")

            startPosition.X=event.clientX
            startPosition.Y=event.clientY
         
            var $td=$(event.target)
            if($td.html()==""){ //内容为空
                tdOperate($td)
            }
        }

        contentTab.onmouseup=function(event){
            isMouseDown=false
        }
        
        contentTab.onmousemove=function(event){
            if(isMouseDown){
                var $td=$(event.target)

                var position=$td.offset()
                var width=$td.outerWidth() //border-box
                var height=$td.outerHeight()
                //td左上角的X Y
                var tdLeftX=position.left
                // var tdLeftY=position.top
                //td右下角的X Y
                var tdRightX=position.left+width
                // var tdRightY=position.top+height
                if($td.html()==""&&(tdLeftX<startPosition.X&&tdRightX>startPosition.X)){ //内容为空；与开始点击的是同一列
                    tdOperate($td)
                }
            }
        }
        //操作
        function tdOperate($ele){ //当前滑过的$td
            if(!$ele.hasClass("mSelected")&&!$ele.hasClass("alreadySelected")){
                $ele.addClass("mSelected")
            }
            if($ele.hasClass("alreadySelected")){
                isMouseDown=false
            }
        }
    },
    //生成tabTitleArr 更新表头
    refreshTabTitleArr:function(beginDate){ //输入周日"2018-08-14"作为第一天
        var beginStamp=new Date(beginDate).getTime()
        Index.tabTitleArr=[]
        for(var i=0;i<7;i++){
            var stamp=beginStamp+i*86400000
            var date=Global.dateToFormat(new Date(stamp)).substr(5,5)
            Index.tabTitleArr.push(date)
        }
        //更新tab的日期
        Index.tabTitleArr.forEach(function(value,index){
            var valueStr=value.replace(/-/g,"月")+"日"

            var span=document.querySelectorAll(".tab-date")[index]

            span.innerHTML=valueStr
            span.setAttribute("data-date",new Date().getFullYear()+"-"+value)
        })
    },
    //绘制放入时间段 (一个时间段)
    drawTime:function(t1,t2){ //从后台得到的时间 date类型
        var datetime1=Global.dateToFormat(t1) //YYYY-MM-DD hh:mm:ss
        var datetime2=Global.dateToFormat(t2)

        var getDate=datetime1.substr(5,5)
        // console.log(getDate)
        var time1=datetime1.substr(11,5)
        var time2=datetime2.substr(11,5)
        console.log(time1,time2)
        var dateIndex=Index.tabTitleArr.indexOf(getDate)
        var time1Index=Index.timePeriodArr.indexOf(time1)
        var time2Index=Index.timePeriodArr.indexOf(time2)
        //放入时间段
        for(var i=0;i<time2Index-time1Index+1;i++){
            var $td=$(".date_content tr").eq(time1Index+1+i).find("td").eq(dateIndex)
            $td.addClass("alreadySelected")
        }
    },
    //获取一周的选定时间段
    getRoomTime:function(rid,weekNum){
        // var postData={
        //     rid:5,
        //     dateAssign:"2018-09-14",
        //     weekNum:0,
        // }
        var inputDate=document.getElementById("datetime").value
        var postData={
            rid:rid,
            dateAssign:inputDate,
            weekNum:weekNum,
        }
        console.log(postData)
        $.ajax({
            type: "POST",
            url: Global.host + "user/showRoomByAssignWeek",
            data:postData,
            success: function (res) {
                console.log(res)
                var arr=res
                //清空原有绘制
                $(".date_content").find("td").removeClass("alreadySelected")
                //更新表头
                var beginDate=arr[arr.length-1].substr(0,10)
                Index.refreshTabTitleArr(beginDate)
                // 绘制表格
                if(arr.length>1){
                    var arr2=arr.slice(0,arr.length-1)
                    arr2.forEach(function(obj){
                        var t1=new Date(obj.ocbegintime)
                        var t2=new Date(obj.ocendtime)
                        //绘制
                        Index.drawTime(t1,t2) //传入date类型
                    })
                }
                //同步表格标题
                Index.sameTableTitle()
            }
        })
    },
    //获取常用会议室
    getRoomList:function(){
        $.ajax({
            url: Global.host + "room/selectOften",
            success: function (res) {
                console.log(res)
                var arr=res
                if(arr.length>0){
                    var ul=document.getElementById("one")
                    arr.forEach(function(item,index){
                        var li=document.createElement("li")
                        li.setAttribute("data-rid",item.rid)
                        li.style.cursor="pointer"
                        li.innerHTML=item.rname
                        if(index==0){
                            li.classList.add("myLeft_tabCon_act")
                            console.log(item)
                            //默认的会议室 常用会议室 第一个
                            Index.rid=item.rid
                            Index.rname=item.rname
                        }
                        ul.appendChild(li)
                    })
                }
                //同步表格标题
                Index.sameTableTitle()

                //获取这一周的选定时间段
                Index.getRoomTime(Index.rid,Index.weekNum)
            }
        });
    },
    //表格标题和常用会议室名称同步
    sameTableTitle:function(){
        var li=document.querySelector(".myLeft_tabCon_act")
        var caption=document.querySelector(".table_caption")
        caption.innerHTML=li.innerHTML
    },
    //获取当前选择的info
    getSelectedInfo:function(){
        var selectedTd=document.querySelectorAll(".mSelected")
        if(selectedTd.length<=1){
            alert("请选择至少30分钟时间段")
            return false
        }
        var firstTd=selectedTd[0]
        var lastTd=selectedTd[selectedTd.length-1]
        var $firstPare=$(firstTd).closest("tr")
        var $lastPare=$(lastTd).closest("tr")
        //日期
        var index=$firstPare.find("td").index($(firstTd))
        console.log(index)
        var date=document.querySelectorAll(".date_content th")[index].getElementsByClassName("tab-date")[0].getAttribute("data-date")
        console.log(date)
        //时间段
        var startTime=$firstPare.find("td").eq(0).html()
        var endTime=$lastPare.find("td").eq(0).html()

        return{
            date:date,
            startTime:startTime,
            endTime:endTime,

            rid:Index.rid, //会议室id
            rname:Index.rname,
        }
    },
    //跳转到 预订页面
    gotoBook:function(){
        var infoObj=Index.getSelectedInfo()
        if(infoObj){
            localStorage.setItem("infoObj",JSON.stringify(infoObj))
            window.location.href="index_detail.html"
        }
    },

    eventsBind:function(){
        //选择时间段
        Index.selectTime()
        //上一周 下一周
        document.getElementById("preWeekBtn").onclick=function(){
            Index.weekNum--
            Index.getRoomTime(Index.rid,Index.weekNum)
        }
        document.getElementById("nextWeekBtn").onclick=function(){
            Index.weekNum++
            Index.getRoomTime(Index.rid,Index.weekNum)
        }
        //上一周 下一周 end
        //选择会议室
        Global.eventProxy(document.getElementById("one"),"li","click",function(ele){
            $(ele).addClass("myLeft_tabCon_act").siblings().removeClass("myLeft_tabCon_act")
            var rid=ele.getAttribute("data-rid")
            Index.rid=rid
            Index.rname=ele.innerHTML
            Index.getRoomTime(Index.rid,Index.weekNum)
        })
        //点击 我要预订
        document.getElementById("gotoBook").onclick=function () {
            Index.gotoBook()
        }
    },
    init:function(){
        //获取常用会议室
        Index.getRoomList()
        //初始化datetimepicker
        Index.initDatetimepicker()

        //事件绑定
        Index.eventsBind()
    }
}
$(function () {
    Index.init()
});