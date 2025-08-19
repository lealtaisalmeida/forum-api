package br.com.alura.forumapi.controller;

import br.com.alura.forumapi.config.security.TokenService;
import br.com.alura.forumapi.controller.dto.TokenDTO;
import br.com.alura.forumapi.controller.form.AutenticacaoForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Profile(value = {"prd", "test"})
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid AutenticacaoForm form) {

        UsernamePasswordAuthenticationToken credenciais = form.converter();

        try {
            Authentication authentication = authenticationManager.authenticate(credenciais);
            String token = tokenService.gerar(authentication);
            return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
        } catch(AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }

    }

}
