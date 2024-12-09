package com.PB.ParkingBay.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.PB.ParkingBay.Service.VendorService;

@Configuration
public class UserDetailsServiceConfig {

    private final VendorService vendorService;

    public UserDetailsServiceConfig(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> vendorService.findByEmail(username)
                                        .map(vendor -> User.builder()
                                             .username(vendor.getEmail())
                                             .password(vendor.getPassword())
                                             .roles("VENDOR") // Add roles as needed
                                             .build())
                                        .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
