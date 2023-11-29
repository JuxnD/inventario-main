package pa.inventario.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import pa.inventario.security.entity.Admin;
import pa.inventario.security.entity.Rol;
import pa.inventario.security.entity.Usuario;
import pa.inventario.security.repository.UsuarioRepository;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expiration;
    public String generateToken(Authentication authentication){
        Admin admin = (Admin) authentication.getPrincipal();
        return Jwts.builder().setSubject(admin.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() +expiration*1000))
                .signWith(getSecret(secret))
                .compact();
    }
    public String getNombreUsuarioFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJwt(token).getBody().getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parserBuilder().setSigningKey(getSecret(secret)).build().parseClaimsJwt(token);
            return true;

        }catch (MalformedJwtException e){
            logger.error("token mal formado");
        }catch (UnsupportedJwtException e){
            logger.error("token no soportado");
        }catch (ExpiredJwtException e){
            logger.error("token expirado");
        }catch (IllegalAccessError e){
            logger.error("token vacio");
        }catch (SignatureException e){
            logger.error("fail en la firma");
        }
        return false;

    }

    private Key getSecret(String secret){

        byte[] secretByte = Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(secretByte);
    }
}
