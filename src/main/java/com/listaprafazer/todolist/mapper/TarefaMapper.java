package com.listaprafazer.todolist.mapper;

import com.listaprafazer.todolist.dto.TarefaRequestDTO;
import com.listaprafazer.todolist.dto.TarefaRequestDTOatuaizacao;
import com.listaprafazer.todolist.dto.TarefaResponseDTO;
import com.listaprafazer.todolist.dto.TarefaResponseDTOfinalizada;
import com.listaprafazer.todolist.model.Tarefa;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TarefaMapper {

    TarefaResponseDTO toTarefaResponseDTO(Tarefa tarefa);

    TarefaResponseDTOfinalizada toTarefaResponseDTOfinalizada(Tarefa tarefa);
    List<TarefaResponseDTO> toListTarefaResponseDTO(List<Tarefa> list);

    Tarefa toTarefa(TarefaRequestDTO tarefaRequestDTO);
    Tarefa toTarefa(TarefaRequestDTOatuaizacao tarefaRequestDTOatuaizacao);


}
