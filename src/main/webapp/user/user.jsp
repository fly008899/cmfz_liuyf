<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script type="application/javascript">
        $(function () {
            $("#userlist").jqGrid({
                url: "${path}/user/showAll",
                editurl: "${path}/user/edit",
                styleUI: "Bootstrap",
                datatype: "json",
                height: "auto",
                colNames: ["ID", "头像", "名字", "昵称", "密码" , "所在地" , "签名" , "性别" , "状态" , "手机号" , "注册时间"],
                autowidth: true,
                pager: "#userpager", //生成分页的工具栏
                rowNum: 4,      //后台每页显示的条数
                rowList: [4, 6, 8, 10], //展示的记录条数
                viewrecords: true,  //展示总条数
                colModel: [
                    {name: "id", width: 90, align: "center"},
                    {name: "picture", editable: true, width: 90, edittype: "file", align: "center",
                        formatter: function (cellvalue) {
                            return "<img src='${path}/upload/photo/" + cellvalue + "' style='height: 50px;width: 50px'/>"
                        },
                    },
                    {name: "name", editable: true, width: 90, align: "left"},
                    {name: "nick", editable: true, width: 90, align: "left"},
                    {name: "password", editable: true, width: 90, align: "left"},
                    {name: "localtion", editable: true, width: 90, align: "left"},
                    {name: "sign", editable: true, width: 90, align: "left"},
                    {name: "sex", editable: true, width: 90, align: "left"},
                    {name: "status", width: 90, align: "left",
                        formatter: function (cellvalue, option, row) {
                            if (cellvalue == "激活") {
                                //展示状态
                                return "<button class='btn btn-danger' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'>冻结</button>";
                            } else {
                                //不展示
                                return "<button class='btn btn-success' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'>激活</button>";
                            }
                        },
                    },
                    {name: "phone", editable: true, width: 90, align: "left"},
                    {name: "creat_time", width: 90, align: "left"},
                ]
            });
            <!--增删改样式-->
            $("#userlist").jqGrid('navGrid', '#userpager', {
                    edit: true, edittext: "编辑",
                    add: true, addtext: "添加",
                    del: true, deltext: "删除",
                },
                <!--添加操作-->
                {closeAfterAdd: true, closeAfterEdit: true},
                <!--修改操作-->
                {
                    afterSubmit: function (data) {
                        $.ajaxFileUpload({
                            //文件上传
                            url: "${path}/user/uploadPic",
                            type: "post",
                            dataType: "JSON",
                            fileElementId: "picture",
                            data: {id: data.responseText},
                            success: function () {
                                //刷新表单
                                $("#userlist").trigger("reloadGrid");
                            }
                        })
                        //需要有返回值
                        return "success";
                    },
                    closeAfterAdd: true,
                    closeAfterEdit: true
                },
                <!--删除操作-->
                {}
            );
        });

        //点击触发函数
        function change(id, value) {
            if (value == "未激活") {
                $.ajax({
                    url: "${path}/user/updateStatus",
                    type: "post",
                    dataType: "JSON",
                    data: {"id": id, "status": "激活"},
                    success: function (data) {
                        //页面的刷新
                        $("#userlist").trigger("reloadGrid");
                        //提示框添加信息
                        $("#warning").html(data.message);
                        //展示错误信息
                        $("#show").show();

                        //设置提示框时间
                        setTimeout(function () {
                            //关闭提示框
                            $("#show").hide();
                        }, 2000);
                    }
                });
            } else {
                $.ajax({
                    url: "${path}/user/updateStatus",
                    type: "post",
                    dataType: "JSON",
                    data: {"id": id, "status": "未激活"},
                    success: function (data) {
                        //页面的刷新
                        $("#userlist").trigger("reloadGrid");
                        //提示框添加信息
                        $("#warning").html(data.message);
                        //展示错误信息
                        $("#show").show();

                        //设置提示框时间
                        setTimeout(function () {
                            //关闭提示框
                            $("#show").hide();
                        }, 2000);
                    }
                });
            }
        }

        //点击触发导出函数
        $("#btn1").click(function () {
            //向后台提交
            $.ajax({
                url: "${path}/user/output",
                type: "post",
                dataType: "json",
                data: {},
                success: function (data) {
                    //提示框添加信息
                    $("#warning").html(data.message);
                    //展示返回信息
                    $("#show").show();
                    //刷新页面
                    $("#artlist").trigger("reloadGrid");

                    //设置提示框时间
                    setTimeout(function () {
                        //关闭提示框
                        $("#show").hide();
                    }, 2000);
                }
            });
        });
    </script>
</head>
<body>
<!--初始化面板-->
<br>
<div class="panel panel-danger">

    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">用户信息</a></li>
    </ul>

    <div class="panel panel-body">
        <button type="button" id="btn1" class="btn btn-success">导出用户表</button>&emsp;
    </div>

</div>
<div id="show" class="alert alert-warning alert-dismissable" role="alert" style="display: none">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="ture">&times;</span>
    </button>
    <strong id="warning"></strong>
</div>

<!--初始化表单-->
<table id="userlist"></table>

<!--定义分页工具栏-->
<div id="userpager"></div>
</div>
</body>
</html>