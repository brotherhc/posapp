package me.ethantu.posapp.domain.service.voucher;

import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.domain.model.voucher.Voucher;
import me.ethantu.posapp.domain.model.voucher.IVoucherRepository;
import me.ethantu.posapp.domain.service.UnAvailableException;
import me.ethantu.posapp.domain.service.user.IUserService;
import me.ethantu.posapp.persistence.hibernate.generic.Pagination;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 2015/7/15
 * Time: 4:37
 */
@Service("voucherService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class VoucherService implements IVoucherService {

    @Autowired
    private IVoucherRepository<Voucher,Long> repository;

    @Autowired
    private IUserService userService;

    @Override
    public void create(Voucher voucher) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        User user = userService.findByUsername(username);
        if (null == user) {
            throw new UnAvailableException();
        }
        voucher.setOwner(user);
        voucher.setCreatedDate(new Date());
        repository.save(voucher);
    }

    @Override
    public Pagination<Voucher> pagination(int page, int pageSize) {
        Map<String, FetchMode> fetchModeMap = new HashMap<String, FetchMode>();
        fetchModeMap.put("owner", FetchMode.JOIN);
        Order[] orders = new Order[]{Order.desc("createdDate")};
        return repository.pagination(page, pageSize, null, null, orders, fetchModeMap);
    }

    @Override
    public String export(Date createdDate, Integer status) {
        List<Voucher> list = repository.export(createdDate, status);
        StringBuilder stringBuilder = new StringBuilder();
        if (null != list) {
            for (Voucher voucher : list) {
                voucher.setStatus(1);
                voucher.setRemark("已导出");
                repository.update(voucher);
                stringBuilder.append(voucher.getMobileNo() + " ");
                stringBuilder.append(voucher.getPassword() + " ");
                stringBuilder.append(voucher.getAmount() + " ");
                stringBuilder.append(voucher.getOwner().getUsername() + " ");
                stringBuilder.append(voucher.getRemark() + " ");
                stringBuilder.append(voucher.getConsumedDate() + " ");
                stringBuilder.append(voucher.getCreatedDate() + " ");
                stringBuilder.append(voucher.getStatus() + "\r\n");
            }
        }
        return stringBuilder.toString();
    }


}
