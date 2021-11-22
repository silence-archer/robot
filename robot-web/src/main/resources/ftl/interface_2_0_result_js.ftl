app.controller('${name}ResultController', function ($scope, $http, $location, dataService, dataDictService) {
const data = dataService.getData();
console.log(data);
layui.use(['layer', 'form', 'table'], function () {
const table = layui.table,
form = layui.form
<#list bodys as body>
    <#if body.type == 'select'>
        dataDictService.getDataDictService("${body.name}","${body.name}");
    </#if>
</#list>
<#list arrays as array>
    <#list array.list as innerBody>
        <#if innerBody.type == 'select'>
            let ${innerBody.name}List = [];
            dataDictService.getDataDictService("${innerBody.name}", null, function (dataList) {
            ${innerBody.name}List = dataList;
            });
        </#if>
    </#list>
</#list>

setTimeout(function () {
form.render();
<#list arrays as array>
table.render({
elem: '#test${array?index}'
, title: '${array.desc}'
,toolbar: true
, cols: [[
<#list array.list as innerBody>
    <#if innerBody?index != 0>,</#if>
    <#if innerBody.type == 'select'>
        {
        field: '${innerBody.name}', title: '${innerBody.desc}', width: 200, templet: function (d) {
        if (d.${innerBody.name} === '' || d.${innerBody.name} === null) {
        return d.${innerBody.name};
        }
        return d.${innerBody.name} + '-' + ${innerBody.name}List[d.${innerBody.name}];
        }
        }
<#else>
    {field: '${innerBody.name}', title: '${innerBody.desc}', width: 100}
</#if>
</#list>
]],
data: data.${array.name}
});
</#list>
},
500
);


form.val('test', data);
$scope.exit = function () {
$location.url('/autoInterface/${name}')
}


});

});