package com.PB.ParkingBay.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PB.ParkingBay.Entity.Vendor;
import com.PB.ParkingBay.JWT.JwtTokenUtil;
import com.PB.ParkingBay.Service.VendorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendor")
public class VendorController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private VendorService vService;
	
	//Registration endpoint
	@PostMapping("/register")
	public ResponseEntity<String> registerVendor(@Valid @RequestBody Vendor vendor){
		// register the vendor
		vService.registerVendor(vendor);
		return ResponseEntity.ok("Vendor registered successfully!");
	}
	
    // Mobile Login
    @PostMapping("/logmob")
    public ResponseEntity<String> loginWithMobile(@RequestBody Vendor loginRequest) {
        try {
        	System.out.println("debug check");
        	System.out.println(loginRequest.getMobile());   
        	System.out.println(loginRequest.getPassword()); 
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getMobile(), loginRequest.getPassword())
            );

            String jwtToken = jwtTokenUtil.generateToken(authentication.getName());
            return ResponseEntity.ok(jwtToken);
        } catch (Exception e) {
        	System.out.println("here is the error ✅✅✅✅"+e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

	
    // Email Login
    @PostMapping("/logmail")
    public ResponseEntity<String> loginWithEmail(@RequestBody Vendor loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        String jwtToken = jwtTokenUtil.generateToken(authentication.getName());
        return ResponseEntity.ok(jwtToken);
    }
}
