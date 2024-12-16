### 1. main package: `package com.PB.ParkingBay;`  
- `DatabaseHealthCheck.java`  
```java
package com.PB.ParkingBay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseHealthCheck implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        try {
            // Test the database connection
            jdbcTemplate.execute("SELECT 1");
            System.out.println("✅ Successfully connected to the database!");
        } catch (Exception e) {
            System.err.println("❌ Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```  
- `ParkingBayApplication.java`  
```java
package com.PB.ParkingBay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.PB.ParkingBay")
public class ParkingBayApplication {

	public static void main(String[] args) {
		System.out.println("✅ Main App ✅");
		SpringApplication.run(ParkingBayApplication.class, args);
	}

}
```  
### 2. Controller:  
- `VendorController.java`  
```java
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
```  
### 3. DTO:  
- `LoginByEmailRequest.java`  
```java
package com.PB.ParkingBay.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginByEmailRequest {

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```  
- `LoginByMobileRequest.java`  
```java
package com.PB.ParkingBay.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LoginByMobileRequest {

    @NotBlank(message = "Mobile number is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobile;

    @NotBlank(message = "Password is mandatory")
    private String password;

    // Getters and Setters
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```  
- `LoginRequest.java`  
```java
package com.PB.ParkingBay.DTO;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Login (email or mobile) is mandatory")
    private String login;  // Could be email or mobile

    @NotBlank(message = "Password is mandatory")
    private String password;

    // Getters and Setters
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
```  
- `LoginResponse.java`  
```java
package com.PB.ParkingBay.DTO;

public class LoginResponse {
    private boolean login;
    private String token;

    public LoginResponse(boolean login, String token) {
        this.login = login;
        this.token = token;
    }

    // Getters and setters
    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
```  
- `RegisterVendorRequest.java`  
```java
package com.PB.ParkingBay.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterVendorRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Mobile is mandatory")
    private String mobile;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 6, message = "Password should have at least 6 characters")
    private String password;

    @NotBlank(message = "Confirm password is mandatory")
    private String confirmPassword;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
```  
### 4.Entity:  
- `Vendor.java`  
```java
package com.PB.ParkingBay.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Name is required")
    private String name;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Mobile is required")
    @Size(min = 10, max = 10, message = "Mobile number should be exactly 10 digits")
    @Column(unique = true)
    private String mobile;

    @NotNull(message = "Password is required")
    @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;

    @Transient  // This field will not be persisted in DB
    @NotNull(message = "Confirm Password is required")
    private String confirmPassword;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
```  
### 5. Repository:  
- `VendorRepository.java`  
```java
package com.PB.ParkingBay.Repository;

import com.PB.ParkingBay.Entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
    
    Optional<Vendor> findByEmail(String email);
    
    Optional<Vendor> findByMobile(String mobile);

}
```  
### 6. Security:  
- `SecurityConfig.java`  
```java
package com.PB.ParkingBay.Security;

import com.PB.ParkingBay.Service.CustomUserDetailsService;
import com.PB.ParkingBay.Utility.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // Configure HTTP security
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
            .requestMatchers("/vendor/register", "/vendor/logmob", "/vendor/logmail").permitAll()
            .anyRequest().authenticated()
            .and()
            .sessionManagement().disable();
        return http.build();
    }

    // Configure the AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .build();
    }

    // Password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```  
### 7. Service:  
- `CustomUserDetailsService.java`  
```java
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
```  
- `VendorService.java`  
```java
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
```  
### 8. Utility:  
- `JwtUtil.java`  
```java
package com.PB.ParkingBay.Utility;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {
	
    public Key SecretKeyGenerator() {
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        System.out.println("Generated Key: " + secretKey); // You can log the key if needed
        return secretKey;
    }

    private Key SECRET_KEY = SecretKeyGenerator(); // Call the method to get the key


//    private String SECRET_KEY = "dnecabtoobgnirpsyabgnikrap"; // Use a stronger key in production
    private long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour expiration

    // Generate JWT Token
    public String generateToken(String username, Map<String, Object> claims) {
        if (claims == null) {
            claims = Map.of();  // Ensure claims is not null
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now())) // Using Instant.now()
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    // Validate JWT Token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token); // Parsing the JWT
            return true; // Token is valid
        } catch (Exception e) {
            // Logging the error or rethrowing could be beneficial here
            return false; // Token is invalid
        }
    }

    // Extract Username from Token
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Extracting the subject (username)
    }
}
```  