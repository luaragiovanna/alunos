package com.example.alunos.domain.Enum;

public enum EalunoGenero {
    M("Masculino"),
    F("Feminino"),
    N("Prefiro Não dizer");

    private final String descricao;

    EalunoGenero(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
