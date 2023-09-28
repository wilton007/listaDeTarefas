package com.listaprafazer.todolist.model;

import com.listaprafazer.todolist.enums.PrioridadesEnum;
import com.listaprafazer.todolist.enums.StatusTarefaEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Data
public class Tarefa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String titulo;
    @Column(name = "descricao", length = 500)
    private String descricao;
    @NotNull
    @Enumerated(EnumType.STRING)
    private PrioridadesEnum prioridades;
    @NotNull
    @Enumerated(EnumType.STRING)
    private StatusTarefaEnum status;
    @NotNull
    private LocalDate dataInicio;
    private LocalDateTime dataFim;
    @ManyToOne
    @NotNull
    private Usuario usuario;


    public void setStatus(StatusTarefaEnum status) {
        this.status = status;
        if (status.equals(StatusTarefaEnum.COCLUIDA)){
            setDataFim(LocalDateTime.now());
        }
    }
}
