package me.ethantu.posapp.domain.service.permission;

import me.ethantu.posapp.domain.model.permission.IPermissionRepository;
import me.ethantu.posapp.domain.model.permission.Permission;
import me.ethantu.posapp.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/28/14
 * Time: 3:57 PM
 */
@Service("permissionService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PermissionService implements IPermissionService {

    @Autowired
    private IPermissionRepository<Permission, Long> permissionRepository;

    @Override
    public void create(Permission permission) {
//        Date localUTC = new Date(0);         //1970-1-1-8-0-0
//        Date now = new Date();
//        user.setSignedDate(localUTC);
//        user.setLastSignedDate(localUTC);
//        user.setCreatedDate(now);
//        user.setUpdatedDate(now);

        permissionRepository.save(permission);
    }

    @Override
    public void edit(Permission permission) {
        permissionRepository.update(permission);
    }

    @Override
    public void delete(Permission permission) {
        permissionRepository.delete(permission);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Pagination<Permission> pagination(int page, int pageSize) {
        return permissionRepository.pagination(page, pageSize, null, null, null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Permission findById(Long id) {
        return permissionRepository.getById(id, false);
    }

    @Override
    public List<Permission> findAll() {
        Map<String, Object> parameterMap = new HashMap();
        parameterMap.put("available", Boolean.TRUE);
        return permissionRepository.findAllByProperties(parameterMap);
    }
}
