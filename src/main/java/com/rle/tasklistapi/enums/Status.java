package com.rle.tasklistapi.enums;

public enum Status {
	
    ABERTO("Aberto"),
    ANDAMENTO("Em andamento"),
    CONCLUIDO("Concluido");

    private String descricao;

    Status(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}