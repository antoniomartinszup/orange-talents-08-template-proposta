package br.com.zupacademy.antonio.proposta.proposta;

import br.com.zupacademy.antonio.proposta.proposta.analiseproposta.AnaliseFinanceiraFeign;
import br.com.zupacademy.antonio.proposta.proposta.metricasproposta.PropostaMetricas;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(PropostaController.class);
    private final Tracer tracer;

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private AnaliseFinanceiraFeign analiseFinanceiraFeign;

    @Autowired
    private PropostaMetricas propostaMetricas;

    public PropostaController(Tracer tracer) {
        this.tracer = tracer;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PropostaDto> salvar(@RequestBody @Valid PropostaForm propostaForm,
                                              UriComponentsBuilder uriBuilder) {

        Span activeSpan = tracer.activeSpan().setBaggageItem("user.email", propostaForm.getEmail());

        Proposta propostaSalva = propostaRepository.save(propostaForm.converteParaModelProposta(propostaRepository));
        propostaSalva.propostaStatus(analiseFinanceiraFeign);

        propostaMetricas.contador();
        logger.info("A Proposta para o cliente {} foi gerada com sucesso com o id {}", propostaSalva.getNome(), propostaSalva.getId());

        URI uri = uriBuilder.path("/propostas/{id}").buildAndExpand(propostaSalva.getId()).toUri();
        return ResponseEntity.created(uri).body(new PropostaDto(propostaSalva));
    }
}
