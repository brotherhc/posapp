package me.ethantu.posapp.domain.service.role;

import me.ethantu.posapp.domain.model.role.IRoleRepository;
import me.ethantu.posapp.domain.model.role.Role;
import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.persistence.hibernate.generic.Pagination;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/28/14
 * Time: 3:57 PM
 */
@Service("roleService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class RoleService implements IRoleService {

    @Autowired
    private IRoleRepository<Role, Long> roleRepository;

    @Override
    public void create(Role role) {
//        Date localUTC = new Date(0);         //1970-1-1-8-0-0
//        Date now = new Date();
//        user.setSignedDate(localUTC);
//        user.setLastSignedDate(localUTC);
//        user.setCreatedDate(now);
//        user.setUpdatedDate(now);

        roleRepository.save(role);
    }

    @Override
    public void edit(Role role) {
        roleRepository.update(role);
    }

    @Override
    public void delete(Role role) {
        roleRepository.delete(role);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Pagination<Role> pagination(int page, int pageSize) {
        return roleRepository.pagination(page, pageSize, null, null, null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Role findById(Long id, boolean initializePermissionSet) {
        Role role = roleRepository.getById(id, false);
        if (role != null && initializePermissionSet) {
            Hibernate.initialize(role.getPermissionSet());
        }
        return role;
    }

    @Override
    public List<Role> findAll() {
        Map<String, Object> parameterMap = new HashMap();
        parameterMap.put("available", Boolean.TRUE);
        return roleRepository.findAllByProperties(parameterMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Role findByRole(String role) {
        Map<String, Object> parameterMap = new HashMap();
        parameterMap.put("role", role);
        return roleRepository.findByProperties(parameterMap);
    }

    @Override
    public Set<User> findAccountAdministrator() {
        return roleRepository.findAccountAdmin();
    }
}
