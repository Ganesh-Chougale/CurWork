package com.PB.ParkingBay.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.PB.ParkingBay.Entity.Vendor;
import com.PB.ParkingBay.Repository.VendorRepository;

@Service
public class VendorService implements UserDetailsService{
	
	@Autowired
	private VendorRepository vRepository;
	
    public Optional<Vendor> findByEmail(String email) {
        return vRepository.findByEmail(email);
    }
    
	public Vendor registerVendor(Vendor vendor) {
		// password confirmation
		if(!vendor.getPassword().equals(vendor.getConfirmPassword())) {
			throw new IllegalArgumentException("Password do not match");
		}
		
		return vRepository.save(vendor);
	}
	
	// to retrieve vendor by mail
	public Optional<Vendor> getVendorByEmail(String email){
		return vRepository.findByEmail(email);
	}
	
	public Optional<Vendor> getVendorByMobile(String mobile){
		return vRepository.findByMobile(mobile);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    // Look up vendor by email (you can adjust this depending on whether you're using mobile or email)
	    Vendor vendor = vRepository.findByEmail(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
	    
	    return org.springframework.security.core.userdetails.User.builder()
	            .username(vendor.getEmail())
	            .password(vendor.getPassword())
	            .roles("VENDOR")  // Add more roles if needed
	            .build();
	}

}
