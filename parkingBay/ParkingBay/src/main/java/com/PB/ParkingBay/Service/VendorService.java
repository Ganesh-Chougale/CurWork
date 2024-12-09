package com.PB.ParkingBay.Service;

import com.PB.ParkingBay.DTO.LoginByEmailRequest;
import com.PB.ParkingBay.DTO.LoginByMobileRequest;
import com.PB.ParkingBay.DTO.RegisterVendorRequest;
import com.PB.ParkingBay.Entity.Vendor;
import com.PB.ParkingBay.Repository.VendorRepository;
import com.PB.ParkingBay.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerVendor(RegisterVendorRequest request) {
        if (vendorRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }
        if (vendorRepository.findByMobile(request.getMobile()).isPresent()) {
            return "Mobile already exists";
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "Passwords do not match";
        }
        if (request.getPassword().length() < 8) {
            return "Password must be at least 8 characters long";
        }

        Vendor vendor = new Vendor();
        vendor.setName(request.getName());
        vendor.setEmail(request.getEmail());
        vendor.setMobile(request.getMobile());
        vendor.setPassword(passwordEncoder.encode(request.getPassword()));

        vendorRepository.save(vendor);
        return "Vendor registered successfully";
    }

    public String authenticateByMobile(LoginByMobileRequest request) {
        Vendor vendor = vendorRepository.findByMobile(request.getMobile()).orElse(null);
        if (vendor == null || !passwordEncoder.matches(request.getPassword(), vendor.getPassword())) {
            return "Invalid credentials";
        }
        String token = jwtUtil.generateToken(vendor.getMobile(), null);
        return "{\"login\": true, \"token\": \"" + token + "\"}";
    }

    public String authenticateByEmail(LoginByEmailRequest request) {
        Vendor vendor = vendorRepository.findByEmail(request.getEmail()).orElse(null);
        if (vendor == null || !passwordEncoder.matches(request.getPassword(), vendor.getPassword())) {
            return "Invalid credentials";
        }
        String token = jwtUtil.generateToken(vendor.getEmail(), null);
        return "{\"login\": true, \"token\": \"" + token + "\"}";
    }
}
