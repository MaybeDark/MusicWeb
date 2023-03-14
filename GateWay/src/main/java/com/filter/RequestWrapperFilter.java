package com.filter;

import com.tools.IPUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


//gateway全局过滤器
//要配置同名Bean
public class RequestWrapperFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        System.out.println(IPUtil.getIpAddr(exchange.getRequest()));
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
