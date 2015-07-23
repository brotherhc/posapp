package me.ethantu.posapp.commons.freemarker.support.spring;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import me.ethantu.posapp.commons.freemarker.directive.DirectiveUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;

/**
 * Author: EthanTu <ethan.l.tu@gmail.com>
 * Date: 13-10-28
 * Time: PM6:20
 */
public class FreeMarkerConfigurerExtension extends FreeMarkerConfigurer {
    /**
     * Return a new Configuration object. Subclasses can override this for
     * custom initialization, or for using a mock object for testing.
     * <p>Called by {@code createConfiguration()}.
     *
     * @return the Configuration object
     * @throws IOException       if a config file wasn't found
     * @throws TemplateException on FreeMarker initialization failure
     * @see #createConfiguration()
     */
    @Override
    protected Configuration newConfiguration() throws IOException, TemplateException {
        Configuration configuration = new Configuration();
        DirectiveUtils.exposeRapidMacros(configuration);
        return configuration;
    }
}
