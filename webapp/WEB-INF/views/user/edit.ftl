[@override name="subContent"]
<div class="col-md-12 panel-info">
    [@mc.showAlert /]

    <div class="content-box-header panel-heading">
        <div class="panel-title">用户管理</div>
    </div>

    <div class="content-box-large box-with-header">
        <div class="panel-body">
            <legend>编辑用户</legend>
            <form class="form-horizontal" action="/user/edit/${user.id}" method="post">
                <fieldset>
                    <input id="version" name="version" type="hidden" value="${user.version}" />

                    [@spring.bind "user.username" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="username">用户名称</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="username" type="text" value="${(user.username)!}" disabled="disabled"/>
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

                    [@spring.bind "user.newPassword" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="newPassword">新密码</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="newPassword" name="newPassword" type="password" />
                            [#if mc.hasError()]
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-remove-circle"></i>
                            </span>
                            [/#if]
                            [@spring.showErrors "" "help-block" /]
                        </div>
                    </div>


                    [@spring.bind "user.confirmNewPassword" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="confirmNewPassword">确认新密码</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="confirmNewPassword" name="confirmNewPassword" type="password" />
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
                        <div class="col-md-10">
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

                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="available">角色</label>
                        <div class="col-md-10">
                            [#list user.allRoleList as role]
                                [#assign checked = ''/]
                                [#if user.roleIds]
                                    [#list user.roleIds as id]
                                        [#if role.id == id]
                                            [#assign checked = 'checked'/]
                                            [#break /]
                                        [/#if]
                                    [/#list]
                                [#else]
                                    [#list user.roleSet as oldRole]
                                        [#if role.id == oldRole.id]
                                            [#assign checked = 'checked'/]
                                            [#break /]
                                        [/#if]
                                    [/#list]
                                [/#if]
                                <div class="checkbox">
                                    <label>
                                    <input type="checkbox" name="roleIds" value="${(role.id)!}" ${checked} />
                                    ${(role.description)!} - ${(role.role)!}
                                    </label>
                                </div>
                            [/#list]
                        </div>
                    </div>


                    <button type="submit" class="btn btn-info">
                        <i class="glyphicon glyphicon-refresh"></i>更新
                    </button>
                    <a href="/user/list" class="btn btn-default">
                        <i class="glyphicon glyphicon-remove"></i>取消
                    </a>
                </fieldset>
            </form>
        </div>
    </div>
</div>
[/@override]

[@extends name="/decorator.ftl" /]