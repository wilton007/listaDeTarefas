package com.listaprafazer.todolist.repository;

import com.listaprafazer.todolist.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
}
