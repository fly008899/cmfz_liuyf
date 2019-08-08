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

            $("#abtable").jqGrid({
                url: "${path}/album/showAll1",
                editurl: "${path}/album/editAlbum",
                styleUI: "Bootstrap",
                datatype: "json",
                autowidth: true,
                height: "auto",
                rowNum: 4,
                rowList: [8, 10, 20, 30],
                pager: '#abpager',
                sortname: 'id',
                viewrecords: true,
                sortorder: "desc",
                multiselect: false,
                colNames: ['Id', '名字', '作者', '评分', '封面', '广播', '集数', '内容', '发布时间'],
                colModel: [
                    {name: "id", width: 55},
                    {name: "title", editable: true, width: 90, align: "left"},
                    {name: "author", editable: true, width: 100, align: "left"},
                    {name: "score", editable: true, width: 90, align: "left"},
                    {
                        name: "cover_img", editable: true, edittype: "file", width: 80,
                        formatter: function (cellvalue) {
                            return "<img src='${path}/upload/photo/" + cellvalue + "' style='height: 70px;width: 90px'/>"
                        },
                    },
                    {name: "broadcast", editable: true, width: 90, align: "left"},
                    {name: "count", editable: true, width: 80, align: "left"},
                    {name: "content", editable: true, width: 90, align: "left"},
                    {name: "pub_date", width: 80, align: "left"}
                ],
                subGrid: true, //开启子表格支持
                //subgrid_id  子表格的Id,当开启子表格式,会在主表格中创建一个子表格，subgrid_id就是这个子表格的Id
                subGridRowExpanded: function (subgridId, rowId) {
                    addSubGrid(subgridId, rowId);
                },
                /*subGridRowColapsed : function(subgrid_id, row_id) {
                    // this function is called before removing the data
                    //var subgrid_table_id;
                    //subgrid_table_id = subgrid_id+"_t";
                    //jQuery("#"+subgrid_table_id).remove();
                }*/
            });
            /*增删改查操作*/
            $("#abtable").jqGrid('navGrid', '#abpager', {
                    add: true, addtext: "添加",
                    edit: true, edittext: "编辑",
                    del: true, deltext: "删除",
                },
                <!--添加操作-->
                {closeAfterAdd: true, closeAfterEdit: true},
                <!--修改操作-->
                {
                    afterSubmit: function (data) {
                        $.ajaxFileUpload({
                            //文件上传
                            url: "${path}/album/uploadAlbum",
                            type: "post",
                            dataType: "JSON",
                            fileElementId: "cover_img",
                            data: {id: data.responseText},
                            success: function () {
                                //刷新表单
                                $("#abtable").trigger("reloadGrid");
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

        //创建子表单
        function addSubGrid(subgridId, rowId) {

            var subgridTableId = subgridId + "table";
            var pagerId = subgridId + "pager";

            //初始化表单,分页工具栏
            $("#" + subgridId).html("<table id='" + subgridTableId + "'/><div id='" + pagerId + "'/>");

            //创建表单
            $("#" + subgridTableId).jqGrid({
                //url:"/chapter/queryByPage?albumId="+rowId,
                url: "${path}/album/showAll2?albumId=" + rowId,
                editurl: "${path}/album/editChapter?albumId=" + rowId,
                styleUI: "Bootstrap",
                datatype: "json",
                rowNum: 20,
                pager: "#" + pagerId,
                sortname: 'num',
                sortorder: "asc",
                autowidth: true,
                viewrecords: true,
                height: "auto",
                colNames: ['Id', '标题', '音频', '音频大小(MB)', '音频时长(Mins)', '上传时间', '操作'],
                colModel: [
                    {name: "id", width: 80, key: true},
                    {name: "name", index: "item", editable: true, width: 130, align: "left"},
                    {name: "url", index: "item", editable: true, edittype: "file", width: 130, align: "left"},
                    {name: "size", index: "qty", width: 70, align: "left"},
                    {name: "duration", index: "unit", width: 70, align: "left"},
                    {name: "up_date", index: "total", width: 70, align: "left"},
                    {
                        name: "xxx", index: "qty", width: 70, align: "left",
                        formatter: function (cellvalue, option, row) {
                            return "<a href='#'><span class='glyphicon glyphicon-play-circle' onclick='play(\"" + row.url + "\",\"" + cellvalue + "\")'>播放</span></a>" +
                                "<a href='#'><span class='glyphicon glyphicon-download' onclick='download(\"" + row.url + "\",\"" + cellvalue + "\")'>下载</span></href>";
                        },
                    }
                ]
            });

            /*子表格增删改查*/
            $("#" + subgridTableId).jqGrid('navGrid', "#" + pagerId,
                {
                    edit: true, edittext: "编辑",
                    add: true, addtext: "添加",
                    del: true, deltext: "删除"
                },
                <!--添加操作-->
                {},
                <!--修改操作-->
                {
                    afterSubmit: function (data) {
                        $.ajaxFileUpload({
                            //文件上传
                            url: "${path}/album/uploadVideo",
                            type: "post",
                            dataType: "JSON",
                            fileElementId: "url",
                            data: {id: data.responseText},
                            success: function () {
                                //刷新表单
                                $("#" + subgridTableId).trigger("reloadGrid");
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
        }

        //点击触发播放函数
        function play(url) {
            //展示模态框
            $("#audioModal").modal("show");
            //播放
            $("#playAudio").attr("src", "${path}/upload/Video/" + url);
        }

        //点击触发下载函数
        function download(url) {
            location.href = "${path}/album/down?filename=" + url;
        }
    </script>
</head>
<body>
<!--初始化面板-->
<br>
<div class="panel panel-success">

    <div class="panel panel-heading"><h2>专辑信息</h2></div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">专辑信息</a></li>
    </ul>
</div>

<div>
    <!--初始化表单-->
    <table id="abtable"/>
    <!--定义分页工具栏-->
    <div id="abpager"/>
</div>


<div id="audioModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <audio id="playAudio" src="" controls></audio>
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

</body>
</html>
