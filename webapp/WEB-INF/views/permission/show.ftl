[@override name="subContent"]
<div class="col-md-12 panel-info">
    [@mc.showAlert /]

    <div class="content-box-header panel-heading">
        <div class="panel-title">权限管理</div>
    </div>

    <div class="content-box-large box-with-header">
        <div class="panel-body">
            <legend>权限信息</legend>
            <fieldset class="form-horizontal">

                <div class="form-group">
                    <label class="col-md-1 control-label">ID</label>
                    <div class="col-sm-11">
                        <span class="form-control">${(permission.id)!}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-1 control-label">权限名称</label>
                    <div class="col-sm-11">
                        <span class="form-control">${(permission.permission)!}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-1 control-label">描述</label>
                    <div class="col-sm-11">
                        <span class="form-control">${(permission.description)!}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-1 control-label">状态</label>
                    <div class="col-md-11">
                        <span class="form-control">[#if permission.available!]可用[#else]禁用[/#if]</span>
                    </div>
                </div>

                <a href="/permission/edit/${(permission.id!)}" class="btn btn-info">
                    <i class="glyphicon glyphicon-refresh"></i>编辑
                </a>
                <a href="/permission/delete/${(permission.id!)}" class="btn btn-danger">
                    <i class="glyphicon glyphicon-trash"></i>删除
                </a>
                <a href="/permission/list" class="btn btn-default">
                    <i class="glyphicon glyphicon-remove"></i>取消
                </a>
            </fieldset>
        </div>
    </div>
</div>
[/@override]

[@extends name="/decorator.ftl" /]