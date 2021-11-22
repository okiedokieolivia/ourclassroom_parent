package com.ourclassroom.ourclassroom.infrastructure.apigateway.filter;

import com.google.gson.JsonObject;
import com.ourclassroom.ourclassroom.base.util.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AuthGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // if the path matches "/api/**/auth/**", authentication is required
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        if (antPathMatcher.match("/api/**/auth/**", path)) {
            // get the token
            List<String> tokenList = request.getHeaders().get("token");
            // token not exist
            if (tokenList == null) {
                ServerHttpResponse response = exchange.getResponse();
                return this.out(response);
            }

            // token not valid
            boolean isValid = JwtUtils.checkJwtTToken(tokenList.get(0));
            if (!isValid) {
                ServerHttpResponse response = exchange.getResponse();
                return this.out(response);
            }
        }
        // pass the request to the next filter in the chain
        return chain.filter(exchange);
    }

    // priority
    @Override
    public int getOrder() {
        return 0;
    }

    private Mono<Void> out(ServerHttpResponse response) {

        JsonObject message = new JsonObject();
        message.addProperty("success", false);
        message.addProperty("code", 28004);
        message.addProperty("data", "");
        message.addProperty("message", "Authentication failed");
        byte[] bytes = message.toString().getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        response.getHeaders().add("Content-Type", "application/json;charset=UTF-8");

        return response.writeWith(Mono.just(buffer));
    }
}
