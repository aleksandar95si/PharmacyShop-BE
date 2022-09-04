package fon.master.nst.orchestrationservice.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import fon.master.nst.orchestrationservice.service.impl.AuthService;

public class FeignAuthConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", AuthService.getAccessToken());
    }
}
