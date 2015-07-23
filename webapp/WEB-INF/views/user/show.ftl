[@override name="subContent"]
<div class="col-md-12 panel-info">
    [@mc.showAlert /]

    <div class="content-box-header panel-heading">
        <div class="panel-title">用户管理</div>
    </div>

    <div class="content-box-large box-with-header">
        <div class="panel-body">
            <legend>用户信息</legend>
            <fieldset class="form-horizontal">

                <div class="form-group">
                    <label class="col-md-1 control-label">ID</label>
                    <div class="col-sm-11">
                        <span class="form-control">${(user.id)!}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-1 control-label">用户名称</label>
                    <div class="col-sm-11">
                        <span class="form-control">${user.username!}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-1 control-label">姓名</label>
                    <div class="col-sm-11">
                        <span class="form-control">${user.realName!}</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-1 control-label">状态</label>
                    <div class="col-md-11">
                        <span class="form-control">[#if user.available!]可用[#else]禁用[/#if]</span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-md-1 control-label">登录日期</label>

                    <div class="col-sm-11">
                        <span class="form-control">${user.signedDate!}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-1 control-label">上次登录日期</label>

                    <div class="col-sm-11">
                        <span class="form-control">${user.lastSignedDate!}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-1 control-label">创建日期</label>

                    <div class="col-sm-11">
                        <span class="form-control">${user.createdDate!}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-1 control-label">更新日期</label>

                    <div class="col-sm-11">
                        <span class="form-control">${user.updatedDate!}</span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-md-1 control-label">角色</label>

                    <div class="col-sm-11">
                        [#list user.roleSet as role]
                            <span class="form-control">${role.role!}</span>
                        [/#list]
                    </div>
                </div>

                <a href="/user/edit/${(user.id!)}" class="btn btn-info">
                    <i class="glyphicon glyphicon-refresh"></i>编辑
                </a>
                <a href="/user/delete/${(user.id!)}" class="btn btn-danger">
                    <i class="glyphicon glyphicon-trash"></i>删除
                </a>
                <a href="/user/list" class="btn btn-default">
                    <i class="glyphicon glyphicon-remove"></i>取消
                </a>
            </fieldset>
        </div>
    </div>
</div>
[/@override]

[@extends name="/decorator.ftl" /]