[#assign shiro = JspTaglibs["/WEB-INF/shiro.tld"]]
<!DOCTYPE html>
<!--
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         佛祖保佑       永无BUG
-->
<html lang="zh_cn">
<head>
[@block name="Meta"]
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta name="description" content=""/>
    <meta name="author" content="EthanTu"/>
[/@block]

    <title>和包POS-[@block name="title"][/@block]</title>
    <link rel="shortcut icon" href="[@spring.url '/resources/images/favicon.ico' /]" type="image/x-icon"/>

[@block name="topResources"]
    <!-- Bootstrap -->
    <link href="[@spring.url '/resources/bootstrap/css/bootstrap.min.css' /]" rel="stylesheet" media="screen"/>
    <link href="[@spring.url '/resources/css/styles.css' /]" rel="stylesheet" media="screen"/>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script type="text/javascript" src="[@spring.url '/resources/js/jquery-1.11.1.js' /]"></script>
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script type="text/javascript" src="[@spring.url '/resources/bootstrap/js/html5shiv-3.7.0.min.js'/]"></script>
    <script type="text/javascript" src="[@spring.url '/resources/bootstrap/js/respond-1.3.0.min.js'/]"></script>
    <![endif]-->
[/@block]
</head>

[@block name="specialBody"]
<body>
[/@block]

[@block name="header"]
    [#include "/shared/header.ftl" /]
[/@block]

[@block name="content"]
<div class="page-content">
    <div class="row">
        <div class="col-md-2">
            <div class="sidebar content-box" style="display: block;">
                <ul class="nav">
                    <!-- Main menu -->
                        [#--[@shiro.hasRole name="administrator"]--]
                        <li>
                            <a href="[@spring.url '/dashboard' /]">
                                <i class="glyphicon glyphicon-home"></i>状态面板
                            </a>
                        </li>

                        <li>
                            <a href="[@spring.url '/voucher' /]">
                                <i class="glyphicon glyphicon-home"></i>电子卷管理
                            </a>
                            <ul>
                                <li><a href="[@spring.url '/voucher/list' /]">电子卷列表</a></li>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="#">
                                <i class="glyphicon glyphicon-user"></i>用户管理
                            </a>
                            <ul>
                                <li><a href="[@spring.url '/user/list' /]">用户列表</a></li>
                                <li><a href="[@spring.url '/user/review' /]">用户审核</a></li>
                                <li><a href="[@spring.url '/user/setParent' /]">用户分配</a></li>
                                <li><a href="[@spring.url '/user/create' /]">创建用户</a></li>
                            </ul>
                        </li>

                        <li class="submenu">
                            <a href="#">
                                <i class="glyphicon glyphicon-lock"></i>授权管理
                            </a>
                            <ul>
                                <li><a href="[@spring.url '/role/list' /]">角色列表</a></li>
                                <li><a href="[@spring.url '/permission/list' /]">权限列表</a></li>
                                <li><a href="[@spring.url '/role/create' /]">创建角色</a></li>
                                <li><a href="[@spring.url '/permission/create' /]">创建权限</a></li>
                            </ul>
                        </li>
                        [#--[/@shiro.hasRole]--]
                </ul>
            </div>
        </div>

        <!-- content -->
        <div class="col-md-10">
            [@block name="subContent"]

            [/@block]
        </div>
    </div>
</div>
[/@block]

[@block name="footer"]
[#--[#include "/shared/footer.ftl" /]--]
[/@block]

[@block name="bottomResources"]
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script type="text/javascript" src="[@spring.url '/resources/bootstrap/js/bootstrap.min.js' /]"></script>
<script type="text/javascript" src="[@spring.url '/resources/js/custom.js' /]"></script>
[/@block]
</body>
</html>

