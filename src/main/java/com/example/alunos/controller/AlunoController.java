package com.example.alunos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.alunos.domain.dto.aluno.AlunoRequestDTO;
import com.example.alunos.domain.dto.aluno.AlunoResponseDTO;
import com.example.alunos.domain.service.AlunoService;


@CrossOrigin("*")
@RestController
@RequestMapping("/api/alunos")
public class AlunoController {


    @Autowired
    private AlunoService alunoService;



    @GetMapping
    public ResponseEntity<List<AlunoResponseDTO>> obterTodos(){
        return ResponseEntity.ok(alunoService.obterTodos());
    }


    @GetMapping("/{id}")
    public ResponseEntity<AlunoResponseDTO> obterPorId(@PathVariable Long id){
          return ResponseEntity.ok(alunoService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<AlunoResponseDTO> cadastrar(@RequestBody AlunoRequestDTO dto){
        AlunoResponseDTO aluno = alunoService.cadastrar(dto);
        return new ResponseEntity<AlunoResponseDTO>(aluno, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        alunoService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

