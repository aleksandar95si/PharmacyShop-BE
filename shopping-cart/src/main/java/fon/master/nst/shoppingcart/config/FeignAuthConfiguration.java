package fon.master.nst.shoppingcart.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import fon.master.nst.shoppingcart.service.impl.AuthService;

public class FeignAuthConfiguration implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header("Authorization", AuthService.getAccessToken());
    }
}
