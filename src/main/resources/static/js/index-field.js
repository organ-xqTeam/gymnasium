function dateToFormat(date) { // date类型
    Y = date.getFullYear() + '-';
    M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date
            .getMonth() + 1) +
        '-';
    D = (date.getDate() < 10 ? "0" + date.getDate() : date.getDate()) + ' ';
    h = (date.getHours() < 10 ? "0" + date.getHours() : date.getHours()) +
        ':';
    m = (date.getMinutes() < 10 ? "0" + date.getMinutes() : date
        .getMinutes())+
        ':';
     s = date.getSeconds();
    return Y + M + D+h+m+"00";
}

function timestampToTime(timestamp) {
    var date = new Date(timestamp); // 时间戳为10位需*1000，时间戳为13位的话不需乘1000
    var Y = date.getFullYear() + '-';
    var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date
            .getMonth() + 1) +
        '-';
    var D = (date.getDate()<10?'0'+date.getDate():date.getDate()) + ' ';
    var h = date.getHours() + ':';
    var m = date.getMinutes() + ':';
    var s = date.getSeconds();
    return M + D;
}


var st = [
	//"00:00 ~ 01:00","01:00 ~ 02:00","02:00 ~ 03:00","03:00 ~ 04:00","04:00 ~ 05:00",
    "05:00 ~ 06:00","06:00 ~ 07:00", "07:00 ~ 08:00", "08:00 ~ 09:00", "09:00 ~ 10:00",
    "10:00 ~ 11:00", "11:00 ~ 12:00", "12:00 ~ 13:00", "13:00 ~ 14:00",
    "14:00 ~ 15:00", "15:00 ~ 16:00", "16:00 ~ 17:00", "17:00 ~ 18:00",
    "18:00 ~ 19:00", "19:00 ~ 20:00", "20:00 ~ 21:00", "21:00 ~ 22:00",
    "22:00 ~ 23:00",
    //"23:00 ~ 00:00"
];
var sts = [
	//"00:00", "01:00", "02:00", "03:00", "04:00", 
	"05:00",
	"06:00", "07:00", "08:00", "09:00",
	"10:00", "11:00", "12:00", "13:00",
	"14:00", "15:00", "16:00", "17:00",
	"18:00", "19:00", "20:00", "21:00", "22:00",// "23:00"
	];
var st1 = [//"00", "01", "02", "03", "04",
	"05",
	"06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16",
    "17", "18", "19", "20", "21", "22", //"23"
];

