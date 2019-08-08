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
    <script src="${path}/js/china.js"></script>
    <script type="text/javascript">
        $(function () {

            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main1'));
            var goEasy = new GoEasy({
                appkey: "BC-fd441772316d4c9aaca97b36656cf51d" //你自己的appkeys
            });

            $.get("${path}/echarts/queryUserChina", function (data) {

                var series = [];

                for (var i = 0; i < data.length; i++) {
                    var d = data[i];

                    series.push({
                        name: d.title,
                        type: 'map',
                        mapType: 'china',
                        label: {
                            normal: {
                                show: false
                            },
                            emphasis: {
                                show: true
                            }
                        },
                        data: d.citys
                    })
                }

                // 指定图表的配置项和数据
                option = {
                    title: {
                        text: '用户分布图',
                        subtext: '2019年份',
                        left: 'center'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['女性', '男性']
                    },
                    visualMap: {
                        min: 0,
                        max: 2500,
                        left: 'left',
                        top: 'bottom',
                        text: ['高', '低'],           // 文本，默认为数值文本
                        calculable: true
                    },
                    toolbox: {
                        show: true,
                        orient: 'vertical',
                        left: 'right',
                        top: 'center',
                        feature: {
                            mark: {show: true},
                            dataView: {show: true, readOnly: false},
                            restore: {show: true},
                            saveAsImage: {show: true}
                        }
                    },
                    series: series
                };
                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            });


            goEasy.subscribe({
                channel: "myChannel158", //管道标识
                onMessage: function (message) {
                    $.get("${path}/echarts/queryUserChina", function (data) {

                        var series = [];

                        for (var i = 0; i < data.length; i++) {
                            var d = data[i];

                            series.push({
                                name: d.title,
                                type: 'map',
                                mapType: 'china',
                                label: {
                                    normal: {
                                        show: false
                                    },
                                    emphasis: {
                                        show: true
                                    }
                                },
                                data: d.citys
                            })
                        }

                        // 指定图表的配置项和数据
                        option = {
                            title: {
                                text: '用户分布图',
                                subtext: '纯属虚构',
                                left: 'center'
                            },
                            tooltip: {
                                trigger: 'item'
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left',
                                data: ['女性', '男性']
                            },
                            visualMap: {
                                min: 0,
                                max: 2500,
                                left: 'left',
                                top: 'bottom',
                                text: ['高', '低'],           // 文本，默认为数值文本
                                calculable: true
                            },
                            toolbox: {
                                show: true,
                                orient: 'vertical',
                                left: 'right',
                                top: 'center',
                                feature: {
                                    mark: {show: true},
                                    dataView: {show: true, readOnly: false},
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            series: series
                        };
                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    });
                }
            }
            , "json");
        });
    </script>
</head>
<body>
<!--初始化面板-->
<br>
<div class="panel panel-danger">

    <div class="panel panel-heading">
        <h2>用户分布</h2>
    </div>

    <ul class="nav nav-tabs">
        <li class="active"><a href="#">用户分布</a></li>
    </ul>
</div>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div align="center">
    <div align="center" id="main1" style="width: 800px;height:500px;"></div>
</div>
</div>
</body>
</html>