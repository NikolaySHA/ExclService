package com.NikolaySHA.ExclusiveService.service.impl;

import com.NikolaySHA.ExclusiveService.model.dto.AddCarDataDTO;
import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.repo.CarRepository;
import com.NikolaySHA.ExclusiveService.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
    
    @InjectMocks
    private CarServiceImpl carService;
    
    @Mock
    private UserService userService;
    
    @Mock
    private CarRepository carRepository;
    
    @Test
    void testAddCarWithEmptyVin() {
        // Given
        AddCarDataDTO addCarDataDTO = new AddCarDataDTO();
        addCarDataDTO.setMake("Toyota");
        addCarDataDTO.setModel("Corolla");
        addCarDataDTO.setLicensePlate("XYZ123");
        addCarDataDTO.setColor("Blue");
        addCarDataDTO.setVin(""); // Empty VIN
        
        // Mock UserService to return a dummy user
        when(userService.findLoggedUser()).thenReturn(new User());
        
        // Mock CarRepository to simulate saving
        when(carRepository.findByLicensePlate(anyString())).thenReturn(Optional.empty());
        when(carRepository.save(any(Car.class))).thenAnswer(invocation -> invocation.getArgument(0));
        
        // When
        boolean result = carService.addCar(addCarDataDTO);
        
        // Then
        assertTrue(result);
        
        // Verify that the Car entity has its VIN set to null
        verify(carRepository).save(argThat(car -> car.getVin() == null));
    }
    @Test
    void testVinIsNullOnEmptyString(){
        AddCarDataDTO data = new AddCarDataDTO();
        
        assertNull(data.getVin());
    }
    @Test
    void testAddCar_AlreadyExists() {
        AddCarDataDTO data = new AddCarDataDTO();
        data.setLicensePlate("CB6666BC");
        
        when(carRepository.findByLicensePlate(data.getLicensePlate())).thenReturn(Optional.of(new Car()));
        
        boolean result = carService.addCar(data);
        
        assertFalse(result);
        verify(carRepository, never()).save(any(Car.class));
    }
    
    @Test
    void testFindCarsByUser() {
        Long userId = 1L;
        User user = new User();
        user.setEmail("user@example.com");
        
        List<Car> cars = new ArrayList<>();
        cars.add(new Car());
        when(userService.findById(userId)).thenReturn(Optional.of(user));
        when(carRepository.findByOwner_Email(user.getEmail())).thenReturn(cars);
        
        List<Car> result = carService.findCarsByUser(userId);
        
        assertEquals(cars, result);
    }
    
    @Test
    void testFindById() {
        Long carId = 1L;
        Car car = new Car();
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));
        
        Optional<Car> result = carService.findById(carId);
        
        assertTrue(result.isPresent());
        assertEquals(car, result.get());
    }
    
    @Test
    void testDelete() {
        Car car = new Car();
        carService.delete(car);
        verify(carRepository).delete(car);
    }
    
    @Test
    void testFindAllCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(new Car());
        when(carRepository.findAll()).thenReturn(cars);
        
        List<Car> result = carService.findAllCars();
        
        assertEquals(cars, result);
    }
    
    @Test
    void testSearchCars() {
        String licensePlate = "CB6666BC";
        String make = "AUDI";
        String customer = "Mad Max";
        
        List<Car> cars = new ArrayList<>();
        cars.add(new Car());
        when(carRepository.searchCarsByFilter(licensePlate, make, customer)).thenReturn(cars);
        
        List<Car> result = carService.searchCars(licensePlate, make, customer);
        
        assertEquals(cars, result);
    }
    
    @Test
    void testUpdateCar_Success() {
        Long carId = 1L;
        AddCarDataDTO updateData = new AddCarDataDTO();
        updateData.setLicensePlate("CB6666BC");
        updateData.setMake("AUDI");
        updateData.setModel("RSˆ");
        updateData.setVin("98765432109876543");
        updateData.setColor("Blue");
        
        Car existingCar = new Car();
        when(carRepository.findById(carId)).thenReturn(Optional.of(existingCar));
        
        carService.updateCar(carId, updateData);
        
        verify(carRepository).save(existingCar);
        assertEquals("CB6666BC", existingCar.getLicensePlate());
        assertEquals("AUDI", existingCar.getMake());
        assertEquals("RSˆ", existingCar.getModel());
        assertEquals("98765432109876543", existingCar.getVin());
        assertEquals("Blue", existingCar.getColor());
    }
    
    @Test
    void testUpdateCar_NotFound() {
        Long carId = 1L;
        AddCarDataDTO updateData = new AddCarDataDTO();
        
        when(carRepository.findById(carId)).thenReturn(Optional.empty());
        
        carService.updateCar(carId, updateData);
        
        verify(carRepository, never()).save(any(Car.class));
    }
}
