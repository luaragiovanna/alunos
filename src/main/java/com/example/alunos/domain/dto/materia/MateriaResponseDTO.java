package com.example.alunos.domain.dto.materia;

import java.util.Date;

public class MateriaResponseDTO {
   
        private Long id;
        private String nome;
        private int cargaHoraria;
        private Date dataInicio;
        private Date dataFim;
        private String codigo;
        private String departamento;
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
        public int getCargaHoraria() {
            return cargaHoraria;
        }
        public void setCargaHoraria(int cargaHoraria) {
            this.cargaHoraria = cargaHoraria;
        }
        public Date getDataInicio() {
            return dataInicio;
        }
        public void setDataInicio(Date dataInicio) {
            this.dataInicio = dataInicio;
        }
        public Date getDataFim() {
            return dataFim;
        }
        public void setDataFim(Date dataFim) {
            this.dataFim = dataFim;
        }
        public String getCodigo() {
            return codigo;
        }
        public void setCodigo(String codigo) {
            this.codigo = codigo;
        }
        public String getDepartamento() {
            return departamento;
        }
        public void setDepartamento(String departamento) {
            this.departamento = departamento;
        }
        public String getProfessor() {
            return professor;
        }
        public void setProfessor(String professor) {
            this.professor = professor;
        }
        private String professor;
    
    
}
