package br.com.alura.forumapi.config.security;

import br.com.alura.forumapi.model.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private String expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerar(Authentication authentication) {

        Usuario usuario = (Usuario) authentication.getPrincipal();
        Date dataCriacao = new Date();
        Date dataExpiracao = new Date(dataCriacao.getTime() + Long.parseLong(this.expiration));

        return Jwts.builder()
                .setIssuer("API Forum")
                .setSubject(usuario.getId().toString())
                .setIssuedAt(dataCriacao)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256 , this.secret)
                .compact();
    }

    public boolean validar(String token) {

        try {
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch(Exception e) {
            return false;
        }

    }

    public Long getUsuarioId(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        return Long.parseLong(claims.getSubject());
    }
}
