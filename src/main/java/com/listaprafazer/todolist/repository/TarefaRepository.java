package com.listaprafazer.todolist.repository;

import com.listaprafazer.todolist.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TarefaRepository extends JpaRepository<Tarefa,Long> {

}
