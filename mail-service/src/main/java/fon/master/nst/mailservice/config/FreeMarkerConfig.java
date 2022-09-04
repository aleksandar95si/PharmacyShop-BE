package fon.master.nst.mailservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

@Configuration
public class FreeMarkerConfig {

    @Primary
    @Bean
    public FreeMarkerConfigurationFactoryBean factoryBean() {
        FreeMarkerConfigurationFactoryBean freeMarkerBean = new FreeMarkerConfigurationFactoryBean();
        freeMarkerBean.setTemplateLoaderPath("");
        return freeMarkerBean;
    }

}
