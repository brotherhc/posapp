[@override name="subContent"]
<div class="col-md-12 panel-info">
    [@mc.showAlert /]

    <div class="content-box-header panel-heading">
        <div class="panel-title">角色管理</div>
    </div>

    <div class="content-box-large box-with-header">
        <div class="panel-body">
            <legend>角色信息</legend>
            <fieldset class="form-horizontal">

                <div class="form-group">
                    <label class="col-md-1 control-label">ID</label>
                    <div class="col-sm-11">
                        <span class="form-control">${role.id!}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-1 control-label">角色名称</label>
                    <div class="col-sm-11">
                        <span class="form-control">${role.role!}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-1 control-label">描述</label>
                    <div class="col-sm-11">
                        <span class="form-control">${(role.description)!}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-1 control-label">状态</label>
                    <div class="col-md-11">
                        <span class="form-control">[#if role.available!]可用[#else]禁用[/#if]</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-1 control-label">权限</label>
                    <div class="col-md-11">
                        [#list role.permissionSet as permission]
                            <span class="form-control">${permission.permission}</span>
                        [/#list]
                    </div>
                </div>

                <a href="/role/edit/${(role.id!)}" class="btn btn-info">
                    <i class="glyphicon glyphicon-refresh"></i>编辑
                </a>
                <a href="/role/delete/${(role.id!)}" class="btn btn-danger">
                    <i class="glyphicon glyphicon-trash"></i>删除
                </a>
                <a href="/role/list" class="btn btn-default">
                    <i class="glyphicon glyphicon-remove"></i>取消
                </a>
            </fieldset>
        </div>
    </div>
</div>
[/@override]

[@extends name="/decorator.ftl" /]