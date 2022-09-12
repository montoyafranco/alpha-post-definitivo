//package com.posada.santiago.alphapostsandcomments.application.config.jwt;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.util.StringUtils;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.web.server.WebFilter;
//import org.springframework.web.server.WebFilterChain;
//import reactor.core.publisher.Mono;
//
//@RequiredArgsConstructor
//public class JwtTokenAuthenticationfilter implements WebFilter {
//    //bearer
//    public static final String HEADER_PREFIX = "Bearer ";
//    private final JwtProvider jwtProvider;
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
//        //1-get the request
//        //var token = resolveToken()
//        return null;
//    }
//    private  String resolveToken(ServerHttpRequest request){
//        //1-ger the header that has the token
//        var bearerToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);//Bearer jasdjasjd
//        //2
//        return StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)?
//                bearerToken.substring(7) : null ;
//
//    }
//
//
//
//
//}
