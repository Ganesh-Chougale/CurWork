package com.PB.ParkingBay.Service;

import com.PB.ParkingBay.DTO.RegisterVendorRequest;
import com.PB.ParkingBay.Entity.Vendor;
import com.PB.ParkingBay.Repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerVendor(RegisterVendorRequest request) {
        // Check if the email or mobile already exists
        if (vendorRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already exists";
        }
        if (vendorRepository.findByMobile(request.getMobile()).isPresent()) {
            return "Mobile already exists";
        }

        // Password match validation
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "Passwords do not match";
        }

        // Password strength validation (optional)
        if (request.getPassword().length() < 8) {
            return "Password must be at least 8 characters long";
        }

        // Create vendor entity and set fields
        Vendor vendor = new Vendor();
        vendor.setName(request.getName());
        vendor.setEmail(request.getEmail());
        vendor.setMobile(request.getMobile());
        vendor.setPassword(passwordEncoder.encode(request.getPassword()));
        vendor.setConfirmPassword(passwordEncoder.encode(request.getConfirmPassword()));  // Hash the password

        // Save the vendor
        vendorRepository.save(vendor);

        return "Vendor registered successfully";
    }
}
