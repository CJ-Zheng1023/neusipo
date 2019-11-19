package com.neusoft.neusipo.gateway.feign;

import com.neusoft.neusipo.common.vo.RoleInfo;
import com.neusoft.neusipo.common.vo.UserInfo;
import com.neusoft.neusipo.core.base.Response;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

/**
 * @description: 用户认证feign客户端
 * @author: zhengchj
 * @create: 2019-11-02 11:58
 **/
@FeignClient(value = "neusipo-admin", fallbackFactory = AuthClientFallbackFactory.class)
public interface AuthClient {
    @GetMapping("/auth/load/user")
    Response<UserInfo> loadByUsername(@RequestParam("username") String username);
    @GetMapping("/auth/load/roles")
    Response<List<RoleInfo>> loadRoles();
}
@Slf4j
@Component
class AuthClientFallbackFactory implements FallbackFactory<AuthClient>{

    @Override
    public AuthClient create(Throwable throwable) {
        return new AuthClient(){
            @Override
            public Response<UserInfo> loadByUsername(String username) {
                log.error("熔断退回，服务降级，原因：[{}]", throwable.getMessage());
                return new Response<>(0);
            }
            @Override
            public Response<List<RoleInfo>> loadRoles() {
                log.error("熔断退回，服务降级，原因：[{}]", throwable.getMessage());
                return new Response<>(0);
            }
        };
    }
}
