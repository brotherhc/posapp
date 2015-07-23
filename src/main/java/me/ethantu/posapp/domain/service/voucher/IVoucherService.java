package me.ethantu.posapp.domain.service.voucher;

import me.ethantu.posapp.domain.model.voucher.Voucher;
import me.ethantu.posapp.persistence.hibernate.generic.Pagination;

import java.util.Date;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 2015/7/15
 * Time: 4:36
 */
public interface IVoucherService {

    void create(Voucher customerInfo);

    Pagination<Voucher> pagination(int page, int pageSize);

    String export(Date createdDate, Integer status);
}
