package me.ethantu.posapp.domain.service.user;


import me.ethantu.posapp.commons.shiro.PasswordHelper;
import me.ethantu.posapp.domain.model.permission.Permission;
import me.ethantu.posapp.domain.model.role.Role;
import me.ethantu.posapp.domain.model.user.IUserRepository;
import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.persistence.hibernate.generic.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.hibernate.FetchMode;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-10-28
 * Time: PM7:38
 */
@Service("userService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class UserService implements IUserService {

    @Autowired
    private IUserRepository<User, Long> userRepository;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public void create(User user) {
        Date localUTC = new Date(0);         //1970-1-1-8-0-0
        Date now = new Date();
        user.setSignedDate(localUTC);
        user.setLastSignedDate(localUTC);
        user.setCreatedDate(now);
        user.setUpdatedDate(now);

        passwordHelper.encryptPassword(user);
        userRepository.save(user);
    }

    @Override
    public void edit(User user, boolean encryptPassword) {
        if (encryptPassword) {
            passwordHelper.encryptPassword(user);
        }

        user.setUpdatedDate(new Date());
        userRepository.update(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Set<String> findRoles(String username) {
        User user = userRepository.findByUsername(username);

        Set<String> allRole = new HashSet<String>();
        if (null != user) {
            Set<Role> roleSet = user.getRoleSet();
            if (null != roleSet) {
                for (Role role : roleSet) {
                    if (role.getAvailable()) {
                        allRole.add(role.getRole());
                    }
                }
            }
        }

        return allRole;
    }

    @Override
    public Set<String> findPermissions(String username) {
        User user = userRepository.findByUsername(username);

        Set<String> allPermission = new HashSet<String>();
        if (null != user) {
            Set<Role> roleSet = user.getRoleSet();
            if (null != roleSet) {
                for (Role role : roleSet) {
                    Set<Permission> permissionSet = role.getPermissionSet();
                    if (null != permissionSet) {
                        for (Permission permission : permissionSet) {
                            if (permission.getAvailable()) {
                                allPermission.add(permission.getPermission());
                            }
                        }
                    }
                }
            }
        }

        return allPermission;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Pagination<User> pagination(int page, int pageSize, String parent) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        Map<String, String> alias = null;

        if (null != parent && !StringUtils.isEmpty(parent)) {
            alias = new HashMap<String, String>();
            alias.put("parent", "parent");

            criterionList.add(Restrictions.like("parent.username", parent, MatchMode.ANYWHERE));
        }

        Criterion[] restrictions = null;
        int size = criterionList.size();
        if (size > 0) {
            restrictions = new Criterion[size];
            criterionList.toArray(restrictions);
        }

        Map<String, FetchMode> fetchModeMap = new HashMap<String, FetchMode>();
        fetchModeMap.put("parent", FetchMode.JOIN);

        return userRepository.pagination(page, pageSize, restrictions, alias, null, fetchModeMap);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public User findById(Long id, boolean initializeRoleSet) {
        User user = userRepository.getById(id, false);
        if (user != null && initializeRoleSet) {
            Hibernate.initialize(user.getRoleSet());
        }
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public boolean exist(String username, String email) {
        return userRepository.exist(username, email);
    }

    private String algorithmName = "md5";
    private int hashIterations = 2;


    public void setAlgorithmName(String algorithmName) {
        this.algorithmName = algorithmName;
    }

    public void setHashIterations(int hashIterations) {
        this.hashIterations = hashIterations;
    }

    public boolean resetPassword(String oldPwd, String newPwd) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        User user = userRepository.findByUsername(username);

        String oldPassword = new SimpleHash(
                algorithmName,
                oldPwd,
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();

        String newPassword = new SimpleHash(
                algorithmName,
                newPwd,
                ByteSource.Util.bytes(user.getCredentialsSalt()),
                hashIterations).toHex();

        if (user.getPassword().equals(oldPassword)) {
            user.setPassword(newPassword);
            user.setUpdatedDate(new Date());
            return userRepository.resetPwd(user);
        }
        return false;
    }

    @Override
    public User checkEmail(String name) {
        List<Criterion> lists = new ArrayList<Criterion>();
        lists.add(Restrictions.eq("email", name));

        Criterion[] restrictions = null;
        int size = lists.size();
        if (size > 0) {
            restrictions = new Criterion[size];
            lists.toArray(restrictions);
        }
        return userRepository.checkEmail(restrictions);
    }


    @Override
    public void updatePassword(Long id, String pwd) {
        User user = userRepository.getById(id, true);
        user.setPassword(pwd);
        passwordHelper.encryptPassword(user);
        userRepository.saveOrUpdate(user);
    }

    @Override
    public boolean setUserParent(String name, String parentuser) {
        User user = userRepository.findByUsername(name);
        User parent = userRepository.findByUsername(parentuser);
        if (null == user || null == parent) {
            return false;
        }
        user.setParent(parent);
        return userRepository.setParent(user);
    }


}
