[@override name="subContent"]
<div class="col-md-12 panel-info">
    [@mc.showAlert /]

    <div class="content-box-header panel-heading">
        <div class="panel-title">电子卷管理</div>
    </div>

    <div class="content-box-large box-with-header">
        <div class="panel-body">
            <legend>电子卷列表</legend>
            <form action="/voucher/export" target="_blank" method="post">
                <div class="col-md-4">
                    <label for="createdDate">创建日期</label>
                    <input class="form-control" type="date" id="createdDate" name="createdDate" value="">
                </div>
                <div class="col-md-3">
                    <label for="status" class="control-label">状态：</label>
                    <select id="status" name="status" class="form-control">
                        <option value="0">初始化</option>
                        <option value="1">已导出</option>
                        <option value="2">正在消费</option>
                        <option value="3">消费成功</option>
                        <option value="4">消费失败</option>
                    </select>
                </div>

                <div class="col-md-1">
                    <label for="btn-ok">&nbsp;</label>
                    <div id="btn-ok">
                        <button  type="submit" class="btn btn-info">
                            <i class="glyphicon glyphicon-search"></i>导出
                        </button>
                    </div>
                </div>
            </form>
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>手机号码</th>
                        <th>密码</th>
                        <th>金额</th>
                        <th>提交用户</th>
                        <th>备注</th>
                        <th>消费时间</th>
                        <th>创建时间</th>
                        <th>状态</th>
                        <th class="col-md-2">操作</th>
                    </tr>
                </thead>
                <tbody>
                [#if pagination.data?size > 0]
                    [#list pagination.data as voucher]
                    <tr>
                        <td>${voucher.id}</td>
                        <td>${voucher.mobileNo}</td>
                        <td>${voucher.password}</td>
                        <td>${voucher.amount}</td>
                        <td>${voucher.owner.username}</td>
                        <td>${voucher.remark}</td>
                        <td>${voucher.consumedDate}</td>
                        <td>${voucher.createdDate}</td>
                        <td>${voucher.status}</td>
                        <td>
                        </td>
                    </tr>
                    [/#list]
                [/#if]
                </tbody>
            </table>
            [@mc.showPagination "/voucher/list" /]
        </div>
    </div>
</div>
[/@override]

[@override name="bottomResources"]
    [@super /]
<link href="[@spring.url '/resources/vendors/datatables/dataTables.bootstrap.css' /]" rel="stylesheet" media="screen" />
[/@override]

[@extends name="/decorator.ftl" /]