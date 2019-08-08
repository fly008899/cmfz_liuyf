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
        $("#list").jqGrid({
            url: "${path}/banner/showAll",
            editurl: "${path}/banner/add",
            styleUI: "Bootstrap",
            datatype: "json",
            height: "auto",
            colNames: ["名称", "图片", "状态", "上传时间", "描述"],
            autowidth: true,
            pager: "#pager", //生成分页的工具栏
            rowNum: 4,      //后台每页显示的条数
            rowList: [4, 6, 8, 10], //展示的记录条数
            viewrecords: true,  //展示总条数
            colModel: [
                {name: "name", editable: true, width: 90, align: "center"},
                {
                    name: "pic_path", editable: true, edittype: "file", align: "left",
                    formatter: function (cellvalue) {
                        return "<img src='${path}/upload/photo/" + cellvalue + "' style='height: 70px;width: 90px'/>"
                    },
                },
                {
                    name: "status", width: 90, align: "left",
                    formatter: function (cellvalue, option, row) {
                        if (cellvalue == "激活") {
                            //展示状态
                            return "<button class='btn btn-danger' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'>取消激活</button>";
                        } else {
                            //不展示
                            return "<button class='btn btn-success' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'>激活</button>";
                        }
                    },
                },
                {name: "creat_time", width: 90, align: "left"},
                {name: "description", editable: true, width: 90, align: "left"}
            ]
        });
        <!--增删改样式-->
        $("#list").jqGrid('navGrid', '#pager', {
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
                        url: "${path}/banner/uploadBanner",
                        type: "post",
                        dataType: "JSON",
                        fileElementId: "pic_path",
                        data: {id: data.responseText},
                        success: function () {
                            //刷新表单
                            $("#list").trigger("reloadGrid");
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
                url: "${path}/banner/updateStatus",
                type: "post",
                dataType: "JSON",
                data: {"id": id, "status": "激活"},
                success: function (data) {
                    //页面的刷新
                    $("#list").trigger("reloadGrid");
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
                url: "${path}/banner/updateStatus",
                type: "post",
                dataType: "JSON",
                data: {"id": id, "status": "未激活"},
                success: function (data) {
                    //页面的刷新
                    $("#list").trigger("reloadGrid");
                    //提示框添加信息
                    $("#warning").html(data.message);
                    //展示错误信息
                    $("#show").show();
                }
            });
        }
    }
</script>
</head>
<body>
<!--初始化面板-->
<br>
<div class="panel panel-info">

    <div class="panel panel-heading">
        <h2>轮播图信息</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">轮播图信息</a></li>
    </ul>


</div>
<div id="show" class="alert alert-warning alert-dismissable" role="alert" style="display: none">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="ture">&times;</span>
    </button>
    <strong id="warning"></strong>
</div>

<!--初始化表单-->
<table id="list"></table>

<!--定义分页工具栏-->
<div id="pager"></div>
</div>
</body>
</html>