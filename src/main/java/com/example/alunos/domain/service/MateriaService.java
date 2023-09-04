package com.example.alunos.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.alunos.domain.dto.materia.MateriaRequestDTO;
import com.example.alunos.domain.dto.materia.MateriaResponseDTO;
import com.example.alunos.domain.exception.BadRequestException;
import com.example.alunos.domain.exception.NotFoundException;
import com.example.alunos.domain.model.Aluno;
import com.example.alunos.domain.model.Materia;
import com.example.alunos.domain.repository.MateriaRepository;

@Service
public class MateriaService implements ICRUDService<MateriaRequestDTO, MateriaResponseDTO> {
    @Autowired
    private MateriaRepository materiaRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public List<MateriaResponseDTO> obterTodos() {
        Aluno aluno = (Aluno) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Teste" + aluno);
        List<Materia> materias = materiaRepository.findByAluno(aluno);
        return materias.stream().map(materia -> mapper.map(materia, MateriaResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public MateriaResponseDTO obterPorId(Long id) {
        Optional<Materia> optMateria = materiaRepository.findById(id);
        if (optMateria.isEmpty()) {
            throw new NotFoundException("Não foi possível encontrar a matéria com o id:" + id);
        }
        return mapper.map(optMateria.get(), MateriaResponseDTO.class);
    }

    @Override
    public MateriaResponseDTO cadastrar(MateriaRequestDTO dto) {
        validarMateria(dto);
        Materia materia = mapper.map(dto, Materia.class);
        Aluno aluno = (Aluno) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        materia.setDataInicio(new Date());  
        materia.setAluno(aluno);
        materia.setId(null);
        materia = materiaRepository.save(materia);
        return mapper.map(materia, MateriaResponseDTO.class);
        
    }

    @Override
public MateriaResponseDTO atualizar(Long id, MateriaRequestDTO dto) {
    obterPorId(id); // Obtém a entidade existente
    validarMateria(dto);
    Materia materia = mapper.map(dto, Materia.class);
    Aluno aluno = (Aluno) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    materia.setAluno(aluno);
    materia.setId(id);
    materia.setDataInicio(new Date());  
    // Atualiza os campos da entidade existente com base no DTO
    materia.setNome(dto.getNome());
    materia.setCodigo(dto.getCodigo());
    materia.setCargaHoraria(dto.getCargaHoraria());
    materia.setProfessor(dto.getProfessor());
    materia.setDepartamento(dto.getDepartamento());
    materia = materiaRepository.save(materia); // Atualiza a entidade no banco de dados
    return mapper.map(materia, MateriaResponseDTO.class);
}

    @Override
    public void deletar(Long id) {
        obterPorId(id);
        materiaRepository.deleteById(id);
    }

    private void validarMateria(MateriaRequestDTO dto) {
        if (dto.getNome() == null || dto.getCodigo() == null || dto.getProfessor() == null) {
            throw new BadRequestException("Matéria Inválida - os campos são obrigatórios!");
        }
    }
}
