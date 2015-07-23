[@override name="title"]
登录
[/@override]

[@override name="specialBody"]
<body class="login-bg">
[/@override]

[@override name="header"]
<div class="header">
    <div class="container">
        <div class="row">
            <div class="col-sm-22">
                <!-- Logo -->
                <div class="logo">
                    <h1><a href="/">和包POS管理系统</a></h1>
                </div>
            </div>
        </div>
    </div>
</div>
[/@override]

[@override name="content"]
    <div class="page-content container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-wrapper">
                    <div class="box">
                        <div class="content-wrap">
                            <h6>登录</h6>
                            <hr/>
                            <form id="loginForm" method="post" action="/login">
                                [@mc.showAlert /]
                                <input class="form-control" type="text" name="username" placeholder="用户名" value="${loginFailedCommand.username!}" />
                                <input class="form-control" type="password" name="password" placeholder="密码" />
                                <div class="col-sm-7">
                                    <input class="form-control" type="text" name="verificationCode" placeholder="验证码"/>
                                </div>

                                <div class="col-sm-5">
                                    [@mc.verificationCode /]
                                </div>
                                <div class="action">
                                    <input class="btn btn-primary signup" type="submit" value="登录">
                                    <!--<a class="btn btn-primary signup" type="submit" href="javascript:document.getElementById('loginForm').submit();">登录</a>-->
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="already">
                        <p>还没有账号?</p>
                        <a href="/register">注册</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
[/@override]

[@override name="footer"]

[/@override]
[@extends name="/decorator.ftl"/]