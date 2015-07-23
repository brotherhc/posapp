package me.ethantu.posapp.interfaces.voucher.web;

import me.ethantu.posapp.commons.utils.DateUtils;
import me.ethantu.posapp.commons.utils.PaginationUtils;
import me.ethantu.posapp.domain.service.voucher.IVoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 2015/7/15
 * Time: 4:52
 */
@Controller
@RequestMapping("/voucher")
public class VoucherController {

    @Autowired
    private IVoucherService voucherService;

    @RequestMapping
    public String index() throws Exception {
        return "redirect:/voucher/list";
    }

    @RequestMapping("/list")
    public ModelAndView list(String page, String pageSize) throws Exception {
        int pageInt = PaginationUtils.verifyPage(page);
        int pageSizeInt = PaginationUtils.verifyPageSize(pageSize, 10);
        return new ModelAndView("/voucher/list", "pagination", voucherService.pagination(pageInt, pageSizeInt));
    }


    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public ResponseEntity<byte[]> export(String createdDate, Integer status) throws Exception {
        Date sDate = null;
        if (null != createdDate && !"".equals(createdDate)) {
            try {
                createdDate += " 00:00:00";
                sDate = DateUtils.dateFormatParse(createdDate, "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
               e.printStackTrace();
            }

        }
        String export = voucherService.export(sDate, status);
        //for chinese
        String fileName = URLEncoder.encode("voucher.txt", "UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl("no-cache");
        headers.setPragma("no-cache");
        headers.setExpires(-1);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        return new ResponseEntity<byte[]>(export.getBytes(), headers, HttpStatus.OK);
    }
}
