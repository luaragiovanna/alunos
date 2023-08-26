
package com.example.alunos.security;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.alunos.domain.model.Aluno;
import com.example.alunos.domain.repository.AlunoRepository;

@Component
public class UserDetailsSecurityServer implements UserDetailsService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public UserDetails loadUserByUsername(String ra) throws UsernameNotFoundException {
       Optional<Aluno> optAluno = alunoRepository.findByRA(ra); //consegue buscar usuario por emil
       if(optAluno.isEmpty()){
        throw new UsernameNotFoundException("RA ou senha Inválidos.");//caso volte vazio
       }
       return optAluno.get();
    }
    
}