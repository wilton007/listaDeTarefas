package com.listaprafazer.todolist.dto;

import com.listaprafazer.todolist.enums.StatusTarefaEnum;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TerminoResponseDTO {
    private Long id;
    private String titulo;
    private StatusTarefaEnum status;
    private LocalDate dataInicio;
    private LocalDateTime dataFim;
}
