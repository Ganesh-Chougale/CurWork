package com.PB.ParkingBay.Service;

import com.PB.ParkingBay.Entity.Vendor;
import com.PB.ParkingBay.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Vendor vendor;

        // Check if the username is mobile or email
        if (username.matches("\\d{10}")) {
            vendor = vendorRepository.findByMobile(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with mobile: " + username));
        } else {
            vendor = vendorRepository.findByEmail(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        }

        // Return a UserDetails object
        return User.builder()
                .username(username)
                .password(vendor.getPassword())
                .roles("USER") // Assign a default role
                .build();
    }
}
