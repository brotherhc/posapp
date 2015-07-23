/**
 * Created by liwenhe on 14-12-11.
 */
$(function(){
    /*导航栏滑动条   --开始--*/
    var add_slide_bar = function(index){
        var tag = $(".mainNav li")[index];
        if(!$(tag).hasClass("mainNav-seperator") && !$(tag).children("div").hasClass(".nav-slide-seperator")){
            $(tag).append(function(){
                return "<div class=" + "nav-slide-seperator" +
                    "><img src=" + "../../../../resources/images/nav-slide-seperator.png" +
                    "></div>";
            });
        }
    }

    var remove_slide_bar = function(index){
        var tag = $(".mainNav li")[index];
        if(!$(tag).children("div").hasClass(".nav-slide-seperator")){
            $(tag).children(".nav-slide-seperator").remove();
        }
    }

    $(".mainNav li").bind({
/*        mouseenter:function(){
            add_slide_bar($(this).index());
            $(".current").next().remove();
        },
        mouseleave:function(){
            remove_slide_bar($(this).index());
            add_slide_bar($(".current").parent().index());
        },
        mousedown:function(){
            $(".current").removeClass("current");
            $(this).find("a").addClass("current");
            console.log($(this));
        }*/
    });
    /*导航栏滑动条   --结束--*/

    /*针对IE8  --开始--*/
    var isIE8 = function(){
        var userAgent = window.navigator.userAgent;
        if(userAgent.indexOf("MSIE 8.0") > 0){
            return true;
        }
        return false;
    }

/*    if(isIE8()){
        $(".loginwarp").css({"background":"none",
            "filter":"progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true, sizingMethod=scale, src='../../../resources/images/login_bgimg.png')"
            });
    }*/

    /*针对IE8  --结束--*/
});