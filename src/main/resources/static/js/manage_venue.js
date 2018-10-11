var Venue = {

    //获取场馆
    getGuan: function () {
        $.ajax({
            type: "get",
            url: Global.host + "message/selectbygym",
            data: {
                sname: $("#sname").val()
            },
            success: function (data) {
            	$("#showgm").html('');
                for (var i = 0; i < data.length; i++) {
                    var $tr = $(`
                                <tr>
                                    <td>${i+1}</td>
                                    <td>${data[i].gname}</td>
                                    <td>${data[i].gnumber}</td>
                                    <td>${data[i].pstatus}</td>
                                    <td class="list_table_btn">
                                    	<button class="normal_btn change buttonstate" onclick="updatestate(${data[i].gid})">变更预定状态</button>
                                        <button class="normal_btn"  onclick="sitemanage(${data[i].gid})">场地管理</button>
                                        <button class="normal_btn" onclick="editor(${data[i].gid},0)" data-toggle="modal" data-target="#myupd">编辑</button>
			                    		<button class="normal_btn" onclick="editor(${data[i].gid},1)" data-toggle="modal" data-target="#myupd">查看</button>
                                        <button class="disabled_btn" onclick="deletegid(${data[i].gid})">删除</button>
                                    </td>
                                </tr>		
                            `)
                    $("#showgm").append($tr);
                }
            }
        });
    },
    //根据场馆名称搜索
    // getGuanByName: function () {
    //     $.ajax({
    //         type: "post",
    //         url: url + "message/selectbygym",
    //         data: {
    //             sname: $("#sname").val()
    //         },
    //         success: function (data) {
    //             $("#showgm").html("");
    //             for (var i = 0; i < data.length; i++) {
    //                 var $tr = $(`
	// 								<tr>
	// 								<td>${i+1}</td>
	// 								<td>${data[i].gname}</td>
	// 								<td>${data[i].gnumber}</td>
	// 								<td>${data[i].gstarttime}</td>
	// 								<td>${data[i].pstatus}</td>
	// 								<td class="list_table_btn">
	// 									<button class="normal_btn"  onclick="sitemanage(${data[i].gid})">场地管理</button>
	//								    <button class="normal_btn" onclick="editor(${data[i].gid})" data-toggle="modal" data-target="#myupd">编辑</button>
	//									<button class="normal_btn" onclick="chakan(${data[i].gid})">查看</button>
	//								    <button class="disabled_btn" onclick="deletegid(${data[i].gid})">删除</button>
	// 								</td>
	// 							</tr>	
	// 							</tr>		
	// 						`)
    //                 $("#showgm").append($tr);
    //             }
    //         }
    //     });
    // },
    update: function () {
        var stnameArr = [];
        $("#ul1 li").each(function () {
            stnameArr.push($(this).text());
        });
        var stname = stnameArr.join(",")

        var checkArr = [];
        var obj = document.getElementsByName("gclosedtime");
        for (k in obj) {
            if (obj[k].checked) {
                checkArr.push(obj[k].value);
            }
        }
        var check = checkArr.join(",")

        var noticeuse = '';
        var noticeuseArr = [];
        $("#ol1 li").each(function () {
            noticeuseArr.push($(this).text());
        });
        var noticeuse = noticeuseArr.join(",")
        if($("#gname").val() == ''){
        	alert("体育馆名称不能为空！");
        	return false;
        }
        if($("#glocation").val() == ''){
        	alert("体育馆地址不能为空！");
        	return false;
        }
        if($("#facilities").val() == ''){
        	alert("场馆设施不能为空！");
        	return false;
        }
        var postData={
            stname: stname,
            gid: $("#gid").val(),
            gname: $("#gname").val(),
            glocation: $("#glocation").val(),
            gphonenum: $("#gphonenum").val(),
            gstarttime: 1,
            gendtime: 1,
            gclosedtime: check,
            facilities: $("#facilities").val(),
            noticeuse: noticeuse
        }
        console.log(postData)
        $.ajax({
            type: "post",
            url: Global.host + "message/updatemessage",
            data: postData,
            success: function (res) {
                console.log(res)
                if(res=="update.success"){
                    alert("修改成功")
                    $("#myupd").modal("hide")
                    Venue.getGuan()
                }else{
                    alert("修改失败")
                }
            }
        });
    },
    eventsBind: function () {
        //场馆名称 搜索
        // $("#sub").click(function () {
        //     Venue.getGuanByName()
        // })
        //模态框
        //点击 添加使用须知
        $(".addNotesBtn").click(function(){
            var value=$(this).siblings("input").val().trim()
            var $modalBody=$(this).closest(".modal-body")
            if(!value==""){
                $modalBody.find(".notes_ol").append($(`<li>${value}</li>`))
            }
        })
        //修改update确定
        $("#update").click(function () {
            Venue.update()
        });
        //模态框 end
    },
    init: function () {
        Venue.getGuan()
        Venue.eventsBind()
    }
}


