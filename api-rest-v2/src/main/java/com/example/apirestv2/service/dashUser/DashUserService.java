package com.example.apirestv2.service.dashUser;

import com.example.apirestv2.api.configuration.securityDoadores.jwt.GerenciadorTokenJwt;
import com.example.apirestv2.domain.dashUser.DashUser;
import com.example.apirestv2.domain.dashUser.repository.DashUserRepository;
import com.example.apirestv2.service.dashUser.autenticacao.dto.DashUserLoginDTO;
import com.example.apirestv2.service.dashUser.autenticacao.dto.DashUserTokenDTO;
import com.example.apirestv2.service.dashUser.dto.DashUserAlteracaoSenhaDTO;
import com.example.apirestv2.service.dashUser.dto.DashUserCriacaoDTO;
import com.example.apirestv2.service.dashUser.dto.mapper.DashUserMapper;
import com.example.apirestv2.service.emailService.EmailService;
import com.example.apirestv2.service.interfaces.ChangeListener;
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
public class DashUserService implements ChangeListener {
    private final DashUserRepository dashUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final GerenciadorTokenJwt gerenciadorTokenJwt;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    public void cadastrar(DashUserCriacaoDTO doadorCriacaoDto) {
        System.out.println(doadorCriacaoDto);
        final DashUser novoDoador = DashUserMapper.toEntity(doadorCriacaoDto);

        String senhaCriptografada = passwordEncoder.encode(novoDoador.getSenha());
        novoDoador.setSenha(senhaCriptografada);

        this.dashUserRepository.save(novoDoador);
    }

    public DashUserTokenDTO login(DashUserLoginDTO dashUserLoginDto) {

        final UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(
                dashUserLoginDto.getEmail(), dashUserLoginDto.getSenha());

        final Authentication authentication = this.authenticationManager.authenticate(credentials);

        DashUser doadorLogado = dashUserRepository.findByEmail(dashUserLoginDto.getEmail()).orElseThrow(() -> new ResponseStatusException(404, "Email do usuário não cadastrado", null));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return DashUserMapper.toTokenDto(doadorLogado, token);
    }

    public void alterarSenha(String email, DashUserAlteracaoSenhaDTO alterarSenhaDTO) throws Exception {
        DashUser doador = dashUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Doador não encontrado"));

        if (!passwordEncoder.matches(alterarSenhaDTO.getSenhaAtual(), doador.getSenha())) {
            throw new Exception("Senha atual incorreta");
        }

        if (!alterarSenhaDTO.getNovaSenha().equals(alterarSenhaDTO.getRepetirNovaSenha())) {
            throw new Exception("A nova senha e a repetição da nova senha não são iguais");
        }

        doador.setSenha(passwordEncoder.encode(alterarSenhaDTO.getNovaSenha()));
        dashUserRepository.save(doador);
    }

    public DashUser buscarPorId(Long id) {
        return dashUserRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public void updateListener(String email, String eventType) {
        String mensagem = String.format("Atualização: O(a) %s teve o status alterado", eventType);
        emailService.sendMail(email, "Status alterado", mensagem);
    }
}
