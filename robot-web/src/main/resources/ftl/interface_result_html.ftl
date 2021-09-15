<form class="layui-form" id="test" lay-filter="test">

    <#list bodys?chunk(3) as subBodys>
        <div class="layui-form-item">
            <#list subBodys as body>
                <#if body.type == 'select'>
                    <div class="layui-inline">
                        <label class="layui-form-label">${body.desc}:</label>
                        <div class="layui-input-inline">
                            <select name="${body.name}" id="${body.name}"
                                    <#if body.required>
                                        lay-verify="required"
                                    </#if>
                            disabled ><option value="">请选择</option></select>
                        </div>
                    </div>
                <#else>
                    <div class="layui-inline">
                        <label class="layui-form-label">${body.desc}:</label>
                        <input type="text" name="${body.name}" id="${body.name}"
                                <#if body.required>
                                    lay-verify="required"
                                </#if>
                               style="width: 250px;" placeholder="请输入${body.desc}"
                               autocomplete="off" disabled
                               class="layui-input">
                    </div>
                </#if>

            </#list>
        </div>
    </#list>
    <#list arrays as array>
        <table class="layui-hide" id="test${array?index}" lay-filter="test${array?index}"></table>
    </#list>
    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-primary" ng-click="exit()">返回</button>
        </div>
    </div>
</form>

