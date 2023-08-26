package com.example.alunos.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.alunos.domain.model.Aluno;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
   
    Optional<Aluno> findByRA(String rA);
   
}
