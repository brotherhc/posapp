package me.ethantu.posapp.commons.freemarker.directive;


import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Author: EthanTu
 * Date: 12-12-7
 * Time: 下午10:22
 */
public class DirectiveUtils {

    public static String BLOCK = "__ftl_override__";
    public static String OVERRIDE_CURRENT_NODE = "__ftl_override_current_node";

    public static String getOverrideVariableName(String name) {
        return BLOCK + name;
    }

    public static void exposeRapidMacros(Configuration conf) {
        conf.setSharedVariable(BlockDirective.DIRECTIVE_NAME, new BlockDirective());
        conf.setSharedVariable(ExtendsDirective.DIRECTIVE_NAME, new ExtendsDirective());
        conf.setSharedVariable(OverrideDirective.DIRECTIVE_NAME, new OverrideDirective());
        conf.setSharedVariable(SuperDirective.DIRECTIVE_NAME, new SuperDirective());
    }

    static String getRequiredParam(Map params, String key) throws TemplateException {
        Object value = params.get(key);
        if (value == null || StringUtils.isEmpty(value.toString())) {
            throw new TemplateModelException("not found required parameter:" + key + " for directive");
        }
        return value.toString();
    }

    static String getParam(Map params, String key, String defaultValue) throws TemplateException {
        Object value = params.get(key);
        return value == null ? defaultValue : value.toString();
    }

    static OverrideDirective.TemplateDirectiveBodyOverrideWrapper getOverrideBody(Environment env, String name) throws TemplateModelException {
        OverrideDirective.TemplateDirectiveBodyOverrideWrapper value = (OverrideDirective.TemplateDirectiveBodyOverrideWrapper) env.getVariable(DirectiveUtils.getOverrideVariableName(name));
        return value;
    }

    static void setTopBodyForParentBody(Environment env,
                                        OverrideDirective.TemplateDirectiveBodyOverrideWrapper topBody,
                                        OverrideDirective.TemplateDirectiveBodyOverrideWrapper overrideBody) {
        OverrideDirective.TemplateDirectiveBodyOverrideWrapper parent = overrideBody;
        while (parent.parentBody != null) {
            parent = parent.parentBody;
        }
        parent.parentBody = topBody;
    }
}