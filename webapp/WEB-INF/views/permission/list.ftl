[@override name="subContent"]
<div class="col-md-12 panel-info">
    [@mc.showAlert /]

    <div class="content-box-header panel-heading">
        <div class="panel-title">权限管理</div>
    </div>

    <div class="content-box-large box-with-header">
        <div class="panel-body">
            <legend>权限列表</legend>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>权限名称</th>
                        <th>描述</th>
                        <th>状态</th>
                        <th class="col-md-2">操作</th>
                    </tr>
                </thead>
                <tbody>
                [#if pagination.data?size > 0]
                    [#list pagination.data as permission]
                    <tr>
                        <td>${permission.id}</td>
                        <td>${permission.permission}</td>
                        <td>${permission.description}</td>
                        <td>[#if permission.available]可用[#else]禁用[/#if]</td>
                        <td>
                            <a class="btn btn-xs btn-default" href="[@spring.url '/permission/show/${permission.id}' /]">
                                <i class="glyphicon glyphicon-eye-open"></i>查看
                             </a>
                            <a class="btn btn-xs btn-info" href="[@spring.url '/permission/edit/${permission.id}' /]">
                                <i class="glyphicon glyphicon-refresh"></i>编辑
                             </a>
                            <a class="btn btn-xs btn-danger" href="[@spring.url '/permission/delete/${permission.id}' /]">
                                <i class="glyphicon glyphicon-trash"></i>删除
                            </a>
                        </td>
                    </tr>
                    [/#list]
                [/#if]
                </tbody>
            </table>
            [@mc.showPagination "/permission/list" /]
        </div>
    </div>
</div>
[/@override]

[@override name="bottomResources"]
    [@super /]
<link href="[@spring.url '/resources/vendors/datatables/dataTables.bootstrap.css' /]" rel="stylesheet" media="screen" />
[/@override]

[@extends name="/decorator.ftl" /]
