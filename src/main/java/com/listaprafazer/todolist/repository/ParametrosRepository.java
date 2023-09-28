package com.listaprafazer.todolist.repository;

import com.listaprafazer.todolist.model.Parametros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ParametrosRepository extends JpaRepository<Parametros, String > {

    @Query(value = "select p.valor from parametros p where p.nome = :quantidadeMaximaTarefaDiaria", nativeQuery = true)
    String buscarValorPorId(@Param("quantidadeMaximaTarefaDiaria") String quantidadeMaximaTarefaDiaria);
}
