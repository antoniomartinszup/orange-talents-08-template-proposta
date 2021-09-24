package br.com.zupacademy.antonio.proposta.viagem;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;
import br.com.zupacademy.antonio.proposta.cartao.CartaoRepository;
import br.com.zupacademy.antonio.proposta.viagem.analiseviagem.AnaliseViagemDto;
import br.com.zupacademy.antonio.proposta.viagem.analiseviagem.AnaliseViagemFeign;
import br.com.zupacademy.antonio.proposta.viagem.analiseviagem.AnaliseViagemForm;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/informa/viagens/cartoes/{id}")
public class InformaViagemController {

    private final Logger logger = LoggerFactory.getLogger(InformaViagemController.class);

    @Autowired
    private InformaViagemRepository informaViagemRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AnaliseViagemFeign analiseViagemFeign;


    @PostMapping
    public ResponseEntity<InformaViagemDto> salva(@Valid @RequestBody InformaViagemForm informaViagemForm,
                                   @PathVariable String id,
                                   @RequestHeader(value = "User-Agent") String userAgent,
                                   @RequestHeader(value = "Host") String ip) {

        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            try {
                AnaliseViagemForm analiseViagemForm = new AnaliseViagemForm(
                        informaViagemForm.getDestinoViagem(), informaViagemForm.getInformaFimViagemEm());

                AnaliseViagemDto analiseViagemDto = analiseViagemFeign.informaViagem(id, analiseViagemForm);

                if (analiseViagemDto.getResultado().equals("CRIADO")) {
                    InformaViagem informaViagem = informaViagemRepository.save(
                            informaViagemForm.converteParaModelInformaViagem(cartaoOptional.get(), userAgent, ip));

                    logger.info("Viagem com id {} informada com sucesso na data de {}", informaViagem.getId(),
                            informaViagem.getInformaViagemEm());

                    return ResponseEntity.ok(new InformaViagemDto(informaViagem));
                }
            } catch (FeignException feignException) {
                logger.error("Ocorreu uma FALHA com status {} no informe de viagem do cartão com o numero {}", feignException.status(), id);
                return ResponseEntity.badRequest().build();
            }
        }
        logger.info("Numero do cartão não encontrado para o id {} informado", id);
        return ResponseEntity.notFound().build();
    }
}
