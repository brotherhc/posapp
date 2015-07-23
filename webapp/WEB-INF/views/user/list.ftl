[@override name="subContent"]
<div class="col-md-12 panel-info">
    [@mc.showAlert /]

    <div class="content-box-header panel-heading">
        <div class="panel-title">用户管理</div>
    </div>

    <div class="content-box-large box-with-header">
        <div class="panel-body">
            <legend>用户列表</legend>
            <form action="[@spring.url '/user/list' /]">
                <div class="col-md-3">
                    <label for="parent" class="control-label">父级用户名：</label>
                    <input id="parent" class="form-control" name="parent" value="${parent}"/>
                </div>
                <div class="col-md-1">
                    <label for="btn-ok">&nbsp;</label>
                    <div id="btn-ok">
                        <button  type="submit" class="btn btn-info">
                            <i class="glyphicon glyphicon-search"></i>查询
                        </button>
                    </div>
                </div>
            </form>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>用户名称</th>
                    <th>父级用户名称</th>
                    <th>姓名</th>
                    <th>登录时间</th>
                    <th>上次登录时间</th>
                    <th>状态</th>
                    <th class="col-md-2">操作</th>
                </tr>
                </thead>
                <tbody>
                    [#if pagination.data?size > 0]
                        [#list pagination.data as user]
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.username}</td>
                            <td>${user.parent.username}</td>
                            <td>${user.realName}</td>
                            <td>${user.signedDate}</td>
                            <td>${user.lastSignedDate}</td>
                            <td>[#if user.available]激活[#else]禁用[/#if]</td>
                            <td>
                                <a class="btn btn-xs btn-default" href="[@spring.url '/user/show/${user.id}' /]">
                                    <i class="glyphicon glyphicon-eye-open"></i>查看
                                </a>
                                <a class="btn btn-xs btn-info" href="[@spring.url '/user/edit/${user.id}' /]">
                                    <i class="glyphicon glyphicon-refresh"></i>编辑
                                </a>
                                <a class="btn btn-xs btn-danger" href="[@spring.url '/user/delete/${user.id}' /]">
                                    <i class="glyphicon glyphicon-trash"></i>删除
                                </a>
                            </td>
                        </tr>
                        [/#list]
                    [/#if]
                </tbody>
            </table>
            [@mc.showPagination "/user/list" /]
        </div>
    </div>
</div>
[/@override]

[@override name="bottomResources"]
    [@super /]
<link href="[@spring.url '/resources/vendors/datatables/dataTables.bootstrap.css' /]" rel="stylesheet" media="screen"/>
[/@override]

[@extends name="/decorator.ftl" /]
