package spittr.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import spittr.web.WebConfig;

public class SpitterWebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override		//ContextLoaderListener也会创建一个应用上下文，加载其他的Bean（用于驱动后端的中间层和数据层组件）
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[] { RootConfig.class };
    }

    @Override		//指定配置类，DispatcherServlet启动时会创建Spring应用上下文，会创建该配置类中的Beans（如控制器/视图解析器/处理器）
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { WebConfig.class};
    }

    @Override		//将DispatcherServlet映射到“/”
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
