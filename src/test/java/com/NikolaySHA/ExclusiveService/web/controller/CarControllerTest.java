package com.NikolaySHA.ExclusiveService.web.controller;

import com.NikolaySHA.ExclusiveService.model.entity.Car;
import com.NikolaySHA.ExclusiveService.model.entity.User;
import com.NikolaySHA.ExclusiveService.repo.CarRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {
    
    @Autowired
    private CarRepository carRepository;
    
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void testViewCar() throws Exception {
        Car car = new Car();
        car.setLicensePlate("CB9999BC");
        car.setMake("Audi");
        car.setModel("RS6");
        car.setOwner(new User("test@example.com", "password", "Mad Max"));
        Car actualCar = carRepository.save(car);
        mockMvc.perform(get("car/{id}", actualCar.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
