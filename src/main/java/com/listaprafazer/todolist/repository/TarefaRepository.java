package com.listaprafazer.todolist.repository;

import com.listaprafazer.todolist.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface TarefaRepository extends JpaRepository<Tarefa,Long> {

   @Query(value = "select * from tarefa t where t.usuario_id = :usuarioId", nativeQuery = true )
    List<Tarefa> bucarPorUsuarioId(@Param("usuarioId") Long usuarioId);

    @Query(value = "select count(*) from tarefa t where t.usuario_id = :usuarioId " +
            "and t.status in ('PENDENTE', 'EM_ANDAMENTO') " +
            "and t.data_inicio = :data", nativeQuery = true )
    Integer countTask(@Param("usuarioId") Long usuarioId,@Param("data")  LocalDate data);


}
