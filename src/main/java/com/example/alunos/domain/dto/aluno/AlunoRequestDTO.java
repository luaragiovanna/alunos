package com.example.alunos.domain.dto.aluno;

import com.example.alunos.domain.Enum.EalunoGenero;

public class AlunoRequestDTO {
   
    private String nome;
   
    private String RA;
   
    private String senha;

    private EalunoGenero genero;
    
    private String curso;
    private String nomeFaculdade;
   
    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNomeFaculdade() {
        return nomeFaculdade;
    }

    public void setNomeFaculdade(String nomeFaculdade) {
        this.nomeFaculdade = nomeFaculdade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRA() {
        return RA;
    }

    public void setRA(String rA) {
        RA = rA;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public EalunoGenero getGenero() {
        return genero;
    }

    public void setGenero(EalunoGenero genero) {
        this.genero = genero;
    }

}






