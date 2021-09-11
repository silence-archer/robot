<fieldset class="layui-elem-field">
    <legend>调用地址</legend>
    <div class="layui-field-box">
        <form class="layui-form">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">ip所有者:</label>
                    <div class="layui-input-inline">
                        <select name="ipOwner" id="ipOwner" lay-verify="required"></select>
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">端口:</label>
                    <input type="text" name="port" id="port" lay-verify="required"
                           style="width: 250px;" placeholder="请输入端口" value="${port?c}"
                           autocomplete="off"
                           class="layui-input">
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">场景:</label>
                    <div class="layui-input-inline">
                        <select name="scene" id="scene" lay-filter="selectScene" lay-verify="required">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
            </div>
        </form>
    </div>
</fieldset>
<fieldset class="layui-elem-field">
    <legend>系统头信息</legend>
    <div class="layui-field-box">
        <form class="layui-form" id="testSys" lay-filter="testSys">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">渠道类型:</label>
                    <input type="text" name="sourceType" id="sourceType" lay-verify="required"
                           style="width: 250px;" placeholder="请输入渠道类型"
                           autocomplete="off"
                           class="layui-input">
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">机构号:</label>
                    <input type="text" name="branchId" id="branchId" lay-verify="required"
                           style="width: 250px;" placeholder="请输入机构号"
                           autocomplete="off"
                           class="layui-input">
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">交易日期:</label>
                    <input type="text" name="tranDate" id="tranDate" lay-verify="required"
                           style="width: 250px;" placeholder="请输入交易日期"
                           autocomplete="off"
                           class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">柜员号:</label>
                    <input type="text" name="userId" id="userId" lay-verify="required"
                           style="width: 250px;" placeholder="请输入柜员号"
                           autocomplete="off"
                           class="layui-input">
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">授权柜员:</label>
                    <input type="text" name="authUserId" id="authUserId" lay-verify="required"
                           style="width: 250px;" placeholder="请输入机构号"
                           autocomplete="off"
                           class="layui-input">
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">来源系统:</label>
                    <input type="text" name="systemId" id="systemId" lay-verify="required"
                           style="width: 250px;" placeholder="请输入来源系统"
                           autocomplete="off"
                           class="layui-input">
                </div>
            </div>
        </form>
    </div>
</fieldset>

<blockquote class="layui-elem-quote">主体信息</blockquote>
<form class="layui-form" id="formLoan" lay-filter="formLoan">
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
                                ><option value="">请选择</option></select>
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
                               autocomplete="off"
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
            <button type="submit" class="layui-btn" lay-submit lay-filter="formLoan">提交</button>
        </div>
    </div>
</form>
<#list arrays as array>

    <div class="layui-row" id="dialog${array?index}" style="display:none;">
        <br/>
        <form class="layui-form" lay-filter="example${array?index}">
            <#list array.list as innerBody>
                <#if innerBody.type == 'select'>
                    <div class="layui-form-item">
                        <label class="layui-form-label">${innerBody.desc}</label>
                        <div class="layui-input-inline">
                            <select name="${innerBody.name}" id="${innerBody.name}"
                                    <#if innerBody.required>
                                        lay-verify="required"
                                    </#if>
                            ><option value="">请选择</option></select>
                        </div>
                    </div>
                <#else>
                    <div class="layui-form-item">
                        <label class="layui-form-label">${innerBody.desc}</label>
                        <div class="layui-input-inline" style="width: 250px;">
                            <input type="text" name="${innerBody.name}" id="${innerBody.name}" <#if innerBody.required>
                                lay-verify="required"
                            </#if>
                                   placeholder="请输入${innerBody.desc}"
                                   autocomplete="off"
                                   class="layui-input">
                        </div>
                    </div>
                </#if>
            </#list>


            <div class="layui-form-item">
                <div class="layui-input-block">
                    <button type="submit" class="layui-btn" lay-submit lay-filter="formArray${array?index}">提交</button>
                </div>
            </div>
        </form>
    </div>

</#list>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">查看</a>
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>


