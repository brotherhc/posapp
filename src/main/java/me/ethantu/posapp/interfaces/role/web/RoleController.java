package me.ethantu.posapp.interfaces.role.web;


import me.ethantu.posapp.commons.utils.PaginationUtils;
import me.ethantu.posapp.domain.model.permission.Permission;
import me.ethantu.posapp.domain.model.role.Role;
import me.ethantu.posapp.domain.service.permission.IPermissionService;
import me.ethantu.posapp.domain.service.role.IRoleService;
import me.ethantu.posapp.interfaces.shared.web.AlertCommand;
import org.apache.commons.beanutils.BeanUtils;
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

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/31/14
 * Time: 3:06 PM
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    private Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping
    public String index() throws Exception {
        return "redirect:/role/list";
    }

    @RequestMapping("/list")
    public ModelAndView list(AlertCommand alertCommand, String pageString, String pageSizeString) throws Exception {
        int page = PaginationUtils.verifyPage(pageString);
        int pageSize = PaginationUtils.verifyPageSize(pageSizeString, 10);
        return new ModelAndView("/role/list", "pagination", roleService.pagination(page, pageSize))
                .addObject(AlertCommand.ALERT_COMMAND_KEY, alertCommand);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() throws Exception {
        CreateCommand createCommand = new CreateCommand();
        createCommand.setAllPermissionList(permissionService.findAll());
        return new ModelAndView("/role/create", "role", createCommand);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView save(@Valid @ModelAttribute("role") CreateCommand createCommand,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
            createCommand.setAllPermissionList(permissionService.findAll());
            return new ModelAndView("/role/create", "user", createCommand);
        }

//        createCommand.setPassword(DigestUtils.md5Hex(createCommand.getPassword()).toUpperCase());
        Role roleInstance = new Role();
        BeanUtils.copyProperties(roleInstance, createCommand);

        Long[] permissionIds = createCommand.getPermissionIds();
        if (null != permissionIds) {
            Set<Permission> permissionSet = new HashSet<Permission>();
            for (Long id : permissionIds) {
                permissionSet.add(permissionService.findById(id));
            }
            roleInstance.setPermissionSet(permissionSet);
        }

        roleService.create(roleInstance);

        redirectAttributes.addAttribute("id", roleInstance.getId());
        return new ModelAndView("redirect:/role/show/{id}");
    }

    @RequestMapping("/show/{id}")
    public ModelAndView show(@PathVariable(value = "id") Long id, Locale locale) throws Exception {
        Role roleInstance = roleService.findById(id, true);

        if (null == roleInstance) {
            String warnInfo = messageSource.getMessage("default.not.found.message", new Object[]{id}, locale);
            return new ModelAndView("redirect:/role/list",
                    AlertCommand.ALERT_COMMAND_KEY, new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        return new ModelAndView("/role/show", "role", roleInstance);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(value = "id") Long id, Locale locale) throws Exception {
        Role roleInstance = roleService.findById(id, true);

        if (null == roleInstance) {
            String warnInfo = messageSource.getMessage("default.not.found.message", new Object[]{id}, locale);
            return new ModelAndView("redirect:/role/list",
                    AlertCommand.ALERT_COMMAND_KEY, new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        EditCommand editCommand = new EditCommand();
        BeanUtils.copyProperties(editCommand, roleInstance);
        editCommand.setAllPermissionList(permissionService.findAll());

        return new ModelAndView("/role/edit", "role", editCommand);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable(value = "id") Long id, @Valid @ModelAttribute("user") EditCommand editCommand,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes, Locale locale) throws Exception {

        Role roleInstance = roleService.findById(id, true);

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/role/edit", "role", editCommand);
        }

        if (roleInstance == null) {
            String warnInfo = messageSource.getMessage("default.not.found.message",
                    new Object[]{id}, locale);
            return new ModelAndView("redirect:/role/list",
                    AlertCommand.ALERT_COMMAND_KEY, new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        if (roleInstance.getVersion() > editCommand.getVersion()) {
            String warnInfo = messageSource.getMessage("default.optimistic.locking.failure",
                    new Object[]{id}, locale);
            return new ModelAndView("/role/edit", "role", editCommand)
                    .addObject(AlertCommand.ALERT_COMMAND_KEY, new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        List<Permission> allPermissions = permissionService.findAll();
        editCommand.setAllPermissionList(allPermissions);

        roleInstance.setRole(editCommand.getRole());
        roleInstance.setDescription(editCommand.getDescription());
        roleInstance.setAvailable(editCommand.getAvailable());
        roleInstance.setVersion(editCommand.getVersion());

        Long[] permissionIds = editCommand.getPermissionIds();
        if (null != permissionIds) {
            Set<Permission> permissionSet = roleInstance.getPermissionSet();
            permissionSet.clear();
            for (Long permissionId : editCommand.getPermissionIds()) {
                permissionSet.add(permissionService.findById(permissionId));
            }
        }

        //TODO catch exception
        roleService.edit(roleInstance);

        redirectAttributes.addAttribute("id", roleInstance.getId());
        return new ModelAndView("redirect:/role/show/{id}");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable(value = "id") Long id, Locale locale) throws Exception {

        Role roleInstance = roleService.findById(id, false);

        if (roleInstance == null) {
            String warnInfo = messageSource.getMessage("default.not.found.message",
                    new Object[]{id}, locale);
            return new ModelAndView("redirect:/role/list",
                    AlertCommand.ALERT_COMMAND_KEY, new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        //TODO catch exception
        roleService.delete(roleInstance);
        return new ModelAndView("redirect:/role/list");
    }
}
