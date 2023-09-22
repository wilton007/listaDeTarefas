package com.listaprafazer.todolist.dto;

import com.listaprafazer.todolist.enums.PrioridadesEnum;
import com.listaprafazer.todolist.enums.StatusTarefaEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TarefaRequestDTOatuaizacao {
    private Long id;
    private String titulo;
    private String descricao;
    private PrioridadesEnum prioridades;
    private StatusTarefaEnum status;
    private LocalDate dataInicio;
}
