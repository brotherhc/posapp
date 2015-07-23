package me.ethantu.posapp.interfaces.permission.web;

import me.ethantu.posapp.commons.utils.PaginationUtils;
import me.ethantu.posapp.domain.model.permission.Permission;
import me.ethantu.posapp.domain.service.permission.IPermissionService;
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
import java.util.Locale;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 5/31/14
 * Time: 3:06 PM
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    private Logger LOGGER = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private MessageSource messageSource;


    @RequestMapping
    public String index() throws Exception {
        return "redirect:/permission/list";
    }

    @RequestMapping("/list")
    public ModelAndView list(AlertCommand alertCommand, String page, String pageSize) throws Exception {
        int pageInt = PaginationUtils.verifyPage(page);
        int pageSizeInt = PaginationUtils.verifyPageSize(pageSize, 10);
        return new ModelAndView("/permission/list", "pagination", permissionService.pagination(pageInt, pageSizeInt))
                .addObject(AlertCommand.ALERT_COMMAND_KEY, alertCommand);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() throws Exception {
        return new ModelAndView("/permission/create", "permission", new CreateCommand());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView save(@Valid @ModelAttribute("permission") CreateCommand createCommand,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) throws Exception {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/permission/create", "permission", createCommand);
        }

        Permission permissionInstance = new Permission();
        BeanUtils.copyProperties(permissionInstance, createCommand);
        permissionService.create(permissionInstance);

        redirectAttributes.addAttribute("id", permissionInstance.getId());
        return new ModelAndView("redirect:/permission/show/{id}");
    }

    @RequestMapping("/show/{id}")
    public ModelAndView show(@PathVariable(value = "id") Long id, Locale locale) throws Exception {
        Permission permissionInstance = permissionService.findById(id);

        if (null == permissionInstance) {
            String warnInfo = messageSource.getMessage("default.not.found.message", new Object[]{id}, locale);
            return new ModelAndView("redirect:/permission/list", AlertCommand.ALERT_COMMAND_KEY,
                    new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        return new ModelAndView("/permission/show", "permission", permissionInstance);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public ModelAndView edit(@PathVariable(value = "id") Long id, Locale locale) throws Exception {
        Permission permissionInstance = permissionService.findById(id);

        if (null == permissionInstance) {
            String warnInfo = messageSource.getMessage("default.not.found.message", new Object[]{id}, locale);
            return new ModelAndView("redirect:/permission/list", AlertCommand.ALERT_COMMAND_KEY,
                    new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        EditCommand editCommand = new EditCommand();
        BeanUtils.copyProperties(editCommand, permissionInstance);
        return new ModelAndView("/permission/edit", "permission", editCommand);
    }

    @RequestMapping(value = "/edit/{id}", method = RequestMethod.POST)
    public ModelAndView update(@PathVariable(value = "id") Long id, @Valid @ModelAttribute("user") EditCommand editCommand,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes, Locale locale) throws Exception {

        Permission permissionInstance = permissionService.findById(id);

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/permission/edit", "permission", editCommand);
        }

        if (permissionInstance == null) {
            String warnInfo = messageSource.getMessage("default.not.found.message",
                    new Object[]{id}, locale);
            return new ModelAndView("redirect:/permission/list", AlertCommand.ALERT_COMMAND_KEY,
                    new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        if (permissionInstance.getVersion() > editCommand.getVersion()) {
            String warnInfo = messageSource.getMessage("default.optimistic.locking.failure",
                    new Object[]{id}, locale);
            return new ModelAndView("/permission/edit", "permission", editCommand)
                    .addObject(AlertCommand.ALERT_COMMAND_KEY, new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        BeanUtils.copyProperties(permissionInstance, editCommand);
        //TODO catch exception
        permissionService.edit(permissionInstance);

        redirectAttributes.addAttribute("id", permissionInstance.getId());
        return new ModelAndView("redirect:/permission/show/{id}");
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ModelAndView delete(@PathVariable(value = "id") long id, Locale locale) throws Exception {

        Permission permissionInstance = permissionService.findById(id);

        if (permissionInstance == null) {
            String warnInfo = messageSource.getMessage("default.not.found.message",
                    new Object[]{id}, locale);
            return new ModelAndView("redirect:/permission/list", AlertCommand.ALERT_COMMAND_KEY, new AlertCommand(AlertCommand.AlertType.DANGER, warnInfo));
        }

        //TODO catch exception
        permissionService.delete(permissionInstance);
        return new ModelAndView("redirect:/permission/list");
    }
}
