var st = [
	"00:00 - 01:00","01:00 - 02:00","02:00 - 03:00","03:00 - 04:00","04:00 - 05:00",
    "05:00 - 06:00","06:00 - 07:00", "07:00 - 08:00", "08:00 - 09:00", "09:00 - 10:00",
    "10:00 - 11:00", "11:00 - 12:00", "12:00 - 13:00", "13:00 - 14:00",
    "14:00 - 15:00", "15:00 - 16:00", "16:00 - 17:00", "17:00 - 18:00",
    "18:00 - 19:00", "19:00 - 20:00", "20:00 - 21:00", "21:00 - 22:00",
    "22:00 - 23:00", "23:00 - 00:00"
];
var bt = [
	"00:00","01:00","02:00","03:00","04:00","05:00",
    "06:00", "07:00", "08:00", "09:00",
    "10:00", "11:00", "12:00", "13:00",
    "14:00", "15:00", "16:00", "17:00",
    "18:00", "19:00", "20:00", "21:00"
    , "22:00", "23:00"
]
var st1 = [
	"0","1","2","3", "4","5", 
    "6", "7", "8", "9",
    "10", "11", "12", "13",
    "14", "15", "16", "17",
    "18", "19", "20", "21"
    , "22", "23"
];

var Index = {
    allType: [],


    //获取 所有的场馆中的场地种类 (篮球 羽毛球 。。)
    getType: function () {
        $.ajax({
            url: Global.host + "sitetype/selectbysitetype",
            success: function (res) {
                Index.allType = res

                Index.getGuanSelect()
            }
        });
    },
    //场馆select
    getGuanSelect: function () {
        $.ajax({
            url: Global.host + "message/selectbygym",
            data: {
                sname: null
            },
            success: function (data) {
                console.log(data)
                for (var i = 0; i < data.length; i++) {
                    var $option = $('<option value="' + data[i].gid + '">' + data[i].gname + '</option>')
                    $("#gid").append($option);
                }
                Index.getAreaType(data[0].gid)
            }
        });
    },
    //根据gid获取该场馆的 场地类型
    getAreaType: function (gid) {
        var arr = Index.allType.filter(function (obj) {
            return obj.gid == Number(gid)
        })
        console.log(arr)
        //更新场地类型select
        $("#stid").html("")
        if (arr.length == 0) {
            // $("#stid").append($('<option value="0">请选择场地类型</option>'))
        } else {
            arr.forEach(function (obj) {
                var $option = $('<option value="' + obj.stid + '" data-rvalue="'+obj.stname+'">' + obj.stname + '</option>')
                $("#stid").append($option)
            })
            Index.getArea(arr[0].stid)
        }
    },
    //根据stid获取场地
    getArea: function (stid) {
        $.ajax({
            type: "post",
            url: Global.host + "site/selectbystid",
            data: {
                stid: Number(stid)
            },
            success: function (data) {
                console.log(data)
                $("#sid").html("")
                if (data.length == 0) {
                    alert("当前条件无场地")
                    $("#sid").append($('<option value="0">请选择场地</option>'))
                } else {
                    var arr = data
                    var $option1 = $('<option value="0">请选择场地</option>')
                	$("#sid").append($option1)
                    arr.forEach(function (obj) {
                        var $option = $('<option value="' + obj.sid + '" data-rvalue="' + obj.sname + '">' + obj.sname + '</option>')
                        $("#sid").append($option)
                    })
                }
                
                Index.getSevenDayData()
            }
        });
    },
    //显示一周的预定表格
    getSevenDayData: function () {
        if($("#sid").val()=="0"){
            alert("请选择场地")
        }
        var stname =$("#stid option:selected").attr("data-rvalue") //"篮球"
        var sid =$("#sid option:selected").attr("value") //"篮球"
        var sname=$("#sid option:selected").attr("data-rvalue") //"篮球一号场地"
        var gid=$("#gid option:selected").attr("value");
        var date=$("#datetime").val()+" 00:00:00"
        $.ajax({
            type: "post",
            url: Global.host + "orders/selectbycount",
            data:{
            	time:date
            },
            success: function (data) {
                console.log(data)
                if (!data.people) {
                    $("#people").html(0);
                } else {
                    $("#people").html(data.people);
                }
                if (!data.site) {
                    $("#site").html(0);
                } else {
                    $("#site").html(data.site);
                }
            }
        });
        var postData = {
            stname:stname,
            sname: sname,
            servenday: date,
            sid:sid,
            gid:gid,
            num:7
        }
        console.log(postData)
        $("td").removeClass("select")
        $.ajax({
            type: "post",
            url: Global.host + "orders/selectbyordersydhou",
            data: postData,
            success: function (data) {
            	/*alert("data"+data);*/
                console.log(data)
                var isEmpty=true
                data.forEach(function (obj, colindex) {
                    //日期表头
                    var date = obj.time.replace(".", "-")
                    $("#time th").eq(colindex + 1).html(date)
                    //这一天的预定时间段
                    if (obj.value.length > 0) {
                        isEmpty=false
                        obj.value.forEach(function (begintime) {
                            var btime = begintime.substr(0, 5)
                             console.log(begintime+"\t"+btime)
                            var rowindex=bt.indexOf(btime)
                            console.log(rowindex)
                            
                            if(rowindex>-1){
                                $("#main tr").eq(rowindex).find("td").eq(colindex+1).addClass("select")
                            }
                        })
                    }
                })
                if(isEmpty){
                    alert("当前场地和日期没有预定")
                }
            }
        });
    },
    eventsBind: function () {
        //select change事件
        //更改场馆select
        $("#gid").change(function () {
            console.log(this.value)
            var gid = this.value
            Index.getAreaType(gid)
        })
        //更改场地类型select
        $("#stid").change(function () {
            console.log(this.value)
            var stid = this.value
            Index.getArea(stid)
        })
        //select change事件 end
        //点击确定
        $("#button").click(function(){
            Index.getSevenDayData()
        })
    },
    init: function () {
        Index.getType()

        //Index.getSevenDayData()

        Index.eventsBind()
    }
}

