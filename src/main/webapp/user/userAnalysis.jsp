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
    <script src="${path}/bootstrap/js/jquery.min.js"></script>
    <%--javascript 方式获取SDK--%>
    <script src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <!-- 引入 ECharts 文件 -->
    <script src="${path}/js/echarts.js"></script>
    <script type="text/javascript">
        $(function () {

            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            var goEasy = new GoEasy({
                appkey: "BC-fd441772316d4c9aaca97b36656cf51d" //你自己的appkeys
            });

            $.post("${path}/echarts/queryUser", function (data) {

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '用户注册量展示', //标题
                        link: "${path}/main/main.jsp",
                        target: "self",
                        subtext: "用户信息"
                    },
                    tooltip: {},  //鼠标的提示
                    legend: {
                        // show:false,  是否展示 选项
                        data: ['男性', "女性"]  //选项
                    },
                    xAxis: {
                        data: data.month  //横坐标
                    },
                    yAxis: {},  //纵坐标   自适应
                    series: [{
                        name: '男性',
                        type: 'line',
                        data: data.boys
                    }, {
                        name: '女性',
                        type: 'bar',
                        data: data.girls
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);

            }, "json");


            goEasy.subscribe({
                channel: "myChannel158", //管道标识
                onMessage: function (message) {
                    $.post("${path}/echarts/queryUser", function (data) {

                        // 指定图表的配置项和数据
                        var option = {
                            title: {
                                text: '用户注册量展示', //标题
                                link: "${path}/main/main.jsp",
                                target: "self",
                                subtext: "用户信息"
                            },
                            tooltip: {},  //鼠标的提示
                            legend: {
                                // show:false,  是否展示 选项
                                data: ['男性', "女性"]  //选项
                            },
                            xAxis: {
                                data: data.month  //横坐标
                            },
                            yAxis: {},  //纵坐标   自适应
                            series: [{
                                name: '男性',
                                type: 'line',
                                data: data.boys
                            }, {
                                name: '女性',
                                type: 'bar',
                                data: data.girls
                            }]
                        };

                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);

                    });
                }
            })

        });
    </script>
</head>
<body>
<!--初始化面板-->
<br>
<div class="panel panel-danger">

    <div class="panel panel-heading">
        <h2>用户统计</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">用户统计</a></li>
    </ul>
</div>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div align="center">
    <div id="main" style="width: 800px;height:500px;"></div>
</div>
</div>
</body>
</html>