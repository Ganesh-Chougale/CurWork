package com.PB.ParkingBay.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

import com.PB.ParkingBay.Entity.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long>{
	
	// find user by email
	Optional<Vendor> findByEmail(String email);
	
	// find use by mobile number
	Optional<Vendor> findByMobile(String mobile);
}
