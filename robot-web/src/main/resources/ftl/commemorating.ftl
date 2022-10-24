<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${desc}纪念日快乐</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="icon" href="image/favicon.ico">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/layui/2.7.6/css/layui.css"
          integrity="sha512-SSF+OBDODWTSIqOivYBOyOKQ93PBDevipJEUEWtkUbTt4v34rmgPcCXcBMolxZIJcuobcdqmYJlonjUBEbOzNw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

</head>
<body>
<div class="layui-container">

    <div class="layui-row">
        <fieldset class="layui-elem-field layui-field-title" style="margin-top: 30px;">
            <legend style="margin-left: 450px;">${year}年前的今天我们${desc}啦!</legend>
        </fieldset>
        <div class="layui-col-md6">
            <ul class="layui-timeline">
                <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis layui-icon-fire"></i>
                    <div class="layui-timeline-content layui-text">
                        <h3 class="layui-timeline-title">现在</h3>
                    </div>
                </li>
                <#list bodys as body>
                    <li class="layui-timeline-item">
                        <#if body.now>
                            <i class="layui-icon layui-timeline-axis layui-icon-heart-fill"></i>
                            <#else>
                                <i class="layui-icon layui-timeline-axis layui-icon-heart"></i>
                        </#if>
                        <div class="layui-timeline-content layui-text">
                            <h3 class="layui-timeline-title">${body.date}</h3>
                            <ul>
                                <#list body.list as item>
                                <li>${item}</li>
                                </#list>
                            </ul>
                        </div>
                    </li>
                </#list>

                <li class="layui-timeline-item">
                    <i class="layui-icon layui-timeline-axis layui-icon-snowflake"></i>
                    <div class="layui-timeline-content layui-text">
                        <h3 class="layui-timeline-title">过去</h3>
                    </div>
                </li>
            </ul>
        </div>
        <div class="layui-col-md6">
            <div class="layui-carousel" id="test1">
                <div carousel-item>
                    <#list bodys as body>
                        <div><img src="image/${body.imageName}" style="width: 100%;height: auto"></div>
                    </#list>
                </div>
            </div>
        </div>
    </div>


    <!-- 引入 layui.js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/layui/2.7.6/layui.js"
            integrity="sha512-mIKH3M2bRlIyhG4tBEbJ8dn8t8JFlNJU2NXlJePgpQ72CK4jAYsZyCGFcASRGtPBbcAQhz67KTkA1Jw6Kizk9g=="
            crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    <script>
        layui.use('carousel', function () {
            var carousel = layui.carousel;
            //建造实例
            carousel.render({
                elem: '#test1'
                , width: '100%' //设置容器宽度
                , height: '900px'
                , arrow: 'always' //始终显示箭头
                , anim: 'fade' //切换动画方式
            });
        });
    </script>
</body>
</html>