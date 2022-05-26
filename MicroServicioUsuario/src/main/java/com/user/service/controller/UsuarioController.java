package com.user.service.controller;

import com.user.service.entity.User;
import com.user.service.models.Car;
import com.user.service.models.Moto;
import com.user.service.services.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @GetMapping("/all/{usuarioID}")
    public ResponseEntity<Map<String, Object>> getAll(
            @PathVariable("usuarioID") int usuarioID){
        Map<String, Object> resultado = this.userService.getUsuarioAndVehiculos(usuarioID);
        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ResponseEntity<User> guardarUsuario(
            @RequestBody User user) {
        User newUser = this.userService.guardarUsuario(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/carro/{usuarioID}")
    public ResponseEntity<Car> guardarCarro(
            @PathVariable("usuarioID") int usuarioID,
            @RequestBody Car car) {
        Car newCar = this.userService.saveCarro(usuarioID, car);
        return ResponseEntity.ok(newCar);
    }

    @PostMapping("/moto/{usuarioID}")
    public ResponseEntity<Moto> guardarMoto(
            @PathVariable("usuarioID") int usuarioID,
            @RequestBody Moto moto) {
        Moto newMoto = this.userService.saveMoto(usuarioID, moto);
        return ResponseEntity.ok(newMoto);
    }
}
