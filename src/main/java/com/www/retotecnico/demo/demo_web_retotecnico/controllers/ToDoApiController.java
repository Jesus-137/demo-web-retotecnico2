package com.www.retotecnico.demo.demo_web_retotecnico.controllers;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.www.retotecnico.demo.demo_web_retotecnico.models.ToDo;
import com.www.retotecnico.demo.demo_web_retotecnico.repositorys.ToDoApiRespository;
import com.www.retotecnico.demo.demo_web_retotecnico.services.ToDoApiService;

@RestController
@RequestMapping("/api/todos")
public class ToDoApiController {

    private final ToDoApiRespository toDoApiRespository;

    private final ToDoApiService toDoService;

    ToDoApiController(ToDoApiRespository toDoApiRespository, ToDoApiService toDoService) {
        this.toDoApiRespository = toDoApiRespository;
        this.toDoService = toDoService;
    }

    private boolean contieneCampoInvalido(ToDo toDo) {
        String toDoStr = toDo.toString();
        Pattern pattern = Pattern.compile("(?<!id)=\\s*(null|\"{2})");
        Matcher matcher = pattern.matcher(toDoStr);
        return matcher.find();
    }

    private boolean estadoInvalido(ToDo toDo) {
        if (toDo.getEstadus() == null) return true;
        String estado = toDo.getEstadus().toLowerCase().trim();
        return !estado.equals("pendiente") &&
               !estado.equals("en progreso") &&
               !estado.equals("completado");
    }

    @PostMapping
    public ResponseEntity<Object> crearToDo(@RequestBody ToDo toDo) {
        if (contieneCampoInvalido(toDo)) {
            return ResponseEntity
                    .badRequest()
                    .body("❌ Hay al menos un campo distinto de 'id' con valor null o vacío");
        }

        if (estadoInvalido(toDo)) {
            return ResponseEntity
                    .unprocessableEntity()
                    .body("❌ El campo 'estadus' debe ser: pendiente, en progreso o completado");
        }

        return ResponseEntity
                .status(201)
                .body(toDoService.crear(toDo));
    }

    @GetMapping
    public ResponseEntity<Object> obtenerTodos() {
        List<ToDo> registros = toDoService.obtenerTodos();
        if (registros.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registros);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> actualizarToDo(@PathVariable Long id, @RequestBody ToDo datosActualizados) {
        if (!toDoApiRespository.findById(id).isPresent()){
            return ResponseEntity
                .status(404)
                .body("❌ El ToDo con ID " + id + " no existe");
        }

        if (contieneCampoInvalido(datosActualizados)) {
            return ResponseEntity
                    .badRequest()
                    .body("❌ Hay al menos un campo distinto de 'id' con valor null o vacío");
        }

        if (estadoInvalido(datosActualizados)) {
            return ResponseEntity
                    .unprocessableEntity()
                    .body("❌ El campo 'estadus' debe ser: pendiente, en progreso o completado");
        }

        return ResponseEntity
                .ok(toDoService.actualizar(id, datosActualizados));
    }

    @PatchMapping("/{id}/estatus")
    public ResponseEntity<Object> cambiarEstatus(@PathVariable Long id, @RequestBody String estadus) {
        if (!toDoApiRespository.findById(id).isPresent()){
            return ResponseEntity
                .status(404)
                .body("❌ El ToDo con ID " + id + " no existe");
        }
        return ResponseEntity.ok(toDoService.cambiarEstatus(id, estadus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> eliminarToDo(@PathVariable Long id) {
        if (!toDoApiRespository.findById(id).isPresent()){
            return ResponseEntity
                .status(404)
                .body("❌ El ToDo con ID " + id + " no existe");
        }
        toDoService.eliminar(id);
        return ResponseEntity.ok("Se elimino el ToDO con ID " + id);
    }
}
