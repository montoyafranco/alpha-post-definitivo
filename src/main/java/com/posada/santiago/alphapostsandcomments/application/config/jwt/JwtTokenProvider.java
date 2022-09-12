//package com.posada.santiago.alphapostsandcomments.application.config.jwt;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//import java.util.stream.Collectors;
//
//@Component
//@RequiredArgsConstructor
//public class JwtTokenProvider {
//    //1 createToken
//    //2 ValidateToken
//    //3 authenticate
//
//    private static final String AUTHORITIES_KEY = "roles";
//    private final JwtProvider jwtProvider;
//
//    private SecretKey secretKey;
//    @PostConstruct
//    protected void init(){
//        var secret = Base64.getEncoder().encodeToString(jwtProvider.getSecret().getBytes(StandardCharsets.UTF_8));
//        secretKey= Keys.hmacShaKeyfor(secret.getBytes(StandardCharsets.UTF_8));
//
//    }
//    public String createToken(Authentication authentication){
//        var username = authentication.getName();
//        var authorites = authentication.getAuthorities();
//        //creatinmg a claim instance in order to senti it and gets oir acces token
//        var claims = Jwts.claims().setSubject(username);
//
//        if(!authorites.isEmpty()){
//            claims.put(AUTHORITIES_KEY,authorites.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining()));
//        }
//
//
//
//    }
//
//}
