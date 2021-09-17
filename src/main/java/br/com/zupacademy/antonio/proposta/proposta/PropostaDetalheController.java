package br.com.zupacademy.antonio.proposta.proposta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/propostas/{id}")
public class PropostaDetalheController {

    @Autowired
    private PropostaRepository propostaRepository;

    @GetMapping
    public ResponseEntity<PropostaDto> detahe(@PathVariable Long id) {

        Optional<Proposta> propostaOptional = propostaRepository.findById(id);
        if (propostaOptional.isPresent()) {
            return ResponseEntity.ok(new PropostaDto(propostaOptional.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
