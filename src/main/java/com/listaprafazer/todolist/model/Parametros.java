package com.listaprafazer.todolist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
public class Parametros {

    @Id
    private String nome;
    private String valor;
    private String observacao;
}
