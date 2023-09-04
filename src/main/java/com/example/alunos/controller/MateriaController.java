package com.example.alunos.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.alunos.domain.dto.materia.MateriaRequestDTO;
import com.example.alunos.domain.dto.materia.MateriaResponseDTO;
import com.example.alunos.domain.service.MateriaService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/materias")
public class MateriaController {
    @Autowired
    private MateriaService materiaService;

    @GetMapping
    public ResponseEntity<List<MateriaResponseDTO>> obterTodos(){
        return ResponseEntity.ok(materiaService.obterTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> obterPorId(@PathVariable Long id){
        return ResponseEntity.ok(materiaService.obterPorId(id));
    }

    @PostMapping
    public ResponseEntity<MateriaResponseDTO> cadastrar(@RequestBody MateriaRequestDTO dto){
        MateriaResponseDTO responseDTO = materiaService.cadastrar(dto);
        responseDTO.setDataInicio(new Date());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaResponseDTO> atualizar(@PathVariable Long id, @RequestBody MateriaRequestDTO dto){
        MateriaResponseDTO responseDTO = materiaService.atualizar(id, dto);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id){
        materiaService.deletar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
