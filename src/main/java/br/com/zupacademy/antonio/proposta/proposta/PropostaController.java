package br.com.zupacademy.antonio.proposta.proposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private PropostaRepository propostaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<PropostaDto> salvar(@RequestBody @Valid PropostaForm propostaForm,
                                              UriComponentsBuilder uriBuilder) {

        Proposta propostaSalvo = propostaRepository.save(propostaForm.converteParaModelProposta());
        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(propostaSalvo.getId()).toUri();
        return ResponseEntity.created(uri).body(new PropostaDto(propostaSalvo));
    }
}
