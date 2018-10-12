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
            url: Global.host + "site/selectbystids",
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
                        var $option = $(
                    		'<option value="' + obj.sid + '" data-rvalue="' + obj.sname + '" data-snumber="'+obj.snumber+'">' + 
                    			obj.sname + 
                    		'</option>'
                		)
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
        
        
    	let a1 = "";
        if ("篮球" === stname) {
    		a1 += "<div class=\"add_input court_img court_position\">"
    			+"<div class='l' data-site='A'></div>"
    			+"<div class='l' data-site='B'></div>"
    			+"</div>";
    	}else if("羽毛球" === stname){
    		a1 += "<div class=\"add_input court_img court_position court_positionBadminton\">"
    			+"<div class='l' data-site='C'></div>"
    			+"<div class='l' data-site='D'></div>"
    			+"<div class='l' data-site='E'></div>"
    			+"<div class='l' data-site='F'></div>"
    			+"</div>";
    	}else{
    		a1 += "<div class=\"add_input court_img court_positionBadminton1\">"
    			+"<div class='l' data-site='by'></div>"
    			+"</div>";
    	}
        $(".lo").html(a1);
        
        
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
        $(".l").removeClass("select")
        $.ajax({
            type: "post",
            url: Global.host + "orders/selectbyordersydhou",
            data: postData,
            success: function (data) {
            	/*alert("data"+data);*/
            	//[{"10-12":[{"b002=2018-10-12 12:00:00":[{"sname":"北海北","snumber":"b002-A","sid":115},{"sname":"北海北","snumber":"b002-B","sid":116}]},{"b002=2018-10-12 13:00:00":[{"sname":"北海北","snumber":"b002-A","sid":115},{"sname":"北海北","snumber":"b002-B","sid":116}]},{"b002=2018-10-12 14:00:00":[{"sname":"北海北","snumber":"b002-A","sid":115},{"sname":"北海北","snumber":"b002-B","sid":116}]}]},{"10.13":[]},{"10.14":[]},{"10.15":[]},{"10.16":[]},{"10.17":[]},{"10.18":[]}]
                console.log(data)
                var isEmpty=true
                data.forEach(function (obj, colindex) {
                	console.log(obj)
                    //日期表头
                	let date=Object.keys(obj)[0]
                	console.log(date)
//                    var date = obj.time.replace(".", "-")
                    $("#time th").eq(colindex + 1).html(date)
                    let arr =  obj[date].map(function(obj){
                    	let date=Object.keys(obj)[0]
                    	return date
                    })
                    
//                    setTimeout(function(){
                    	let objValueArr=obj[date] //10-12: Array(6) 数组
                        objValueArr.forEach(function(timeObj,timeindex){
                        	console.log(timeObj)
                        	let timeKey=arr[timeindex] //time key "b001=2018-10-12 07:00:00"
                        	//这个date被预定的时间段
                        	let btime=timeKey.split("=")[1].substr(11,5)
                        	var rowindex=bt.indexOf(btime)
                        	console.log(rowindex,colindex)
                        	
                        	//找到这个td
                        	var $td=$("#main tr").eq(rowindex).find("td").eq(colindex+1)
                        	console.log($td)
//                        	$td.addClass("select")
                        	//这个时间段选中的场地数组
                        	let selectedAreaArr=timeObj[timeKey]
                        	console.log(selectedAreaArr)
                        	selectedAreaArr.forEach(function(areaObj){
                        		let site=areaObj.snumber.substr(areaObj.snumber.length-1,1)
                        		console.log(site)
                        		console.log($td[0])
                        		console.log($td[0].getElementsByClassName("l")[0])
//                        		debugger
                        		$td.find("div.l").addClass("select")
                        	})
                        })
//                    },5000)
                    
                    
                    
                    console.log(arr)
                    //这一天的预定时间段
//                  Index.htmlLoad(date,data,stname);
                    if (arr.length > 0) {
                        isEmpty=false
//                        console.log(stname) //篮球
//                        arr.forEach(function (timeStr) {
//                        	let btime=timeStr.split("=")[1].substr(11,5)
//                        	var rowindex=bt.indexOf(btime)
//                        	console.log(rowindex)
//                            if(rowindex>-1){
//                            	console.log($("#main tr").eq(rowindex).find("td").eq(colindex+1))
//                            	let $td=$("#main tr").eq(rowindex).find("td").eq(colindex+1)
//                            	if(stname){
//                            		
//                            	}else{
//                            		
//                            	}
//                                $("#main tr").eq(rowindex).find("td").eq(colindex+1).addClass("select")
//                            }
//                        })
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
        
         // 预定框点击事件
        $("#main").undelegate();
        $("#main").delegate(".l", "click", function () {
            // 样式
            if ($(this).hasClass("book_act")) {
                $(this).removeClass('book_act').removeAttr("data-col").removeAttr("data-row")
            } else {
                // no_book
                if ($(this).hasClass("select")) {

                } else {
                    $(this).addClass('book_act')
                }
            }

            var $tr = $(this).closest("tr")
            // 获取第几列
// var colindex = $tr.find("td").index($(this))
            var colindex = $tr.find("td").index($(this).closest("td"))
             //console.log(colindex)
            // 获取第几行
            var rowindex = $tr.closest("tbody").find("tr").index($tr)
            // console.log(rowindex)

            // 保存 行 列
            $(this).attr("data-col", colindex).attr("data-row", rowindex)
        })
        
        
        document.getElementById("Affirmatory").onclick=function(){
        	Index.submit();
        }
        
    },
    submit() {
    	let  stname =$("#stid option:selected").attr("data-rvalue") //"篮球"
    	let  gid=$("#gid option:selected").attr("value");
			//获取 snumber 对应 sid
    	  $.ajax({
              type: "get",
              url: Global.host + "site/selectBySnumbers",
              data: {
            	  //?stname=%E7%AF%AE%E7%90%83&gid=50
            	  stname:stname,
                  gid: gid,
              },
              success: function (res) {
            	  console.log(res)
            	  let sidcount=res //数组 
            	 //[{"sid":113,"stid":92,"srsnumber":null,"sname":"南山南","snumber":"b001-A","sprice":23.0,"pstate":1,"pstatus":1,"sremark":"","time":"2018-09-28T14:39:28.000+0000","strnumber":null,"strstname":null,"strgname":null},{"sid":114,"stid":92,"srsnumber":null,"sname":"南山南","snumber":"b001-B","sprice":23.0,"pstate":1,"pstatus":1,"sremark":"","time":"2018-09-28T14:39:28.000+0000","strnumber":null,"strstname":null,"strgname":null},{"sid":115,"stid":92,"srsnumber":null,"sname":"北海北","snumber":"b002-A","sprice":23.0,"pstate":1,"pstatus":1,"sremark":"","time":"2018-09-28T14:40:04.000+0000","strnumber":null,"strstname":null,"strgname":null},{"sid":116,"stid":92,"srsnumber":null,"sname":"北海北","snumber":"b002-B","sprice":23.0,"pstate":1,"pstatus":1,"sremark":"","time":"2018-09-28T14:40:04.000+0000","strnumber":null,"strstname":null,"strgname":null}]
            	  let areaArr=[]
            	  
            	  let count=[]
            	  let selectedSid=[] //sid的数组
            	  let $ths=$("th[data-sid]") //表头数组
      	  		console.log($("div.book_act[data-site]"))
      	  		let $selectedDivs=$("div.book_act[data-site]") //所有被选中的div
      	  		var sidStr=$("#sid option:selected").attr("data-snumber") //b002 
      	  		$selectedDivs.each(function(index,el){ 
      	  			let col=$(this).attr("data-col")
      	  			console.log(col)
//      	  			let sidStr=$ths.eq(col).attr("data-sid") //b002
      	  			let sidStrReal=sidStr+"-"+$(this).attr("data-site") //b002-A //snumber
      	  			console.log(sidStrReal)
      	  			let sid=getSidBySnumber(sidStrReal,sidcount)
      	  			console.log(sid)
      	  			//保存自己的sid
      	  			$(this).attr("data-mySid",sid)
      	  			if(selectedSid.indexOf(sid)==-1){
      	  				selectedSid.push(sid)
      	  			}
      	  		})
      	  		console.log(selectedSid)
      	  		selectedSid.forEach(function(sid){
      	  			let obj={
      	  				sid:sid,
      	  				value:[]
      	  			}
      	  			count.push(obj)
      	  		})
      	  		console.log(count)
      	  		//在循环一次所有的div
      	  		$selectedDivs.each(function(index,el){ 
      	  			let mysid=$(this).attr("data-mySid") //自己的sid
      	  			//找到count里的对象的index
      	  			let rightIndex
      	  			count.forEach(function(obj,index2){
      	  				if(obj.sid==mysid){
      	  					rightIndex=index2
      	  				}
      	  			})
      	  			console.log(rightIndex)
      	  			
      	  			//找到自己的时间段
      	  			let row=Number($(this).attr("data-row"))
      	  			let col=Number($(this).attr("data-col"))
      	  			
      	  			$("#time th").eq(col).text()
      	  			
      	  			console.log(row)
      	  			let bgtime=new Date().getFullYear()+"-"+$("#time th").eq(col).text()+" "+bt[row]+":00"
      	  			let edtime=new Date().getFullYear()+"-"+$("#time th").eq(col).text()+" "+bt[row+1]+":00"
      	  			console.log(bgtime,edtime)
      	  			
      	  			//把时间段放入count里对应的对象
      	  			count[rightIndex].value.push([bgtime,edtime])
      	  			
      	  		})
      	  		
      	  		console.log(count)
      	  		
      	  			let datecontent = {
            		  gid :Number(gid),
            		  count:count
            		  }
                   console.log(datecontent)
                   let map = datecontent
                  $.ajax({
                      type: "post",
                      url: Global.host + "orders/insertorderss",
                      //dataType : 'jsonp',\
                      //traditional:true,
                      data:JSON.stringify(map),
                      contentType:"application/json; charset=utf-8",
                      success: function (res) {
                          console.log(res)
                          if(res == 'login'){
                          	alert("请先登录！");
                          }else if(res == 'There are orders pending'){
                          	alert("请先完成没完成的预定单再来完成！");
                          }else if(res == '1'){
                          	alert("羽毛球场地对应的篮球场地已经预定了!");
                          }else if(res == 's'){
                          	alert("抱歉，暂不可选，请选择其他场地!");
                          }else if(res == '2'){
                          	alert("羽毛球场地已经预定了!");
                          }else if(res == '3'){
                          	alert("篮球场地对应的羽毛球场地已经预定了!");
                          }else if(res == '4'){
                          	alert("篮球场地已经预定不可预定！");
                          }else if(res == '99'){
                          	alert("请选择大于当前时间的场地！");
                          }else{
                          	alert("恭喜您，预定成功！");
                          }
                          window.location.reload();
                      }
                  });
            	  
              }
          });
        
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
    });
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
function getSidBySnumber(snumber,arr){ //str
	let mysid
	arr.forEach(function(obj){
		if(obj.snumber==snumber){
			mysid=obj.sid
		}
	})
	return mysid
}