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
    <script charset="utf-8" src="${path}/kindeditor/kindeditor-all.js"></script>
    <script charset="utf-8" src="${path}/kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.create('#editor_id', {
            uploadJson: "${path}/editor/upload",
            filePostName: "photo",
            allowFileManager: true,
            fileManagerJson: "${path}/editor/queryAllPhoto",
            afterBlur: function () {  //编辑器失去焦点(blur)时执行的回调函数。
                this.sync();  //将编辑器中的内容同步到表单
            }
        });
    </script>
    <script type="application/javascript">
        $(function () {
            $("#artlist").jqGrid({
                url: "${path}/article/showAll",
                editurl: "${path}/article/edit",
                styleUI: "Bootstrap",
                autowidth:true,  //自适应父容器
                datatype: "json",
                height: "auto",
                colNames: ["名称", "封面", "内容", "状态", "上传时间", "上师", "操作"],
                pager: "#artpager", //生成分页的工具栏
                rowNum: 4,      //后台每页显示的条数
                rowList: [4, 6, 8, 10], //展示的记录条数
                viewrecords: true,  //展示总条数
                colModel: [
                    {name: "title", editable: true, width: 90, align: "center"},
                    {
                        name: "picture", editable: true, edittype: "file", align: "left",
                        formatter: function (cellvalue) {
                            return "<img src='${path}/upload/photo/" + cellvalue + "' style='height: 70px;width: 90px'/>";
                        },
                    },
                    {name: "content", width: 90,itemHeight: 120, align: "center"},
                    {
                        name: "status", width: 90, align: "left",
                        formatter: function (cellvalue, option, row) {
                            if (cellvalue == "激活") {
                                //展示状态
                                return "<button class='btn btn-danger' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'>不展示</button>";
                            } else {
                                //不展示
                                return "<button class='btn btn-success' onclick='change(\"" + row.id + "\",\"" + cellvalue + "\")'>展示</button>";
                            }
                        },
                    },
                    {name: "creat_time", width: 90, align: "left"},
                    {name: "guru_id", editable: true, width: 90, align: "left"},
                    {name: "xxx"}
                ]
            });
            <!--增删改样式-->
            $("#artlist").jqGrid('navGrid', '#artpager', {
                    edit: true, edittext: "编辑项目",
                    add: true, addtext: "添项目",
                    del: true, deltext: "删项目",
                },
                <!--添加操作-->
                {closeAfterAdd: true, closeAfterEdit: true},
                <!--修改操作-->
                {
                    afterSubmit: function (data) {
                        $.ajaxFileUpload({
                            //文件上传
                            url: "${path}/article/uploadPicture",
                            type: "post",
                            dataType: "JSON",
                            fileElementId: "picture",
                            data: {id: data.responseText},
                            success: function () {
                                //刷新表单
                                $("#artlist").trigger("reloadGrid");
                            }
                        });
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
                    url: "${path}/article/updateStatus",
                    type: "post",
                    dataType: "JSON",
                    data: {"id": id, "status": "激活"},
                    success: function (data) {
                        //页面的刷新
                        $("#artlist").trigger("reloadGrid");
                        //提示框添加信息
                        $("#warning").html(data.message);
                        //展示错误信息
                        $("#show").show();
                    }
                });
            } else {
                $.ajax({
                    url: "${path}/article/updateStatus",
                    type: "post",
                    dataType: "JSON",
                    data: {"id": id, "status": "未激活"},
                    success: function (data) {
                        //页面的刷新
                        $("#artlist").trigger("reloadGrid");
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

        <!--                            编辑文章                                               -->
        /*点击展示详情*/
        $("#btn1").click(function () {
            //选中一行 获取行Id
            var rowId = $("#artlist").jqGrid("getGridParam", "selrow");
            //判断是否选中一行
            if (rowId != null) {
                //根据行Id获取行数据
                var row = $("#artlist").jqGrid("getRowData", rowId);

                //展示模态框
                $("#ModalBlank").modal("show");

                //给inout框设置内容
                $("#title").val(row.title);

                //给KindEditor框设置内容
                KindEditor.html("#editor_id", row.content);

                //添加按钮
                $("#modalFooter").html("<button type='button' class='btn btn-primary' data-dismiss='modal'>关闭</button>");
            } else {
                alert("请选中要展示的文章");
            }

        });

        /*点击添加文章*/
        $("#btn2").click(function () {
            //选中一行 获取行Id
            var rowId = $("#artlist").jqGrid("getGridParam", "selrow");
            //判断是否选中一行
            if (rowId != null) {
                //根据行Id获取行数据
                var row = $("#artlist").jqGrid("getRowData", rowId);

                //展示模态框
                $("#ModalBlank").modal("show");

                //给inout框设置内容
                $("#title").val(row.title);

                //给KindEditor框设置内容
                KindEditor.html("#editor_id", row.content);

                //添加按钮
                $("#modalFooter").html("<button type='button' onclick='updateArticle(\"" + rowId + "\")' class='btn btn-default'>提交</button><button type='button' class='btn btn-primary' data-dismiss='modal'>关闭</button>");
            } else {
                alert("请选中待编辑文章");
            }

        });

        /*修改的提交按钮*/
        function updateArticle(rowId) {

            //向后台提交
            $.ajax({
                url: "${path}/article/update?ArticleId=" + rowId,
                type: "post",
                dataType: "json",
                data: $("#articleFrom").serialize(),
                success: function (data) {
                    //关闭模态框
                    $('#ModalBlank').modal('hide');
                    //提示框添加信息
                    $("#warning2").html(data.message);
                    //展示错误信息
                    $("#show2").show();
                    //刷新页面
                    $("#artlist").trigger("reloadGrid");

                    //设置提示框时间
                    setTimeout(function () {
                        //关闭提示框
                        $("#show2").hide();
                    }, 2000);
                }
            });
        }


    </script>
</head>
<body>
<!--初始化面板-->
<br>
<div class="panel panel-primary">

    <div class="panel panel-heading">
        <h2>文章信息</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">文章信息</a></li>
    </ul>

    <div class="panel panel-body">
        <button type="button" id="btn1" class="btn btn-info">查看文章</button>&emsp;
        <button type="button" id="btn2" class="btn btn-success">添加文章</button>&emsp;
    </div>

</div>
<div id="show" class="alert alert-warning alert-dismissable" role="alert" style="display: none">
    <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="ture">&times;</span>
    </button>
    <strong id="warning"></strong>
</div>
<div id="show2" class="alert alert-warning alert-dismissable" role="alert" style="display: none">
    <button type="button" class="close" data-dismiss="alert"  aria-label="Close"><span aria-hidden="ture">&times;</span>
    </button>
    <strong id="warning2"></strong>
</div>

<!--初始化表单-->
<table id="artlist"></table>

<!--定义分页工具栏-->
<div id="artpager"></div>
</div>

<%--初始化模态框--%>
<div id="ModalBlank" class="modal fade" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document" style="width: 730px">
        <div class="modal-content">
            <%--模态框标题--%>
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="gridSystemModalLabel">Modal title</h4>
            </div>
            <%--模态框内容--%>
            <div class="modal-body">
                <form class="form-horizontal" id="articleFrom">

                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1">标题</span>
                        <input id="title" name="title" type="text" class="form-control" aria-describedby="basic-addon1">
                    </div>
                    <br>
                    <div class="input-group">
                        <%--初始化富文本编辑器--%>
                        <textarea id="editor_id" name="content" style="width:700px;height:300px;">

                        </textarea>
                    </div>
                </form>
            </div>
            <%--  模态框按钮  --%>
            <div class="modal-footer" id="modalFooter">
                <%--<button type="button" class="btn btn-default">提交</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>--%>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
</body>
</html>