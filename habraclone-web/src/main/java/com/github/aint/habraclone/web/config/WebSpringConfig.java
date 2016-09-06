package com.github.aint.habraclone.web.config;

import com.github.aint.habraclone.data.config.DataSpringConfig;
import com.github.aint.habraclone.service.config.ServiceSpringConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.EnumSet;

public class WebSpringConfig implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(DataSpringConfig.class, ServiceSpringConfig.class, SecuritySpringConfig.class);
        container.addListener(new ContextLoaderListener(rootContext));



        AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(DispatcherSpringConfig.class);

        ServletRegistration.Dynamic dispatcher = container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setInitParameter("throwExceptionIfNoHandlerFound", "true");
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");



        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);

        FilterRegistration.Dynamic characterEncoding = container.addFilter("encodingFilter", new CharacterEncodingFilter("UTF-8", true));
        characterEncoding.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

        FilterRegistration.Dynamic security = container.addFilter("springSecurityFilterChain", new DelegatingFilterProxy());
        security.addMappingForUrlPatterns(dispatcherTypes, true, "/*");

    }
}
