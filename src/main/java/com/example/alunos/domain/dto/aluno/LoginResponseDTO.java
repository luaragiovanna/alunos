package com.example.alunos.domain.dto.aluno;

public class LoginResponseDTO {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AlunoResponseDTO getAluno() {
        return aluno;
    }

    public void setAluno(AlunoResponseDTO aluno) {
        this.aluno = aluno;
    }

    private String token;
    private AlunoResponseDTO aluno;

}
