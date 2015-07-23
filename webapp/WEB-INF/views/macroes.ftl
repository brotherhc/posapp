[#-- always return false if call spring.bind before using function--]
[#function hasError]
    [#if spring.status.errorMessages?size > 0]
        [#return true]
    [/#if]
    [#return false]
[/#function]

[#-- warnInfo defined in Constants.WARN_INFO--]
[#macro showAlert]
    [#if alertCommand?? && (alertCommand.type)??]
        [#switch alertCommand.type.getValue()]
            [#case 0]
                [#local alertType = "success"]
                [#break]
            [#case 1]
                [#local alertType = "info"]
                [#break]
            [#case 2]
                [#local alertType = "warning"]
                [#break]
            [#case 3]
                [#local alertType = "danger"]
                [#break]
        [/#switch]
        <div class="alert alert-${alertType}">
            <a class="close" data-dismiss="alert" href="#">×</a>
            <strong>${alertCommand.type.getName()}!</strong>
            ${alertCommand.content}
        </div>
    [/#if]
[/#macro]

[#--//TODO with parameters--]
[#macro showPagination path]
<div class="row">
    [#local totalPage = (pagination.count / pagination.pageSize)?ceiling]

    [#if path?contains("?")]
        [#local basePath = path + '&pageSize=' + pagination.pageSize + '&page=']
    [#else]
        [#local basePath = path + '?pageSize=' + pagination.pageSize + '&page=']
    [/#if]

    [#if pagination.data?size > 0]
    <div class="col-xs-6">
        <div class="dataTables_info">总计 ${pagination.count} 条数据, 每页显示${pagination.pageSize}条,总计 ${totalPage}页</div>
    </div>
    <div class="col-xs-6">
        <div class="dataTables_paginate paging_bootstrap">
            <ul class="pagination">
                [#if pagination.page - 1 <= 0]
                    <li class="prev disabled"><a href="#">← 上一页</a></li>
                [#else]
                    <li class="prev"><a href="[@spring.url basePath + (pagination.page - 1)/]">← 上一页</a></li>
                [/#if]

                [#list 1..totalPage as index]
                    [#if totalPage < 11]
                        [#if pagination.page == index]
                            <li class="active"><a href="#">${index}</a></li>
                        [#else]
                            <li><a href="[@spring.url basePath + index/]">${index}</a></li>
                        [/#if]
                    [#else]
                        [#if (index > (pagination.page - 5)) && (index < (pagination.page + 4))]
                            [#if pagination.page == index]
                                <li class="active"><a href="#">${index}</a></li>
                            [#else]
                                <li><a href="[@spring.url basePath + index/]">${index}</a></li>
                            [/#if]
                        [/#if]
                    [/#if]
                [/#list]

                [#if pagination.page == totalPage]
                    <li class="next disabled"><a href="#">下一页 →</a></li>
                [#else]
                    <li class="next"><a href="[@spring.url basePath + (pagination.page + 1)/]">下一页 →</a></li>
                [/#if]
            </ul>
        </div>
    </div>
    [#else]
    <div class="col-md-2 col-md-offset-5">
        <Strong>没有查询到数据</Strong>
    </div>
    [/#if]
</div>
[/#macro]

[#macro verificationCode id="verificationCode"]
<img id="${id}Img" src="[@spring.url '/verificationCode'/]" title="点击可切换" class="form-control verificationCode"/>
<script type="text/javascript">
    $(document).ready(function(){
        $("#${id}" + "Img").on('click', function(e){
            var act = "[@spring.url '/verificationCode'/]";
            $(this).attr("src",act+"?"+new Date().getTime());
        });
    });
</script>
[/#macro]