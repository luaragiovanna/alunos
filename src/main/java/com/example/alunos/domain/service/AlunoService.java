package com.example.alunos.domain.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.alunos.domain.dto.aluno.AlunoRequestDTO;
import com.example.alunos.domain.dto.aluno.AlunoResponseDTO;
import com.example.alunos.domain.exception.BadRequestException;
import com.example.alunos.domain.exception.NotFoundException;
import com.example.alunos.domain.model.Aluno;
import com.example.alunos.domain.repository.AlunoRepository;

@Service
public class AlunoService implements ICRUDService<AlunoRequestDTO, AlunoResponseDTO> {
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public List<AlunoResponseDTO> obterTodos() {
        List<Aluno> alunos = alunoRepository.findAll();
        return alunos.stream().map(aluno -> mapper.map(aluno, AlunoResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public AlunoResponseDTO obterPorId(Long id) {
        Optional<Aluno> optAluno = alunoRepository.findById(id);
        if (optAluno.isEmpty()) {
            throw new NotFoundException("Aluno não encontrado com o id " + id);
        }
        return mapper.map(optAluno.get(), AlunoResponseDTO.class);
    }

    @Override
    public AlunoResponseDTO cadastrar(AlunoRequestDTO dto) {
        if (dto.getRA() == null || dto.getSenha() == null) {
            throw new BadRequestException("Email e Senha são Obrigatórios!");
        }
        Optional<Aluno> optAluno = alunoRepository.findByRA(dto.getRA());
        if (optAluno.isPresent()) {
            throw new BadRequestException("Já existe um aluno cadastrado com esse RA: " + dto.getRA());
        }
        Aluno aluno = mapper.map(dto, Aluno.class);
        // encriptografar senha
        String senha = passwordEncoder.encode(aluno.getSenha());
        aluno.setSenha(senha);
        aluno.setId(null);
        aluno.setDataCadastro(new Date());
        aluno = alunoRepository.save(aluno);
        return mapper.map(aluno, AlunoResponseDTO.class);
    }

    @Override
public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO dto) {
    AlunoResponseDTO alunoBanco = obterPorId(id);
    if (dto.getRA() == null || dto.getSenha() == null) {
        throw new BadRequestException("RA e Senha são Obrigatórios!");
    }
    
    Aluno aluno = mapper.map(dto, Aluno.class);
    
    // Encriptar a nova senha antes de salvar
    String senhaCriptografada = passwordEncoder.encode(dto.getSenha());
    aluno.setSenha(senhaCriptografada);
    
    aluno.setId(id);
    aluno.setDataInativacao(alunoBanco.getDataInativacao());
    aluno.setDataCadastro(alunoBanco.getDataCadastro());
    aluno = alunoRepository.save(aluno);
    return mapper.map(aluno, AlunoResponseDTO.class);
}


    @Override
    public void deletar(Long id) {
        Optional<Aluno> optAluno = alunoRepository.findById(id);
        if (optAluno.isEmpty()) {
            throw new NotFoundException("Não foi possível encontrar o aluno com id: " + id);
        }
        Aluno aluno = optAluno.get();
        aluno.setDataInativacao(new Date());
        alunoRepository.save(aluno);
    }
}
