package com.ExclusiveService.repo;

import com.ExclusiveService.model.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByOwner_Email(String email);
    
    Optional<Car> findByLicensePlate(String licensePlate);
    @Query("SELECT c FROM Car c WHERE " +
            "(:licensePlate IS NULL OR c.licensePlate LIKE %:licensePlate%) AND " +
            "(:make IS NULL OR c.make LIKE %:make%) AND " +
            "(:customer IS NULL OR c.owner.name LIKE %:customer%)")
    List<Car> searchCarsByFilter(@Param("licensePlate") String licensePlate,
                                 @Param("make") String make,
                                 @Param("customer") String customer);
}
