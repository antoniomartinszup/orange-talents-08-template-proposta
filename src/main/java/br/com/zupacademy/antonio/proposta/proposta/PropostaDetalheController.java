package br.com.zupacademy.antonio.proposta.proposta;

import br.com.zupacademy.antonio.proposta.proposta.metricasproposta.PropostaMetricas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(PropostaDetalheController.class);

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private PropostaMetricas propostaMetricas;

    @GetMapping
    public ResponseEntity<PropostaDto> detahe(@PathVariable Long id) {

        propostaMetricas.timer();
        Optional<Proposta> propostaOptional = propostaRepository.findById(id);
        if (propostaOptional.isPresent()) {
            logger.info("A Prosposta com o id {} tem o status {}", propostaOptional.get().getId(), propostaOptional.get().getPropostaStatus());
            return ResponseEntity.ok(new PropostaDto(propostaOptional.get()));
        }
        logger.error("A Prosposta com o id {} não foi encontrada", id);
        return ResponseEntity.notFound().build();
    }
}
