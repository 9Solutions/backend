package com.caixadesapato.api.service;

import com.caixadesapato.api.config.doador.GerenciadorTokenJwt;
import com.caixadesapato.api.dto.doador.*;
import com.caixadesapato.api.model.Doador;
import com.caixadesapato.api.repository.DoadorRepository;
import com.caixadesapato.api.utils.interfaces.ChangeListener;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class DoadorService implements ChangeListener {

    private final DoadorRepository doadorRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

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
        String mensagem = String.format("Atualização: O(a) %s teve o status alterado", eventType);
        emailService.sendMail(email, "Status alterado", mensagem);
    }

}
