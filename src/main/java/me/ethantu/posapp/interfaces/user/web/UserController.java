package me.ethantu.posapp.interfaces.user.web;



import me.ethantu.posapp.commons.shiro.PasswordHelper;
import me.ethantu.posapp.commons.utils.PaginationUtils;
import me.ethantu.posapp.domain.model.role.Role;
import me.ethantu.posapp.domain.model.user.User;
import me.ethantu.posapp.domain.service.role.IRoleService;
import me.ethantu.posapp.domain.service.user.IUserService;
import me.ethantu.posapp.interfaces.shared.web.AlertCommand;
import me.ethantu.posapp.persistence.hibernate.generic.Pagination;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-10-28
 * Time: PM6:37
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private PasswordHelper passwordHelper;

    @RequestMapping
    public String index() throws Exception {
        return "redirect:/user/list";
    }

    @RequestMapping("/list")
    public ModelAndView list(AlertCommand alertCommand, String page, String pageSize, String parent) throws Exception {
        return new ModelAndView("/user/list", "pagination", userService.pagination(PaginationUtils.verifyPage(page),
                PaginationUtils.verifyPageSize(pageSize, 10), parent))
                .addObject(AlertCommand.ALERT_COMMAND_KEY, alertCommand)
                .addObject("parent", parent);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() throws Exception {
        CreateCommand createCommand = new CreateCommand();
        createCommand.setAllRoleList(roleService.findAll());

        return new ModelAndView("/user/create", "user", createCommand);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView save(@Valid @ModelAttribute("user") CreateCommand createCommand,
        BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

        if (!createCommand.getConfirmPassword().equals(createCommand.getPassword())) {
            bindingResult.rejectValue("confirmPassword", "CreateCommand.confirmPassword.error.message");
        }

        if (bindingResult.hasErrors()) {
            createCommand.setAllRoleList(roleService.findAll());
            return new ModelAndView("/user/create", "user", createCommand);
        }

//        createCommand.setPassword(DigestUtils.md5Hex(createCommand.getPassword()).toUpperCase());
        User userInstance = new User();
        BeanUtils.copyProperties(userInstance, createCommand);

        String parentUsername = createCommand.getParentUsername();
        if (!StringUtils.isEmpty(parentUsername)) {
            User parentUser = userService.findByUsername(parentUsername);
            userInstance.setParent(parentUser);
        }

        Long[] roleIds = createCommand.getRoleIds();
        if (null != roleIds) {
            Set<Role> roleSet = new HashSet<Role>();
            for (Long id : roleIds) {
                 roleSet.add(roleService.findById(id, false));
            }
            userInstance.setRoleSet(roleSet);
        }

        userService.create(userInstance);

        redirectAttributes.addAttribute("id", userInstance.getId());
        return new ModelAndView("redirect:/user/show/{id}");
    }

    @RequestMapping("/show/{id}")
    public ModelAndView show(@PathVariable(value = "id") Long id, Locale locale) throws Exception {
        User userInstance = userService.findById(id, true);

        if (null == userInstance) {
            String warnInfo = messageSource.getMessage("default.not.found.message", new Object[]{id}, locale);
            return new ModelAndView("redirect:/user/list",
                    AlertCommand.ALERT_COMMAND_KEY, new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        return new ModelAndView("/user/show", "user", userInstance);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(value = "id") Long id, Locale locale) throws Exception {
        User userInstance = userService.findById(id, true);

        if (null == userInstance) {
            String warnInfo = messageSource.getMessage("default.not.found.message", new Object[]{id}, locale);
            return new ModelAndView("redirect:/user/list",
                    AlertCommand.ALERT_COMMAND_KEY, new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        EditCommand editCommand = new EditCommand();
        BeanUtils.copyProperties(editCommand, userInstance);
        editCommand.setAllRoleList(roleService.findAll());
        return new ModelAndView("/user/edit", "user", editCommand);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable(value = "id") Long id, @Valid @ModelAttribute("user") EditCommand editCommand,
        BindingResult bindingResult, RedirectAttributes redirectAttributes, Locale locale) throws Exception {

        User userInstance = userService.findById(id, true);

        if (bindingResult.hasErrors()) {
            editCommand.setAllRoleList(roleService.findAll());
            return new ModelAndView("/user/edit", "user", editCommand);
        }

        if (userInstance == null) {
            String warnInfo = messageSource.getMessage("default.not.found.message",
                    new Object[]{id}, locale);
            return new ModelAndView("redirect:/user/list",
                    AlertCommand.ALERT_COMMAND_KEY, new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        if (userInstance.getVersion() > editCommand.getVersion()) {
            String warnInfo = messageSource.getMessage("default.optimistic.locking.failure",
                                new Object[]{id}, locale);
            return new ModelAndView("/user/edit", "user", editCommand)
                    .addObject(AlertCommand.ALERT_COMMAND_KEY, new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        List<Role> allRoles = roleService.findAll();
        editCommand.setAllRoleList(allRoles);

        boolean encryptPassword = false;
        if (!StringUtils.isEmpty(editCommand.getPassword())) {
            if (passwordHelper.equalsPassword(userInstance, editCommand.getPassword())) {
                if (!StringUtils.isEmpty(editCommand.getNewPassword()) &&
                        !StringUtils.isEmpty(editCommand.getConfirmNewPassword())) {
                    if (!editCommand.getNewPassword().equals(editCommand.getConfirmNewPassword())) {
                        LOGGER.debug("Valid new password.");
                        bindingResult.rejectValue("confirmNewPassword", "EditCommand.confirmNewPassword.error.message");
                        return new ModelAndView( "/user/edit", "user", editCommand);
                    } else {
                        //md5 newPassword for copy properties
//                        editCommand.setPassword(DigestUtils.md5Hex(editCommand.getNewPassword()).toUpperCase());
                        userInstance.setPassword(editCommand.getNewPassword());
                        encryptPassword = true;
                    }
                }
            } else {
                LOGGER.debug("Valid password.");
                bindingResult.rejectValue("password", "EditCommand.password.error.message");
                return new ModelAndView( "/user/edit", "user", editCommand);
            }
        }

        //ignore reset password
        LOGGER.debug("Ignore reset password.");
        userInstance.setAvailable(editCommand.isAvailable());
        userInstance.setVersion(editCommand.getVersion());

        Long[] roleIds = editCommand.getRoleIds();
        if (null != roleIds) {
            Set<Role> roleSet = userInstance.getRoleSet();
            roleSet.clear();
            for (Long roleId : editCommand.getRoleIds()) {
                 roleSet.add(roleService.findById(roleId, false));
            }
        }
         //TODO catch exception
        userService.edit(userInstance, encryptPassword);

        redirectAttributes.addAttribute("id", userInstance.getId());
        return new ModelAndView("redirect:/user/show/{id}");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable(value = "id") Long id, Locale locale) throws Exception {

        User userInstance = userService.findById(id, false);

        if (userInstance == null) {
            String warnInfo = messageSource.getMessage("default.not.found.message",
                    new Object[]{id}, locale);
            return new ModelAndView("redirect:/user/list",
                    AlertCommand.ALERT_COMMAND_KEY, new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

         //TODO catch exception
        userService.delete(userInstance);
        return new ModelAndView("redirect:/user/list");
    }
}


