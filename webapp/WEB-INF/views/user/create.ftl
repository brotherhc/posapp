[@override name="subContent"]
<div class="col-md-12 panel-info">
    [@mc.showAlert /]

    <div class="content-box-header panel-heading">
        <div class="panel-title">用户管理</div>
    </div>

    <div class="content-box-large box-with-header">
        <div class="panel-body">
            <legend>创建用户</legend>
            <form class="form-horizontal" action="/user/create" method="post">
                <fieldset>
                    [@spring.bind "user.username" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="username">用户名</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="username" name="username" type="text" value="${(user.username)!}" />
                            [#if mc.hasError()]
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-remove-circle"></i>
                            </span>
                            [/#if]
                            [@spring.showErrors "" "help-block" /]
                        </div>
                    </div>

                    [@spring.bind "user.password" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="password">密码</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="password" name="password" type="password" />
                            [#if mc.hasError()]
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-remove-circle"></i>
                            </span>
                            [/#if]
                            [@spring.showErrors "" "help-block" /]
                        </div>
                    </div>

                    [@spring.bind "user.confirmPassword" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="confirmPassword">确认密码</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="confirmPassword" name="confirmPassword" type="password" />
                            [#if mc.hasError()]
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-remove-circle"></i>
                            </span>
                            [/#if]
                            [@spring.showErrors "" "help-block" /]
                        </div>
                    </div>

                    [@spring.bind "user.realName" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="realName">姓名</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="realName" name="realName" type="text" value="${(user.realName)!}" />
                            [#if mc.hasError()]
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-remove-circle"></i>
                            </span>
                            [/#if]
                            [@spring.showErrors "" "help-block" /]
                        </div>
                    </div>

                    [@spring.bind "user.parentUsername" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="parentUsername">父级用户</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="parentUsername" name="parentUsername" type="text" value="${(user.parentUsername)!}" />
                            [#if mc.hasError()]
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-remove-circle"></i>
                            </span>
                            [/#if]
                            [@spring.showErrors "" "help-block" /]
                        </div>
                    </div>

                    [@spring.bind "user.available" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="available">状态</label>
                        <div class="col-sm-10">
                            <select id="available" name="available" class="form-control">
                                <option [#if user.available]selected="selected"[/#if] value="true">可用</option>
                                <option [#if !user.available]selected="selected"[/#if] value="false">禁用</option>
                            </select>
                            [#if mc.hasError()]
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-remove-circle"></i>
                            </span>
                            [/#if]
                            [@spring.showErrors "" "help-block" /]
                        </div>
                    </div>

                    [@spring.bind "user.allRoleList" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="available">角色</label>
                        <div class="col-sm-10">
                            [#list user.allRoleList as role]
                                [#assign checked = '' /]
                                [#list user.roleIds as id]
                                    [#if role.id == id]
                                        [#assign checked = 'checked' /]
                                        [#break /]
                                    [/#if]
                                [/#list]
                                <div class="checkbox">
                                    <label>
                                    <input type="checkbox" name="roleIds" value="${(role.id)!}" ${checked} />
                                    ${(role.role)!}
                                    </label>
                                </div>
                            [/#list]
                        </div>
                    </div>

                    <button type="submit" class="btn btn-success">
                        <i class="glyphicon glyphicon-refresh"></i>创建
                    </button>
                    <a href="/permission/list" class="btn btn-default">
                        <i class="glyphicon glyphicon-remove"></i>取消
                    </a>
                </fieldset>
            </form>
        </div>
    </div>
</div>
[/@override]

[@extends name="/decorator.ftl" /]