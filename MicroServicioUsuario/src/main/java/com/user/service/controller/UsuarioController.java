package com.user.service.controller;

import com.user.service.entity.User;
import com.user.service.models.Car;
import com.user.service.models.Moto;
import com.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> listarUsuarios() {
        List<User> usuarios = this.userService.getAll();
        if (usuarios.isEmpty()) {
            //en caso de estar vacia retornamos una respuesta de que no existe
            //tal contenido
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> obtenerUsuario(
            @PathVariable("id") int id) {
        User user = this.userService.getUsuarioById(id);
        System.out.println("user = " + user);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    // Con la anotación de circuitbreaker hacemos que el microservicio no caiga totalmente
    // si otro se encuentra caido, lo intentará mantener activo
    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackGetCarros")
    @GetMapping("/carros/{userId}")
    public ResponseEntity<List<Car>> listarCarros(
            @PathVariable("userId") int userId) {
        User user = this.userService.getUsuarioById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Car> cars = this.userService.getCarros(userId);
        return ResponseEntity.ok(cars);
    }

    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackGetMotos")
    @GetMapping("/motos/{userId}")
    public ResponseEntity<List<Moto>> listarMotos(
            @PathVariable("userId") int userId) {
        User user = this.userService.getUsuarioById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        List<Moto> motos = this.userService.getMotos(userId);
        return ResponseEntity.ok(motos);
    }

    @CircuitBreaker(name = "todosCB", fallbackMethod = "fallBackGetTodos")
    @GetMapping("/all/{usuarioID}")
    public ResponseEntity<Map<String, Object>> getAll(
            @PathVariable("usuarioID") int usuarioID) {
        Map<String, Object> resultado = this.userService.getUsuarioAndVehiculos(usuarioID);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ResponseEntity<User> guardarUsuario(
            @RequestBody User user) {
        User newUser = this.userService.guardarUsuario(user);
        return ResponseEntity.ok(newUser);
    }

    @CircuitBreaker(name = "carrosCB", fallbackMethod = "fallBackSaveCarro")
    @PostMapping("/carro/{usuarioID}")
    public ResponseEntity<Car> guardarCarro(
            @PathVariable("usuarioID") int usuarioID,
            @RequestBody Car car) {
        Car newCar = this.userService.saveCarro(usuarioID, car);
        return ResponseEntity.ok(newCar);
    }

    // Notación post con una ruta predefinida
    @CircuitBreaker(name = "motosCB", fallbackMethod = "fallBackSaveMoto")
    @PostMapping("/moto/{usuarioID}")
    public ResponseEntity<Moto> guardarMoto(
            // @PathVariable es el parámetro que se agrega a la url
            @PathVariable("usuarioID") int usuarioID,
            // @RequestBody es el cuerpo de la petición en el cual esperará 
            // un body de tipo modo
            @RequestBody Moto moto) {
        Moto newMoto = this.userService.saveMoto(usuarioID, moto);
        return ResponseEntity.ok(newMoto);
    }

    //implementación de los métodos del circuit breaker
    private ResponseEntity<List<Car>> fallBackGetCarros(
            @PathVariable("userId") int userId,
            RuntimeException exception) {
        // en caso de que el microservicio falle retornamos un mensaje con un código http como respuesta
        return new ResponseEntity("El usuario: " + userId + "tiene los carros en el taller", HttpStatus.OK);
    }
    
    private ResponseEntity<List<Moto>> fallBackGetMotos(
            @PathVariable("userId") int userId,
            RuntimeException exception) {
        return new ResponseEntity("El usuario: " + userId + "tiene las motos en el taller", HttpStatus.OK);
    }
    
    private ResponseEntity<Moto> fallBackSaveMoto(
            @PathVariable("userId") int userId,
            RuntimeException exception,
            @RequestBody Moto moto) {
        return new ResponseEntity("El usuario: " + userId + "no tiene dinero para la moto", HttpStatus.OK);
    }
    
    private ResponseEntity<Car> fallBackSaveCarro(
            @PathVariable("userId") int userId,
            RuntimeException exception) {
        return new ResponseEntity("El usuario: " + userId + "no tiene dinero para el carro", HttpStatus.OK);
    }
    
    private ResponseEntity<Map<String, Object>> fallBackGetTodos(
            @PathVariable("userId") int userId,
            RuntimeException exception) {
        return new ResponseEntity("El usuario: " + userId + "tiene sus vehiculos en el taller", HttpStatus.OK);
    }
}
