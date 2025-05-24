//package com.hps.userservice.services;
//
//import com.hps.userservice.entities.User;
//import com.hps.userservice.repositories.UserRepository;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = this.userRepository.findByEmail(username)
//                .orElseThrow(()-> new UsernameNotFoundException("User not found: "+username));
//
//        return org.springframework.security.core.userdetails.User
//                .withUsername(user.getEmail())
//                .password(user.getPassword())
//                .authorities(user.getRoles()
//                        .stream()
//                        .map(Enum::name)
//                        .toArray(String[]::new)
//                )
//                .build();
//    }
//}
