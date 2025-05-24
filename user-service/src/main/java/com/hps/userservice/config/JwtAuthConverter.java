package com.hps.userservice.config;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtAuthConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    @Override
    public Collection<GrantedAuthority> convert(Jwt jwt) {
        List<String> roles = jwt.getClaimAsMap("realm_access") != null
                ? (List<String>) ((Map<String, Object>) jwt.getClaim("realm_access")).get("roles")
                : List.of();

        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // <- important
                .collect(Collectors.toList());
    }

    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this);
        return converter;
    }
    /*
    code Youssfi
     */
//    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter=new JwtGrantedAuthoritiesConverter();
//    @Override
//    public AbstractAuthenticationToken convert(Jwt jwt) {
//        Collection<GrantedAuthority> authorities = Stream.concat(
//                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
//                extractResourceRoles(jwt).stream()
//        ).collect(Collectors.toSet());
//        return new JwtAuthenticationToken(jwt, authorities,jwt.getClaim("preferred_username"));
//    }
//    private Collection<GrantedAuthority> extractResourceRoles(Jwt jwt) {
//        Map<String , Object> realmAccess;
//        Collection<String> roles;
//        if(jwt.getClaim("realm_access")==null){
//            return Set.of();
//        }
//        realmAccess = jwt.getClaim("realm_access");
//        roles = (Collection<String>) realmAccess.get("roles");
//        return roles.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet());
//    }

}
