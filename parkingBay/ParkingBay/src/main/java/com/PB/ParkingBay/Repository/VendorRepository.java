package com.PB.ParkingBay.Repository;

import com.PB.ParkingBay.Entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    
    Optional<Vendor> findByEmail(String email);
    
    Optional<Vendor> findByMobile(String mobile);
   

}
