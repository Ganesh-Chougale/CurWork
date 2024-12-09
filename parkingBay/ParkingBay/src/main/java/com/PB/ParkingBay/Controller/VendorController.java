package com.PB.ParkingBay.Controller;

import com.PB.ParkingBay.DTO.RegisterVendorRequest;
import com.PB.ParkingBay.Service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping("/register")
    public ResponseEntity<String> registerVendor(@RequestBody @Valid RegisterVendorRequest request) {
        // Call the service method to register the vendor
        String result = vendorService.registerVendor(request);
        
        // Return the response based on the result
        if (result.equals("Vendor registered successfully")) {
            return ResponseEntity.ok(result);  // HTTP 200 OK
        } else {
            return ResponseEntity.badRequest().body(result);  // HTTP 400 Bad Request with message
        }
    }
}
