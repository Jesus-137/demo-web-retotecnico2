package com.www.retotecnico.demo.demo_web_retotecnico.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.www.retotecnico.demo.demo_web_retotecnico.models.ToDo;
import com.www.retotecnico.demo.demo_web_retotecnico.repositorys.ToDoApiRespository;

@Service
public class ToDoApiService {
    private final ToDoApiRespository toDoRepository;

    public ToDoApiService(ToDoApiRespository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public ToDo crear(ToDo toDo) {
        return toDoRepository.save(toDo);
    }

    public List<ToDo> obtenerTodos() {
        return toDoRepository.findAll();
    }

    public ToDo actualizar(Long id, ToDo datosActualizados) {
        return toDoRepository.findById(id)
            .map(todo -> {
                todo.setDescripcion(datosActualizados.getDescripcion());
                todo.setEstadus(datosActualizados.getEstadus());
                todo.setFecha(datosActualizados.getFecha());
                return toDoRepository.save(todo);
            })
            .orElseThrow(() -> new RuntimeException("ToDo no encontrado con id " + id));
    }

    public ToDo cambiarEstatus(Long id, String completado) {
        return toDoRepository.findById(id)
            .map(todo -> {
                todo.setEstadus(completado);
                return toDoRepository.save(todo);
            })
            .orElseThrow(() -> new RuntimeException("ToDo no encontrado con id " + id));
    }

    public void eliminar(Long id) {
        toDoRepository.deleteById(id);
    }
}
