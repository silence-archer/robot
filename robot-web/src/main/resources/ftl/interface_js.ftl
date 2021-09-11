app.controller('${name}Controller', function ($scope, $http, $location, dataService, dataDictService) {
var baseUrl = dataService.getUrlData();
layui.use(['layer', 'form', 'table'], function () {
var table = layui.table,
form = layui.form,
layer = layui.layer,
$ = layui.jquery;
dataDictService.getUserListService("ipOwner");
dataDictService.getSceneService("scene","${name}");
<#list bodys as body>
    <#if body.type == 'select'>
        dataDictService.getDataDictService("${body.name}","${body.name}");
    </#if>
</#list>
<#list arrays as array>
    <#list array.list as innerBody>
        <#if innerBody.type == 'select'>
            dataDictService.getDataDictService("${innerBody.name}","${innerBody.name}");
        </#if>
    </#list>
</#list>
form.render();
form.on('select(selectScene)', function(data){
$http.get(baseUrl+'getSceneBySceneId?sceneId='+data.value).then(function successCallback(response) {
if(response.data.code === 0){
form.val('formLoan', response.data.data.body);
form.val('testSys', response.data.data.sysHead);
<#list arrays as array>
table.reload('test${array?index}', {
data: response.data.data.body.${array.name}
});
</#list>
}else{
layer.msg(response.data.msg, {icon: 5});
}

}, function errorCallback(response) {
console.log(response);
});
});
<#list arrays as array>
table.render({
elem: '#test${array?index}'
, title: '${array.desc}'
, toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
, cols: [[
<#list array.list as innerBody>

    <#if innerBody?index != 0>,</#if>{field: '${innerBody.name}', title: '${innerBody.desc}', width: 100}
    </#list>
, {fixed: 'right', title: '操作', toolbar: '#barDemo', width: 200}
]],
data: []
});
    //监听头工具栏事件
    table.on('toolbar(test${array?index})', function (obj) {
    switch (obj.event) {
    case 'add':
    layer.open({
    type: 1
    , title: '添加数据'
    , area: ['50%', '500px']
    , content: $("#dialog${array?index}").html()//引用的弹出层的页面层的方式加载修改界面表单
    , success: function (layero, index) {
    form.render('select');
    }
    });
//动态向表传递赋值可以参看文章进行修改界面的更新前数据的显示，当然也是异步请求的要数据的修改数据的获取
form.on('submit(formArray${array?index})', function (data) {
form.render('select');
let tableData = table.getData('test${array?index}');
tableData.push(data.field);
table.reload('test${array?index}', {
data: tableData
});
layer.closeAll();//关闭所有的弹出层
return false;//false：阻止表单跳转 true：表单跳转
});
break;
case 'update':
break;
case 'delete':
break;
}
});

//监听行工具事件
table.on('tool(test${array?index})', function (obj) {
var data = obj.data;
if (obj.event === 'del') {
layer.confirm('真的删除行么', function (index) {
obj.del();
layer.close(index);
});
} else if (obj.event === 'edit') {
layer.open({
type: 1
, title: '修改数据'
, area: ['50%', '500px']
, content: $('#dialog${array?index}').html()//引用的弹出层的页面层的方式加载修改界面表单
, success: function (layero, index) {
form.val('example${array?index}', {
    <#list array.list as innerBody>
        <#if innerBody?index != 0>,</#if>'${innerBody.name}' : data.${innerBody.name}
    </#list>
});
}
});
form.on('submit(formArray${array?index})', function (info) {
obj.update({
<#list array.list as innerBody>

    <#if innerBody?index != 0>,</#if>${innerBody.name}: info.field.${innerBody.name}
</#list>
});
layer.closeAll();//关闭所有的弹出层
return false;//false：阻止表单跳转 true：表单跳转
});
}
});
</#list>
form.on('submit(formLoan)', function (info) {
const body = info.field;
<#list arrays as array>
body["${array.name}"] = table.getData("test${array?index}");
</#list>
console.log(body);
$http.post(baseUrl+"loan",{
apiCd: $('#ipOwner').val()+":"+$('#port').val()+"${uri}",
data: {'body':body,'sysHead':{
'sourceType': $('#sourceType').val(),
'branchId': $('#branchId').val()
}}
}).then(function successCallback(response) {
if(response.data.code === 0){
dataService.setData(response.data.data);
$location.url("/${name}/result");
}else{
layer.msg(response.data.msg, {icon: 5});
}

}, function errorCallback(response) {
console.log(response);
});
});
});
});