$(function () {
    Index.init()
    $.ajax({
        type: "post",
        url: Global.host + "site/selectbycount",
        data: {
            key: 'today',
            value: '1',
        },
        success: function (data) {
        	console.log(data)
//        	alert(data);
//        	console.log("----"+data);
        	$("#one").html('');
        	for(var i = 0; i < data.length; i++){
    			$("#one").append("<li>"+data[i].sname+"</li>");
        	}
        	if(data.length == 1){
        		$("#one").append("<li>暂无</li>");
        		$("#one").append("<li>暂无</li>");
        	}else if(data.length == 2){
        		$("#one").append("<li>暂无</li>");
        	}else if(data.length != 3){
        		$("#one").append("<li>暂无</li>");
        	}
        }
    });
    $("#today").click(function () {
    	$.ajax({
            type: "post",
            url: Global.host + "site/selectbycount",
            data: {
                key: 'today',
                value: '1',
            },
            success: function (data) {
            	console.log(data)
//            	alert(data);
//            	console.log("----"+data);
            	$("#one").html('');
            	for(var i = 0; i < data.length; i++){
        			$("#one").append("<li>"+data[i].sname+"</li>");
            	}
            	if(data.length == 1){
            		$("#one").append("<li>暂无</li>");
            		$("#one").append("<li>暂无</li>");
            	}else if(data.length == 2){
            		$("#one").append("<li>暂无</li>");
            	}else if(data.length != 3){
            		$("#one").append("<li>暂无</li>");
            	}
            }
        });
    });
    $("#month").click(function () {
        $.ajax({
            type: "post",
            url: Global.host + "site/selectbycount",
            data: {
                key: 'month',
                value: '1',
            },
            success: function (data) {
            	$("#two").html('');
            	for(var i = 0; i < data.length; i++){
        			$("#two").append("<li>"+data[i].sname+"</li>");
            	}
            	if(data.length == 1){
            		$("#two").append("<li>暂无</li>");
            		$("#two").append("<li>暂无</li>");
            	}else if(data.length == 2){
            		$("#two").append("<li>暂无</li>");
            	}else if(data.length != 3){
            		$("#two").append("<li>暂无</li>");
            	}
            }
        });
    });

    var nowDate = dateToFormat(new Date()).substr(0, 10)
    document.getElementById("datetime").value = nowDate
    //时间改变列表日期改变
    $("#datetime").datetimepicker({
        format: 'yyyy-mm-dd',
        language: 'zh-CN', //显示中文
        minView: "month",
        autoclose: true
        // locale: moment.locale('zh-cn')
    }).on('changeDate', function (ev) {
        //alert("dsdasd"+ev);
        /* $.ajax({
        	type:"post",
        	url:url+"time/calculatetime",
        	data:{
        		date:ev
        	},
        	success:function(data){
        		var str = "<th>时间</th>";
        		for(var i = 0; i < data.length; i++){
        			str += "<th>"+data[i]+"</th>";
        		}
        		$("#time").html(str);
        	}
        }); */
    });
    
    //var date=$("#datetime").val()+" 00:00:00";
    //统计预定人数
    $.ajax({
        type: "post",
        url: Global.host + "orders/selectbycount",
        data:{
        	time:nowDate
        },
        success: function (data) {
            console.log(data)
            if (!data.people) {
                $("#people").html(0);
            } else {
                $("#people").html(data.people);
            }
            if (!data.site) {
                $("#site").html(0);
            } else {
                $("#site").html(data.site);
            }
        }
    });
});

function dateToFormat(date) { //date类型
    Y = date.getFullYear() + '-';
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date
            .getMonth() + 1) +
        '-';
    D = (date.getDate() < 10 ? "0" + date.getDate() : date.getDate());
    h = (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) +
        ':';
    m = (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
        .getMinutes());
    // s = date.getSeconds();
    return Y + M + D;
}