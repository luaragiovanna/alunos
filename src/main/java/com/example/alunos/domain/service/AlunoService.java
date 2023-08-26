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
public class AlunoService implements ICRUDService<AlunoResponseDTO, AlunoRequestDTO> {

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
            throw new NotFoundException("Este ID nao existe" + id);
        }

        return mapper.map(optAluno.get(), AlunoResponseDTO.class);
    }

    @Override
    public void deletar(Long id) {
        Optional<Aluno> optAluno = alunoRepository.findById(id);
        if (optAluno.isEmpty()) {
            throw new NotFoundException("Este ID não foi encontrado" + id);
        }
        Aluno aluno = optAluno.get();
        aluno.setDataInativacao(new Date());
        alunoRepository.save(aluno);
    }

    @Override
    public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO dto) {
        AlunoResponseDTO alunoResponseDTO = obterPorId(id);
        
        if(dto.getRA() == null || dto.getSenha() == null){
            throw new BadRequestException("RA e senha são obrigatorios");
        }
        //transformar usuariorequest em usuario da model
        Aluno aluno = mapper.map(dto, Aluno.class);
        aluno.setSenha(dto.getSenha());
        aluno.setDataInativacao(alunoResponseDTO.getDataInativacao());
        aluno.setDataCadastro(alunoResponseDTO.getDataCadastro());
        aluno.setId(id); //cria novo usuario mas com id diferente
        aluno.setDataCadastro(alunoResponseDTO.getDataCadastro());
        aluno.setDataInativacao(alunoResponseDTO.getDataInativacao());
        
        
        aluno = alunoRepository.save(aluno); //salva
        return mapper.map(aluno, AlunoResponseDTO.class);
    }

    @Override
    public AlunoResponseDTO cadastrar(AlunoRequestDTO dto) {

        if (dto.getSenha() == null || dto.getRA() == null) {
            throw new BadRequestException("CAMPOS OBRIGATORIOS: RA E SENHA");

        }
        Optional<Aluno> aluOptional = alunoRepository.findByRA(dto.getRA());
        if (aluOptional.isPresent()) {
            throw new BadRequestException("Já existe um usuário cadastrado com esse RA: " + dto.getRA());
        }
        Aluno aluno = mapper.map(dto, Aluno.class);
        String senha = passwordEncoder.encode(aluno.getSenha());
        aluno.setSenha(senha);
        aluno.setDataCadastro(new Date());
        aluno.setId(null);
        aluno = alunoRepository.save(aluno);
        return mapper.map(aluno, AlunoResponseDTO.class);

    }

}
