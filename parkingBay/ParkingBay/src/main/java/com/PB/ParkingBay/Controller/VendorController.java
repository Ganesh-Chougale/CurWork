package com.PB.ParkingBay.Controller;

import com.PB.ParkingBay.DTO.LoginByEmailRequest;
import com.PB.ParkingBay.DTO.LoginByMobileRequest;
import com.PB.ParkingBay.DTO.RegisterVendorRequest;
import com.PB.ParkingBay.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/logmob")
    public ResponseEntity<String> loginByMobile(@RequestBody LoginByMobileRequest request) {
        String result = vendorService.authenticateByMobile(request);
        if (result.contains("Invalid credentials")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/logmail")
    public ResponseEntity<String> loginByEmail(@RequestBody LoginByEmailRequest request) {
        String result = vendorService.authenticateByEmail(request);
        if (result.contains("Invalid credentials")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerVendor(@RequestBody RegisterVendorRequest request) {
        String result = vendorService.registerVendor(request);
        if (result.contains("already exists") || result.contains("Passwords do not match")) {
            return ResponseEntity.badRequest().body(result);
        }
        return ResponseEntity.ok(result);
    }
}
