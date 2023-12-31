package com.example.alunos.security;
import java.io.IOException;
import java.util.Date;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.alunos.common.ConversorData;
import com.example.alunos.domain.dto.aluno.AlunoResponseDTO;
import com.example.alunos.domain.dto.aluno.LoginRequestDTO;
import com.example.alunos.domain.dto.aluno.LoginResponseDTO;
import com.example.alunos.domain.model.Aluno;
import com.example.alunos.domain.model.ErroResposta;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    private AuthenticationManager authenticationManager;
    
    private JwtUtil jwtUtil;
    //@Autowired
    //private ModelMapper mapper;

    public JwtAuthenticationFilter (AuthenticationManager authenticationManager, JwtUtil jwtUtil){
        super();
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        setFilterProcessesUrl("/api/auth");
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
      //qm faz a autenticacao
      //primeiro try mapper transforma em login o q vem na request
      //cria token da utenticacao email e senha
      //manda autenticar token
      //se n dar certo(1= usuario senha invalido; 2= erro que nao pode ser tratado)
     try{
          LoginRequestDTO login = new ObjectMapper().readValue(request.getInputStream(), LoginRequestDTO.class);
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getRA(), login.getSenha());
          Authentication auth = authenticationManager.authenticate(authToken);
          return auth;
      }catch(BadCredentialsException e){//nao compativeis cm credenciais
          throw new BadCredentialsException("ra ou senha Invalidos");
      }catch(Exception e){//nao tem como tratar erro
          throw new InternalAuthenticationServiceException(e.getMessage());
      }
  }
    


    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException{

        Aluno aluno = (Aluno) authResult.getPrincipal();
        String token = jwtUtil.gerarToken(authResult);
        AlunoResponseDTO alunoResponseDTO = new AlunoResponseDTO();
        alunoResponseDTO.setId(aluno.getId());
        alunoResponseDTO.setNome(aluno.getNome());
        alunoResponseDTO.setGenero(aluno.getGenero());
        alunoResponseDTO.setDataCadastro(aluno.getDataCadastro());
        alunoResponseDTO.setDataInativacao(aluno.getDataInativacao());
        //mapear o token
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setToken("Bearer " + token);
        loginResponseDTO.setAluno(alunoResponseDTO);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(loginResponseDTO));



    }

     @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException{

        String dataHora = ConversorData.converterDateParaDataHora(new Date());
        ErroResposta resposta =  new ErroResposta(dataHora, 401, "Unauthorized", failed.getMessage());
        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(new Gson().toJson(resposta));
        

    }
      
}