var Field = {
    gid: 1, // 场馆id
    typeArr: [], // 本场馆的 球类 类型 [{stid: 5, gid: 1, stname: "篮球"}]
    // 查询场馆信息
    getGuanInfo() {
    	// alert("gid"+g);
        $.ajax({
            type: "post",
            url: Global.host + "message/selectbygymgid",
            data: {
                gid: Field.gid,
            },
            success: function (data) {
            	$("#ol1").html('');
                console.log(data);
                // update dom
                $("#gname").html(data.gname);
                $("#gstarttime").html(data.gstarttime);
                $("#facilities").html(data.facilities);
                $("#glocation").html(data.glocation);
                $("#gphonenum").html(data.gphonenum);
                if(data.noticeuse != null && data.noticeuse != ''){
                	var n = data.noticeuse.split(",");
                	for(var i = 0; i < n.length; i++){
                		$("#ol1").append("<li>"+(i+1)+"."+n[i]+"</li>")
                	}
                }
            }
        });
    },
    // 获取 所有的场馆中的场地种类 (篮球 羽毛球 。。)
    getType() {
        $.ajax({
            type: "get",
            url: Global.host + "sitetype/selectbysitetype",
            success: function (data) {
                console.log(data)
                let allArr = data
                // 筛选出本场馆的type
                let arr = allArr.filter(function (obj) {
                    return obj.gid == Field.gid
                })
                Field.typeArr = arr
                // update dom
                Field.updateDomType()
                // load(fristdataname);
            }
        });
    },
    // 更新下面的表头
    updateDomType() {
    	$("#select").html("")
        Field.typeArr.forEach(function (obj, index) {
            let $li = $(`
                <li data-stid=${obj.stid}>${obj.stname}</li>
            `)
            if (index == 0) {
                $li.addClass("field_tab_act")
            }
            $("#select").append($li)
        })

        // 查其对应的有多少场地
        Field.getAreasBystid(-1,-1)
    },
    // 获取stid的篮球 有多少场地
    getAreasBystid(stname ,time) {
    	if($(".field_tab_act").length==0){
    		return
    	}
        // 获取当前激活的 type（篮球）
    	if(stname == -1){
    		console.log($(".field_tab_act").attr("data-stid"))
    		 stid = Number($(".field_tab_act").attr("data-stid"))
    		 stname =$(".field_tab_act").text()
    	}else{
    		stid = Number($(".field_tab_act").attr("data-stid"))
    		 stname = $(".field_tab_act").text()
    	}
        // let stname=$(".field_tab_act").text()
// alert("stid"+stid);
        $.ajax({
            type: "post",
            url: Global.host + "site/selectbystids",
            data: {
                stid: stid
            },
            success: function (data) {
                console.log(data)
                let areasArr = data // 场地数组
                // 绘制场地表头
                // Field.drawAreasTab(areasArr)
                // 获取 场地（篮球）的订单信息
                Field.getAreaOrder(areasArr,stname,time)
            }
        });
    },
    // 获取 场地（篮球）的订单信息
    getAreaOrder(areasArr,stname,servenday) {
        // 根据激活的class获取参数
    	let date = $("li.field_date_act .field_date_rili").text().trim()
    	if(servenday == -1){
    		 servenday = new Date().getFullYear() + "-" + date + " 00:00:00"
    	}else{
    		servenday = new Date().getFullYear()+"-"+servenday+" 00:00:00"
    	}
    	if(stname == -1){
    		 stname = $(".field_tab_act").text() // 篮球
    	}
        console.log(servenday)
       $.ajax({
        	type:"post",
        	url:Global.host + "orders/selectbyweek",
        	data:{
        		gid:Field.gid,
        		servenday: servenday
        	},
        	success:function(data){
        		console.log(data)
        		if(Number(data) == 1){ // 开馆
        			$(".dd").show();
        			$.ajax({
        				type: "post",
        				// orders/selectbyordersydhou?stname=篮球&gid=50&servenday=2018-10-12%2000:00:00&num=1&sid=0
        				url: Global.host + "orders/selectbyordersydhou",
        				data: {
        					stname: stname,
        					servenday: servenday,
        					// stname:"篮球",
        					gid:Field.gid,
        					// servenday:"2018-10-12 00:00:00",
        					sid:"0",
        					num:"1"
        					// servenday: "2018-09-09 00:00:00", //测试用
        				},
        				success: function (data) {
        					if(stname === "篮球"){
        						$("#mynote").html('<li><div class="select"></div> <span>已选择</span></li><li><div class="selectno"><div class="selectno_cover"></div></div> <span>可预订</span></li>')
        					}else if(stname === "羽毛球"){
        						$("#mynote").html('<li><div class="select" style="background:url(../images/badminton.jpg) no-repeat;background-size: 100% 100%;"></div> <span>已选择</span></li><li><div class="selectno" style="background:url(../images/badminton.jpg) no-repeat;background-size: 100% 100%;"><div class="selectno_cover"></div></div> <span>可预订</span></li>')
        					}
        					
        					//获取场地是否可预定
        				
        				            	//console.log(fieldDate)
        	        					//获取时间10-12
        	        					let mouth = servenday.substring(5,10)
        	        					console.log(mouth)
        	        					// alert("data"+data);
        	        					let field = areasArr
        	        					console.log(data)
        	        					console.log(field)
        	        					
        	        					// [{"sname":"篮球1号场地","time":["12:00:00","14:46:22"]},{"sname":"篮球2号场地","time":["13:00:00"]}]
        	        					var a1 = "<thead><tr><th></th>";
        	        					for (var i = 0; i < field.length; i++) {
        	        						// a1 += "<th>" + field[i].sname + "</th>";
        	        						a1 += `<th data-sid=${field[i].snumber}>${field[i].sname}</th>`;
        	        					}
        	        					a1 += "</tr></thead>";
        	        					a1 += "<tbody>";
        	        					var body = "";
        	        					/*
        								 * 
        								 * <div class="add_input court_img court_position">
        								 * <div class=""></div> <div
        								 * class="court_position_bg"></div> </div>
        								 * 
        								 * 
        								 */
        	        					// 获取所有 key(时间和体育场id)
        	        					let obj1=data[0]
        								let arr1=obj1[mouth]
        								let arr2=arr1.map(function(obj){
        									let skey=Object.keys(obj)[0]
        									return skey
        								})
        								let arr3=arr2.map(function(str){
        									return str.split("=")[0]
        								})
        								let keyArr=Array.from(new Set(arr3))
        	        					let obj={}
        	        					keyArr.forEach(function(key){
        	        						obj[key]=[]
        	        					})
        	        					arr2.forEach(function(str){
        	        						let key=str.split("=")[0]
        									let value=str.split("=")[1]
        									obj[key].push(value)
        	        					})
        	        					
        	        					//显示当前时间后的可预定实现.
        	        					var nowDate = dateToFormat(new Date());
        	        					console.log(nowDate)
        	        					let indexTime = 0;
        	        					if (nowDate.indexOf(mouth) != -1) {
        	        						console.log(nowDate.substr(11, 2))
        	        						console.log(st1.indexOf(nowDate.substr(11, 2)))
        	        						indexTime = Number(st1.indexOf(nowDate.substr(11, 2)))
        								}
        	        					//var rowindex=bt.indexOf(btime)
        	        					
        	        					// 循环 时间
        	        					for (var j = indexTime; j < st.length; j++) {
        	        						if (j == indexTime) {
        	        							body += "<li></li>";
        	        						}
        	        						body += "<li>" + st[j] + "</li>";
        	        						a1 += "<tr><th></th>";
        	        						// 循环所有体育场
        	        						for (var i1 = 0; i1 < field.length; i1++) {
        	        							var flag = false;
        	        							var fl = false;
        	        							var fl1 = -1;
        	        							
        	        							// 循环所有预定记录中的 体育场id
        	        							for (var i = 0; i < keyArr.length; i++) {
        	        								if (keyArr[i] == field[i1].snumber) {
        	        									fl1 = i;
        	        									break;
        	        								}
        	        							}
        	        							if (fl1 == -1) {
        	        								if ("篮球" === stname) {
        	        									a1 += "<td><div class=\"court_img court_position\">"
        	        										+"<div class='l' data-site='A'></div>"
        	        										+"<div class='l' data-site='B'></div>"
        	                								+"</div></td>";
        				        					}else if("羽毛球" === stname){
        				        						if (field[i1].snumber.indexOf("by") != -1) {
        				        							a1 += "<td><div class=\"court_img court_positionBadminton court_positionBadminton1\">"
        														+"<div class='l' data-site='by'></div>"
        				        								+"</div></td>";
        												}else{
        													a1 += "<td><div class=\"court_img court_position court_positionBadminton\">"
        														+"<div class='l' data-site='C'></div>"
        														+"<div class='l' data-site='D'></div>"
        														+"<div class='l' data-site='E'></div>"
        														+"<div class='l' data-site='F'></div>"
        														+"</div></td>";
        												}
        				        					}else{
        				        						
        				        					}
        	        							} else {
        	        								let sst = "";
        	        								for (var k = 0; k < obj[keyArr[i]].length; k++) {
        	        									var str =obj[keyArr[i]][k].split(" ")[1].substring(0, 2);
        	        									if (str == st1[j]) {
        	        										sst = obj[keyArr[i]][k];
        	        										flag = true;
        	        										break;
        	        									}
        	        								}
        	        								if (flag) {// court_position_bg
        	        									let indeed1 = field[i1].snumber+"="+sst;
        	        									let ssr
        	        									data[0][mouth].forEach(function(obj){
        	        										let skey=Object.keys(obj)[0]
        	        										if (skey == indeed1) {
        	        											ssr = obj[skey]
        													}
        					        					})
        					        					
        					        					if ("篮球" === stname) {
        					        						a1 += "<td><div class=\"add_input court_img court_position \">"
        					        					}else if("羽毛球" === stname){
        					        						if (field[i1].snumber.indexOf("by") != -1) {
        					        							a1 += "<td><div class=\"add_input court_img court_positionBadminton court_positionBadminton1 \">"
        					        						}else{
        					        							
        					        							a1 += "<td><div class=\"add_input court_img court_position court_positionBadminton \">"
        					        						}
        					        					}else{
        					        						
        					        						
        					        					}
        					        					let m1 = false;	
        	        									let m2 = false;	
        	        									let m3 = false;	
        	        									let m4 = false;	
        	        									let m5 = false;	
        	        									let m6 = false;	
        	        									let by = false;	
        					        					ssr.forEach(function(obj){
        					        						if(obj.snumber.indexOf("A") != -1){
        					        							m1 = true;
        					        						}
        					        						if(obj.snumber.indexOf("B") != -1){
        					        							m2 = true;
        					        						}
        					        						if(obj.snumber.indexOf("C") != -1){
        					        							m3 = true;
        					        						}
        					        						if(obj.snumber.indexOf("D") != -1){
        					        							m4 = true;
        					        						}
        					        						if(obj.snumber.indexOf("E") != -1){
        					        							m5 = true;
        					        						}
        					        						if(obj.snumber.indexOf("F") != -1){
        					        							m6 = true;
        					        						}
        					        						if(obj.snumber.indexOf("by") != -1){
        					        							by = true;
        					        						}
        					        					})
        					        					if ("篮球" === stname) {
        					        						if (m1) {
        					        							a1+="<div class='l court_position_bg' data-site='A'></div>"
        					        						}else{
        					        							a1+="<div class='l' data-site='A'></div>"
        					        						}
        					        						if (m2) {
        					        							a1+="<div class='l court_position_bg' data-site='B'></div>"
        					        						}else{
        					        							a1+="<div class='l' data-site='B'></div>"
        					        						}
        												}else if ("羽毛球" === stname) {
        													
        													if (field[i1].snumber.indexOf("by")  != -1) {
        														
        														if (by) {
        															a1+="<div class='l court_position_bg' data-site='by'></div>"
        														}else{
        															a1+="<div class='l' data-site='by'></div>"
        														}
        													}else{
        														
        														if (m3) {
        															a1+="<div class='l court_position_bg' data-site='C'></div>"
        														}else{
        															a1+="<div class='l' data-site='C'></div>"
        														}
        														if (m4) {
        															a1+="<div class='l court_position_bg' data-site='D'></div>"
        														}else{
        															a1+="<div class='l' data-site='D'></div>"
        														}
        														if (m5) {
        															a1+="<div class='l court_position_bg' data-site='E'></div>"
        														}else{
        															a1+="<div class='l' data-site='E'></div>"
        														}
        														if (m6) {
        															a1+="<div class='l court_position_bg' data-site='F'></div>"
        														}else{
        															a1+="<div class='l' data-site='F'></div>"
        														}
        													}
        													
        												}else{
        												}
        	                								a1+="</div></td>";
        	        								} else {
        	        									if ("篮球" === stname) {
        	            									a1 += "<td><div class=\"add_input court_img court_position\">"
        	            										+"<div class='l' data-site='A'></div>"
        	            										+"<div class='l' data-site='B'></div>"
        	                    								+"</div></td>";
        	    			        					}else if("羽毛球" === stname){
        	    			        						
        	    			        						if (field[i1].snumber.indexOf("by") != -1) {
        	    			        							
        	    			        							a1 += "<td><div class=\"add_input court_img court_positionBadminton court_positionBadminton1\">"
        	    			        								+"<div class='l' data-site='by'></div>"
        	    			        								+"</div></td>";
        	    			        						}else{
        	    			        							a1 += "<td><div class=\"add_input court_img court_position court_positionBadminton\">"
        	    			        								+"<div class='l' data-site='C'></div>"
        	    			        								+"<div class='l' data-site='D'></div>"
        	    			        								+"<div class='l' data-site='E'></div>"
        	    			        								+"<div class='l' data-site='F'></div>"
        	    			        								+"</div></td>";
        	    			        						}
        	    			        					}else{
        	    			        					}
        	        								}
        	        							}
        	        						}
        	        						a1 += "</tr>";
        	        					}
        	        					a1 += "</tr></tbody>";
        	        					
        	        					if(field.length == 0){
        	        						var a1 = "<div class='nobook_word ac'>无可预定场地<div>";
        	        						$("#noInfo").html(a1);
        	        						$("#field_th").html("");
        	        						$("#date_content").html("");
        	        					}else{
        	        						$("#date_content").html(a1);
        	            					$("#field_th").html(body);
        	        					}
        	        					// 更改样式
        	        					var hh = field.length + 1;
        	        					var aa = hh * 128;
        	        					$('.date_content').css('width', aa + 'px')
        				            	
        				         
        				}
        			});
        		}else{
        			$(".dd").hide();
        			//alert("当前是闭馆时间！");
   					$("#field_confirm").css("display","block");
   					$("#field_word").text("当前是闭馆时间！")
        		}
        	}
        });
    },
    drawTab() {
        // 日期
        var nowDate = dateToFormat(new Date()).substr(0, 10);
        var dateh = "";
        for (var i = 0; i < 7; i++) {
            var a = new Date().getTime() + (86400000 * (i));
            if (i == 0) {
                dateh += "<li class='field_date_act'><div class=\"field_date_week\"></div><div class=\"field_date_rili font_b\">" +
                    timestampToTime(a) + "</div></li>";
            } else {
                dateh += "<li><div class=\"field_date_week\"></div><div class=\"field_date_rili font_b\">" +
                    timestampToTime(a) + "</div></li>";
            }
        }
        $("#getdate").html(dateh);
        // 左侧时间段
        $("#field_th").append($(`<li></li>`))
        st.forEach(function (value) {
            let $li = $(`<li>${value}</li>`)
            $("#field_th").append($li)
        })
    },
    // 绘制场地表头
    // drawAreasTab(areasArr) {
    // console.log(242342)
    // console.log(areasArr)
    // $("#date_content thead tr").append($("<th></th>"))
    // areasArr.forEach(function (obj) {
    // // console.log(obj)
    // // let $th = $(`<th>${obj.sname}3434</th>`)
    // // console.log(3242342)
    // // $th.attr("data-sid",obj.sid)
    // // $("#date_content thead tr").append($th)
    // })
    // },
    // 确定
    submit() {
    	let stname = $(".field_tab_act").text() // 篮球
			//获取 snumber 对应 sid
    	  $.ajax({
              type: "get",
              url: Global.host + "site/selectBySnumbers",
              data: {
            	  //?stname=%E7%AF%AE%E7%90%83&gid=50
            	  stname:stname,
                  gid: Field.gid,
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
      	  		$selectedDivs.each(function(index,el){ 
      	  			let col=$(this).attr("data-col")
      	  			console.log(col)
      	  			let sidStr=$ths.eq(col).attr("data-sid") //b002
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
      	  			console.log(row)
      	  			let bgtime=new Date().getFullYear()+"-"+$('.field_date_act .field_date_rili').text()+sts[row]+":00"
      	  			let edtime=new Date().getFullYear()+"-"+$('.field_date_act .field_date_rili').text()+sts[row+1]+":00"
      	  			console.log(bgtime,edtime)
      	  			
      	  			//把时间段放入count里对应的对象
      	  			count[rightIndex].value.push([bgtime,edtime])
      	  			
      	  		})
      	  		
      	  		console.log(count)
      	  		
      	  			let datecontent = {
            		  gid :Field.gid,
            		  count:count
            		  }
            	  
            	  var dataclass = $('.book_act');
                  let indexCol
                  for (i = 0; i < dataclass.length; i++) {
                 	 if (i == 0) {
                 		 indexCol =$(dataclass[i]).attr('data-col')
                 	 }
                 	 if ($(dataclass[i]).attr('data-col') != indexCol) {
         				//alert("不能跨场地预定")
	   					$("#field_confirm").css("display","block");
	   					$("#field_word").text("不能跨场地预定！")
         				
         			}
         	         //获取所有选中 的预定
         	         //console.log($(dataclass[i]).attr('data-col') ,$(dataclass[i]).attr('data-row'))
                 	 
                  }
            	  
            	  
//      	  			return
//                  $("#date_content th[data-sid]").each(function(index,ele){
                  	//console.log(index)
                  	//console.log(ele)<th data-sid="b002">北海北</th>
                      // 该场地的时间段
//                      let time=[]
//                      $(`div.book_act[data-col=${index}]`).each(function(){
//                          let row=$(this).attr("data-row")
//                          let site=$(this).attr("data-site")
//                          console.log(row,site)
//                          let timetemp=new Date().getFullYear()+"-"+$('.field_date_act .field_date_rili').text()+sts[row]+":00"
//                      })
//                      console.log(time)
//                      console.log($(this).attr("data-sid"))
//                      let ={
//                          sid:$(this).attr("data-sid"), // 场地id
//                          time:time
//                      }
//                      console.log(obj)
//                      if(time.length!==0){
//                      	areaArr.push(obj)
//                      }
//                  })
//                  console.log(areaArr)
                  // 处理结束
//                  var num = 0;
//                  var num1 = 0;
//                  if(areaArr.length == 0){
//                  	alert('请选择场地！！');
//                  }
//          	       for (var i = 0; i < areaArr.length; i++) {
//          	    	   if (areaArr[i].time.length > 0) {
//          	    		   num++;
//          	    		   num1  = i;
//          	    	   }
//          	       }
//          	       if (num > 1) {
//          				alert('只能选择同一场地！！');
//          				return
//          	       }
//          	       console.log(areaArr[num1].time)
//          	       let arr=areaArr[num1].time.map(function(value){
//          	    	   return Number(value.substr(11,2))
//          // return value.substr(11,2)
//          	       })
//          	       console.log(arr)
//          	       let flagIsConti=true
//          	       arr.forEach(function(num,index){
//          	    	   if(index>=1){
//          	    		   if(num-arr[index-1]!==1){
//          		    		   flagIsConti=false
//          		    	   }
//          	    	   }
//          	       })
//          	       console.log(flagIsConti)
//          	       if(flagIsConti == false){
//          	    	   alert('时间必须连着定！！');
//          	    	   return
//          	       }
          // if (areaArr[num1].time.length == 2) {
          // var a1 = Number(areaArr[num1].time[0].split(" ")[1].substr(0,2));//2018-09-21
          // 08:00:00
          // var a2 = Number(areaArr[num1].time[1].split(" ")[1].substr(0,2));
          // if (Math.abs(a1-a2) == 1) {
          // }else{
          // alert('只能选择2个时间临近的！！');
          // return
          // }
          // }
                  // let sid=areaArr.map(function(obj){
                  // return obj.sid
                  // })
                  // let ostarttime=[areaArr[0].time[0],areaArr[1].time[0]]
                  // let
          		// oendtime=[areaArr[0].time[areaArr[0].time.length-1],areaArr[1].time[areaArr[1].time.length-1]]
//          	    let ostarttime="";
//          	    let oendtime="";
//          	    let sid="";
//                  for (var i = 0; i < areaArr[num1].time.length; i++) {
//                  	sid +=  "&sid="+areaArr[num1].sid;
//                  	ostarttime +="&ostarttime="+areaArr[num1].time[i];
//                  	 var date = new Date(areaArr[num1].time[i]);
//                  	oendtime +="&oendtime="+dateToFormat(new Date(date.getTime()+3600000));
//          		}
                  
          // let sid="&sid="+areaArr.map(function(obj){
          // return obj.sid
          // }).join("&sid=")
//                  console.log(sid)
                  
                  
                 // let
          		// ostarttime="&ostarttime="+[areaArr[0].time[0],areaArr[1].time[0]].join("&ostarttime=")
//                  console.log(ostarttime)
                 // let
          		// oendtime="&oendtime="+[areaArr[0].time[areaArr[0].time.length-1],areaArr[1].time[areaArr[1].time.length-1]].join("&oendtime=")
//                  console.log(oendtime)
//                  let str=sid+ostarttime+oendtime+"&gid="+Field.gid
//                  str=str.substr(1,str.length-1)
//                  console.log(str)
                  
                  // let postData = {
                  // sid: sid, //场地id
                  // ostarttime: ostarttime, //预定开始时间
                  // oendtime: oendtime, //预定结束时间
                  // }
                   console.log(datecontent)
                   let map = datecontent
                   if (count.length == 0) {
   					//alert("请选择预定的场地")
   					$("#field_confirm").css("display","block");
   					$("#field_word").text("请选择预定的场地")
                    }else{
	                  $.ajax({
	                      type: "post",
	                      url: Global.host + "orders/insertorderss",
	                      //dataType : 'jsonp',\
	                      //traditional:true,
	                      data:JSON.stringify(map),
	                      contentType:"application/json; charset=utf-8",
	                      success: function (res) {
	                          console.log(res.count)
	                         if(res.count == 'login'){
	                          	//alert("请先登录！");
	    	   					$("#field_confirm").css("display","block");
	    	   					$("#field_word").text("请先登录！")
	                          }else if(res.count == 'There are orders pending'){
	                          	//alert("请先完成没完成的预定单再来完成！");
	    	   					$("#field_confirm").css("display","block");
	    	   					$("#field_word").text("请先完成没完成的预定单再来完成！")
	                          }else if(res.count == '1'){
	                          	//alert("羽毛球场地对应的篮球场地已经预定了！");
	    	   					$("#field_confirm").css("display","block");
	    	   					$("#field_word").text("羽毛球场地对应的篮球场地已经预定了！")
	                          }else if(res.count == 's'){
	                          	//alert("抱歉，暂不可选，请选择其他场地！");
	    	   					$("#field_confirm").css("display","block");
	    	   					$("#field_word").text("抱歉，暂不可选，请选择其他场地！")
	                          }else if(res.count == '2'){
	                          	//alert("羽毛球场地已经预定了!");
	    	   					$("#field_confirm").css("display","block");
	    	   					$("#field_word").text("羽毛球场地已经预定了！")
	                          }else if(res.count == '3'){
	                          	//alert("篮球场地对应的羽毛球场地已经预定了！");
	    	   					$("#field_confirm").css("display","block");
	    	   					$("#field_word").text("篮球场地对应的羽毛球场地已经预定了！")
	                          }else if(res.count == '4'){
	                          	//alert("篮球场地已经预定不可预定！");
	    	   					$("#field_confirm").css("display","block");
	    	   					$("#field_word").text("篮球场地已经预定不可预定！")
	                          }else if(res.count == '99'){
	                          	//alert("请选择大于当前时间的场地！");
	    	   					$("#field_confirm").css("display","block");
	    	   					$("#field_word").text("请选择大于当前时间的场地！")
	                          }else if(res.count == 'not.site'){
	                        	  //alert("该场地没有添加场地！");
		    	   					$("#field_confirm").css("display","block");
		    	   					$("#field_word").text("该场地没有添加场地！")
	                          }else if(res.count == "orders.not.null"){
	                        	  //alert("已经预定不能预定！");
		    	   					$("#field_confirm").css("display","block");
		    	   					$("#field_word").text("已经预定不能预定！")
	                          }else if(res.count == "pstate"){
	                        	  //alert("当前场地不可预定！");
		    	   					$("#field_confirm").css("display","block");
		    	   					$("#field_word").text("当前场地不可预定！")
	                          }else{
	                        	  //alert("恭喜您，预定成功！");
		    	   					$("#field_confirm").css("display","block");
		    	   					$("#field_word").text("恭喜您，预定成功！")
	                          }
//	                          window.location.reload();
	                      }
	                  });
                    }
            	  
              }
          });
    	
    	
         
        
    },
    eventsBind() {
        // 点击 篮球 羽毛球 type
    	$("#select").undelegate()
        $("#select").delegate("li", "click", function () {
            // 更改样式
            $(this).addClass('field_tab_act').siblings().removeClass('field_tab_act')
            console.log($(this).text());
            console.log($(".field_date_act .field_date_rili").text());
            
            Field.getAreasBystid($(this).text(),$(".field_date_act .field_date_rili").text())
        })
        // 点击日期
        $("#getdate").undelegate()
        $("#getdate").delegate("li", "click", function () {
            // 更改样式
            $(this).closest("li").addClass('field_date_act').siblings().removeClass('field_date_act')
            
           // console.log($(this).text());
           // console.log($(".field_tab_act").text());
            Field.getAreasBystid($(".field_tab_act").text(),$(this).text())
        })
        // 预定框点击事件
        $("#date_content").undelegate();
        $("#date_content").delegate(".l", "click", function () {
            // 样式
            if ($(this).hasClass("book_act")) {
                $(this).removeClass('book_act').removeAttr("data-col").removeAttr("data-row")
            } else {
                // no_book
                if ($(this).hasClass("court_position_bg")) {

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
        // 点击确认预定button
      // $("#Affirmatory").undelegate();
        document.getElementById("Affirmatory").onclick=function(){
        	Field.submit();
        }
// $("#Affirmatory").click(function () {
// //window.location.reload();
// });
    },
    init() {
        // 查询场馆信息
        Field.getGuanInfo()
        // 获取 所有的场馆中的场地种类 (篮球 羽毛球 。。)
        Field.getType()

        // 初始绘制表格
        Field.drawTab()

        // 事件绑定
        Field.eventsBind()
    }
}
$(function () {
	// 查询体育馆
    $.ajax({
    	type:"get",
    	url:Global.host + "message/selectbycode",
    	success:function(data){
    		if(data.length>0){
    			console.log(data)
        		var gid = document.getElementById("gid");
    			$.each(data,function(){
    				gid.add(new Option(this.gname,this.gid));
    			});

    			$(gid).change(function(){
    				var gid=Number($(this).val())
    				Field.gid=gid
    				Field.init();
    			})
    			
    			Field.gid=Number($(gid).val())
    			Field.init();
    		}
    	}
    });
})

function getSidBySnumber(snumber,arr){ //str
	let mysid
	arr.forEach(function(obj){
		if(obj.snumber==snumber){
			mysid=obj.sid
		}
	})
	return mysid
}

$(document).ready(function(){
    $(document).scroll(function(){
        var top = $("#mytop").offset().top;
        var fixedTop = top - $(document).scrollTop()
        /*console.log("距离顶部",fixedTop)*/
        if(fixedTop <= 108 ){
        	$("#fixedTop").addClass("fixedTop")
        	$("#kong").addClass("kong")
        }else if(fixedTop > 108){
        	$("#fixedTop").removeClass("fixedTop")
        	$("#kong").removeClass("kong")
        }
        
    });
});