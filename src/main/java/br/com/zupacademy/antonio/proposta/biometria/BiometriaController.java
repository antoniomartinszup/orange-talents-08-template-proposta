package br.com.zupacademy.antonio.proposta.biometria;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;
import br.com.zupacademy.antonio.proposta.cartao.CartaoRepository;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/biometrias/cartoes/{id}")
public class BiometriaController {

    private final Logger logger = LoggerFactory.getLogger(BiometriaController.class);
    private final Tracer tracer;

    @Autowired
    private BiometriaRepository biometriaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public BiometriaController(Tracer tracer) {
        this.tracer = tracer;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<BiometriaDto> salva(@Valid @RequestBody BiometriaForm biometriaForm,
                                   @PathVariable String id,
                                   UriComponentsBuilder uriComponentsBuilder) {

        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            if (Base64.isBase64(biometriaForm.getFingerPrint())) {

                Span activeSpan = tracer.activeSpan().setBaggageItem("user.email",
                        cartaoOptional.get().getProposta().getEmail());

                Biometria biometria = biometriaForm.converteParaModelBiometria(cartaoOptional.get());
                biometriaRepository.save(biometria);

                logger.info("Biometria cadastrada com sucesso para {}", cartaoOptional.get().getTitular());
                URI uri = uriComponentsBuilder.path("/biometrias/cartoes/{id}").buildAndExpand(biometria.getId()).toUri();
                return ResponseEntity.created(uri).body(new BiometriaDto(biometria));
            }
            logger.error("Ocorreu uma FALHA com status {}", ResponseEntity.badRequest());
            return ResponseEntity.badRequest().build();
        }
        logger.info("Numero do cartão não encontrado para o id {} informado", id);
        return ResponseEntity.notFound().build();
    }
}
