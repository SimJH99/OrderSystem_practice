package com.encore.ordering.Securities;

import com.encore.ordering.Common.ErrorResponseDto;
import com.encore.ordering.Common.ExceptionHandlerClass;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthFilter extends GenericFilter {
    ExceptionHandlerClass exceptionHandlerClass;


    @Value("${jwt.secretKey}")
    private String secretKey;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
        try {
            String bearerToken = ((HttpServletRequest) servletRequest).getHeader("Authorization");
            if (bearerToken != null) {
                if (!bearerToken.substring(0, 7).equals("Bearer ")) {
                    throw new IllegalArgumentException("token의 형식이 맞지 않습니다.");
                }
                //        bearer토큰에서 토큰 값 만 추출
                String token = bearerToken.substring(7);
                //        "mySecret"
                //        추출된 토큰을 검중 후 Authentication 객체 생성
                //        키를 꺼낼 때 검증이된다. 따라서 해당 아래의 과정에서 검증이 된다고 볼 수 있다.
//                토큰검증 및 claims 추출
                Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();//body는 payload와 거의 유사하다.
//                Authentication객체를 생성하기 위한 UserDetails 생성
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_" + claims.get("role")));
                UserDetails userDetails = new User(claims.getSubject(), "", authorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
//            filterchain에서 그 다음 filtering으로 넘어가도록 하는 메서드
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            httpServletResponse.setContentType(("application/json"));
            httpServletResponse.getWriter().write(ErrorResponseDto.makeMessage(HttpStatus.UNAUTHORIZED, e.getMessage()).toString());
        }
    }
}
