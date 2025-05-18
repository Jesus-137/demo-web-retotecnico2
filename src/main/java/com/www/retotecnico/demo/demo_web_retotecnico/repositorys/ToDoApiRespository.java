package com.www.retotecnico.demo.demo_web_retotecnico.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.www.retotecnico.demo.demo_web_retotecnico.models.ToDo;

@Repository
public interface ToDoApiRespository extends JpaRepository<ToDo, Long>{

}