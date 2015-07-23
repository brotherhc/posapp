[@override name="subContent"]
<div class="col-md-12 panel-info">
    [@mc.showAlert /]

    <div class="content-box-header panel-heading">
        <div class="panel-title">权限管理</div>
    </div>

    <div class="content-box-large box-with-header">
        <div class="panel-body">
            <legend>编辑权限</legend>
            <form class="form-horizontal" action="/permission/edit/${permission.id}" method="post">
                <fieldset>
                    <input id="version" name="version" type="hidden" value="${permission.version}" />

                    [@spring.bind "permission.permission" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="permission">权限名称</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="permission" name="permission" type="text" value="${(permission.permission)!}" />
                            [#if mc.hasError()]
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-remove-circle"></i>
                            </span>
                            [/#if]
                            [@spring.showErrors "" "help-block" /]
                        </div>
                    </div>

                    [@spring.bind "permission.description" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="description">描述</label>
                        <div class="col-sm-10">
                            <input class="form-control" id="description" name="description" type="text" value="${(permission.description)!}" />
                            [#if mc.hasError()]
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-remove-circle"></i>
                            </span>
                            [/#if]
                            [@spring.showErrors "" "help-block" /]
                        </div>
                    </div>

                    [@spring.bind "permission.available" /]
                    <div class="form-group [#if mc.hasError()]has-error[/#if]">
                        <label class="col-md-1 control-label" for="available">状态</label>
                        <div class="col-md-10">
                            <select id="available" name="available" class="form-control">
                                <option [#if permission.available]selected="selected"[/#if] value="true">可用</option>
                                <option [#if !permission.available]selected="selected"[/#if] value="false">禁用</option>
                            </select>
                            [#if mc.hasError()]
                            <span class="input-group-addon">
                                <i class="glyphicon glyphicon-remove-circle"></i>
                            </span>
                            [/#if]
                            [@spring.showErrors "" "help-block" /]
                        </div>
                    </div>

                    <button type="submit" class="btn btn-info">
                        <i class="glyphicon glyphicon-refresh"></i>更新
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