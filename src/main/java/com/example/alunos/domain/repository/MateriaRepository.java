package com.example.alunos.domain.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.alunos.domain.model.Aluno;
import com.example.alunos.domain.model.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long>{
        List<Materia> findByAluno(Aluno aluno);

        
    }

