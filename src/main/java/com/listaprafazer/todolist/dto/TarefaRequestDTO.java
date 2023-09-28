package com.listaprafazer.todolist.dto;

import com.listaprafazer.todolist.enums.PrioridadesEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class TarefaRequestDTO {

    @NotBlank
    private String titulo;
    private Long usuarioId;
    private String descricao;
    private PrioridadesEnum prioridades;

}
