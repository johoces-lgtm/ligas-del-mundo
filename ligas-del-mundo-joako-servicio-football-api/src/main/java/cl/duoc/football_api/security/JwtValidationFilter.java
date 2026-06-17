package cl.duoc.football_api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.List;

// 1. Transformamos el filtro en un componente de Spring
@Component
public class JwtValidationFilter extends OncePerRequestFilter {

    // 2. Inyectamos la clave secreta desde application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String header = request.getHeader("Authorization");

        // Si no hay token, dejamos que Spring Security bloquee la ruta si está protegida
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            // 3. Recreamos la llave criptográfica usando el secreto exacto
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());

            // 4. Validamos la firma y fecha de expiración del JWT
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // 5. Extraemos el usuario y su rol desde el payload del token
            String username = claims.getSubject();
            String role = claims.get("role", String.class);

            // 6. Convertimos el rol en un permiso formal de Spring Security
            List<SimpleGrantedAuthority> authorities = (role != null) 
                    ? List.of(new SimpleGrantedAuthority(role)) 
                    : List.of();

            // 7. Autenticamos formalmente la petición
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            // Si el token fue alterado, la clave es distinta o ya expiró, lanzamos 401
            logger.error("Fallo crítico de seguridad - JWT Inválido: " + e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Acceso Denegado: Token invalido, modificado o expirado\"}");
            return; // Cortamos el flujo aquí, no llega al controlador
        }

        // Si todo sale bien, continuamos con la cadena
        filterChain.doFilter(request, response);
    }
}