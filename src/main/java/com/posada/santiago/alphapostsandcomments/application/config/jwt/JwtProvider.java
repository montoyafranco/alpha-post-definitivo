package com.posada.santiago.alphapostsandcomments.application.config.jwt;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class JwtProvider {
    private String secret = "god-saved-the-queen";
    private long validTime = 3600000;//1h//ms

}
