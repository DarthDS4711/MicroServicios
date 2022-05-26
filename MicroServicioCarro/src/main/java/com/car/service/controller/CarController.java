package com.car.service.controller;

import com.car.service.entity.Car;
import com.car.service.services.CarService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/carro") 
public class CarController {
    
    @Autowired
    private CarService carService;
    
    @GetMapping
    public ResponseEntity<List<Car>> listarCarros() {
        List<Car> carros = this.carService.getAll();
        if (carros.isEmpty()) {
            //en caso de estar vacia retornamos una respuesta de que no existe
            //tal contenido
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Car> obtenerCarro(
            @PathVariable("id") int id) {
        Car car = this.carService.getCarById(id);
        if (car == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(car);
    }

    @PostMapping
    public ResponseEntity<Car> guardarCarro(
    @RequestBody Car car) {
        Car newCar = this.carService.guardarCarro(car);
        System.out.println("Carro guardado");
        return ResponseEntity.ok(newCar);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Car>> listarCarrosUsuarioId(
    @PathVariable("usuarioId") int userId){
        List<Car> cars = this.carService.getByUserId(userId);
        if (cars.isEmpty()) {
            //en caso de estar vacia retornamos una respuesta de que no existe
            //tal contenido
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }
}
