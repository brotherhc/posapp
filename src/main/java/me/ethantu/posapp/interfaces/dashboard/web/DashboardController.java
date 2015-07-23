package me.ethantu.posapp.interfaces.dashboard.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 7/16/14
 * Time: 10:51 PM
 */
@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @RequestMapping
    public String index() throws Exception {
        return "/dashboard/dashboard";
    }

}