$(function () {
    Venue.init()
    //新增
    $("#insert").click(function () {
        var stnameArr = [];
        $("#ul2 li").each(function () {
            stnameArr.push($(this).text());
        });
        var stname = stnameArr.join(",")

        var checkArr = [];
        var obj = document.getElementsByName("gclosedtime1");
        for (k in obj) {
            if (obj[k].checked) {
                checkArr.push(obj[k].value);
            }
        }
        var check = checkArr.join(",")

        var noticeuse = '';
        var noticeuseArr = [];
        $("#ol li").each(function () {
            noticeuseArr.push($(this).text());
        });
        var noticeuse = noticeuseArr.join(",")
        if($("#gname1").val() == ''){
        	alert("体育馆名称不能为空！");
        	return false;
        }
        if($("#glocation1").val() == ''){
        	alert("体育馆地址不能为空！");
        	return false;
        }
        if($("#gphonenum1").val() == ''){
        	alert("体育馆电话不能为空！");
        	return false;
        }
        if($("#facilities1").val() == ''){
        	alert("场馆设施不能为空！");
        	return false;
        }
        $.ajax({
            type: "post",
            url: Global.host + "message/insertmessage",
            data: {
                stname: stname,
                gname: $("#gname1").val(),
                glocation: $("#glocation1").val(),
                gphonenum: $("#gphonenum1").val(),
                gstarttime: 1,
                gendtime: 1,
                gclosedtime: check,
                facilities: $("#facilities1").val(),
                noticeuse: noticeuse
            },
            success: function (data) {
                if(data == 'insert.success'){
                	alert("新增成功！");
                }else if(data = 'login'){
                	alert("请先登录！");
                }else{
                	alert("新增失败！");
                }
                location.reload();
            }
        });
    });
    //导出excel
    $("#excel").click(function () {
        $.ajax({
            type: "post",
            url: Global.host + "message/getgmdown",
            success: function (data) {
                location.reload();
            }
        });
    });
});
//修改展示
function editor(gid,look) {
    $.ajax({
        type: "post",
        url: Global.host + "message/selectbygid",
        data: {
            gid: gid
        },
        success: function (data) {
            $("#glocation").val(data.glocation);
            $("#gname").val(data.gname);
            $("#gphonenum").val(data.gphonenum);
            var sitetype = data.stietype.split(",");
            $(".venue_attr").html('');
            for (var i = 0; i < sitetype.length; i++) {
                $("#ul2").append("<li>" + sitetype[i] + "</li>");
            }
            $("#gstarttime").val(data.gstarttime);
            $("#gendtime").val(data.gendtime);
            $("#gid").val(data.gid);
            var gclosedtime = data.gclosedtime.split(",");
            //console.log(gclosedtime)
            for (var i = 0; i < gclosedtime.length; i++) {
                if (gclosedtime[i] == "0") {
                    $("#ch_0").prop("checked", true);
                } else if (gclosedtime[i] == "1") {
                    $("#ch_1").prop("checked", true);
                } else if (gclosedtime[i] == "2") {
                    $("#ch_2").prop("checked", true);
                } else if (gclosedtime[i] == "3") {
                    $("#ch_3").prop("checked", true);
                } else if (gclosedtime[i] == "4") {
                    $("#ch_4").prop("checked", true);
                } else if (gclosedtime[i] == "5") {
                    $("#ch_5").prop("checked", true);
                } else if (gclosedtime[i] == "6") {
                    $("#ch_6").prop("checked", true);
                } else if (gclosedtime[i] == "7") {
                    $("#ch_7").prop("checked", true);
                }
            }
            $("#facilities").val(data.facilities);
            var noticeuse = data.noticeuse.split(",");
            $("#ol1").html('');
            for (var i = 0; i < noticeuse.length; i++) {
                $("#ol1").append("<li>" + noticeuse[i] + "</li>");
            }
            if(look == 1){
            	$("#block1").css("display","none");
            	$("#notes_item").css("display","none");
            	$("#bbb").css("display","none");
            }else{
            	$("#block1").css("display","");
            	$("#notes_item").css("display","");
            	$("#bbb").css("display","");
            }
        }
    });
}
//删除
function deletegid(gid) {
	if(gid == ''){
		alert("体育馆id不能为空！");
		return false;
	}
	//是否确认删除
	var flag = confirm('是否确认删除?');
	if(flag == false){
		return false;
	}
    $.ajax({
        type: "post",
        url: Global.host + "message/deletebygid",
        data: {
            gid: gid
        },
        success: function (result) {
            if (result == 9) {
                alert("有预定不可以删除！");
            } else if(result > 0) {
                alert("删除成功！");
            }else{
            	alert("删除失败！");
            }
            location.reload();
        }
    });
}

//变更开馆闭馆状态
function updatestate(gid){
	if(gid == ''){
		alert("体育馆id不能为空！");
		return false;
	}
	 $.ajax({
        type: "post",
        url: Global.host + "message/updatestate",
        data: {
            gid: gid
        },
        success: function (data) {
            if (data == 'update.state.success') {
                alert("修改成功！");
            } else {
                alert("修改失败！");
            }
            location.reload();
        }
    });
}

//场地管理
function sitemanage(gid) {
    window.location.href = "manage_field.html?gid=" + gid;
}