package com.example.alunos.security;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.example.alunos.domain.model.Aluno;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Component
public class JwtUtil {
    @Value("${auth.jwt.secret}")
    private String jwtSecret;
    @Value("${auth.jwt-expiration-milliseg}")
    private Long jwtExpirationMilliseg;
    

    public String gerarToken(Authentication authentication){
        Date dataExpiracao = new Date(new Date().getTime() + jwtExpirationMilliseg);
        Aluno aluno = (Aluno) authentication.getPrincipal();
        try{
             Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));  

             return Jwts.builder().setSubject(aluno.getRA()).setIssuedAt(new Date()).setExpiration(dataExpiracao).signWith(secretKey).compact();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return " ";
    }

    private Claims getClaims(String token){ //desmembrar token
        try{
            Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes("UTF-8"));
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
            return claims;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String getRAJwtUtil(String token){
        Claims claims = getClaims(token); //ja recebe token quebrado
        if(claims == null){
            return null;
        }
        return claims.getSubject(); //pra dar get username Q foi mudado para ra
    };
    
    public boolean isValidToken(String token){
        Claims claims = getClaims(token);
        if(claims == null){
            return false;
        }
        String ra = claims.getSubject();
        Date dataExpiracao = claims.getExpiration();
        Date agora = new Date(System.currentTimeMillis());
        if(ra != null && agora.before(dataExpiracao)){
            return true;
        }
        return false;
    }


}
