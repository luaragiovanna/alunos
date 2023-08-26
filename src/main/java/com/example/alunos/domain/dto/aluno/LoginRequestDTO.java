package com.example.alunos.domain.dto.aluno;

public class LoginRequestDTO {
    public String getRA() {
        return ra;
    }

    public void setRA(String ra) {
        this.ra = ra;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    private String ra;
    private String senha;
}
