package com.example.alunos.domain.dto.aluno;

import java.util.Date;

import com.example.alunos.domain.Enum.EalunoGenero;

public class AlunoResponseDTO {

    private Long id;
    private String RA;
    private String nome;
    private EalunoGenero genero;
    private String senha;
    private Date dataCadastro;
    private Date dataInativacao;
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

    public String getRA() {
        return RA;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataInativacao() {
        return dataInativacao;
    }

    public void setDataInativacao(Date dataInativacao) {
        this.dataInativacao = dataInativacao;
    }

 
    public void setRA(String rA) {
        RA = rA;
    }

    public EalunoGenero getGenero() {
        return genero;
    }

    public void setGenero(EalunoGenero genero) {
        this.genero = genero;
    }

    

}
