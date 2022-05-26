package com.moto.service.controller;

import com.moto.service.entity.Moto;
import com.moto.service.service.MotoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/moto")
public class MotoController {
    
    @Autowired
    private MotoService motoService;
    
    @GetMapping
    public ResponseEntity<List<Moto>> listarMotos() {
        List<Moto> carros = this.motoService.getAll();
        if (carros.isEmpty()) {
            //en caso de estar vacia retornamos una respuesta de que no existe
            //tal contenido
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carros);
    }
    
    
    @GetMapping("/{id}")
    public ResponseEntity<Moto> obtenerMoto(
            @PathVariable("id") int id) {
        Moto moto = this.motoService.getCarById(id);
        if (moto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(moto);
    }

    @PostMapping
    public ResponseEntity<Moto> guardarMoto(
    @RequestBody Moto moto) {
        Moto newMoto = this.motoService.guardarMoto(moto);
        return ResponseEntity.ok(newMoto);
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Moto>> listarMotosUsuarioId(
    @PathVariable("usuarioId") int userId){
        List<Moto> cars = this.motoService.getByUserId(userId);
        if (cars.isEmpty()) {
            //en caso de estar vacia retornamos una respuesta de que no existe
            //tal contenido
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cars);
    }
    
}
