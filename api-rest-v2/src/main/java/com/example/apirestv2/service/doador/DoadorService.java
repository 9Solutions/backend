package com.example.apirestv2.service.doador;

import com.example.apirestv2.api.configuration.security.jwt.GerenciadorTokenJwt;
import com.example.apirestv2.domain.doador.Doador;
import com.example.apirestv2.domain.doador.repository.DoadorRepository;
import com.example.apirestv2.service.doador.autenticacao.dto.DoadorLoginDTO;
import com.example.apirestv2.service.doador.autenticacao.dto.DoadorTokenDTO;
import com.example.apirestv2.service.doador.dto.DoadorAlteracaoSenhaDTO;
import com.example.apirestv2.service.doador.dto.DoadorCriacaoDTO;
import com.example.apirestv2.service.doador.dto.mapper.DoadorMapper;
import com.example.apirestv2.service.interfaces.ChangeListener;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoadorService implements ChangeListener {

    private final DoadorRepository doadorRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;

    public void cadastrar(DoadorCriacaoDTO doadorCriacaoDto) {
        System.out.println(doadorCriacaoDto);
        final Doador novoDoador = DoadorMapper.toEntity(doadorCriacaoDto);

        String senhaCriptografada = passwordEncoder.encode(novoDoador.getSenha());
        novoDoador.setSenha(senhaCriptografada);

        this.doadorRepository.save(novoDoador);
    }

    public DoadorTokenDTO login(DoadorLoginDTO doadorLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                doadorLoginDto.getEmail(), doadorLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        Doador doadorLogado = doadorRepository.findByEmail(doadorLoginDto.getEmail()).orElseThrow(() -> new ResponseStatusException(404, "Email do doador não cadastrado", null));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return DoadorMapper.toTokenDto(doadorLogado, token);
    }

    public void alterarSenha(String email, DoadorAlteracaoSenhaDTO alterarSenhaDTO) throws Exception {
        Doador doador = doadorRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Doador não encontrado"));

        if (!passwordEncoder.matches(alterarSenhaDTO.getSenhaAtual(), doador.getSenha())) {
            throw new Exception("Senha atual incorreta");
        }

        if (!alterarSenhaDTO.getNovaSenha().equals(alterarSenhaDTO.getRepetirNovaSenha())) {
            throw new Exception("A nova senha e a repetição da nova senha não são iguais");
        }

        doador.setSenha(passwordEncoder.encode(alterarSenhaDTO.getNovaSenha()));
        doadorRepository.save(doador);
    }

    public Doador buscarPorId(Long id) {
        return doadorRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public void updateListener(String email, String eventType) {
        System.out.println(String.format("Enviado para: %s | Atualização: O(a) %s teve o status alterado", email, eventType));
    }

}
