package br.com.zupacademy.antonio.proposta.carteira;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;
import br.com.zupacademy.antonio.proposta.cartao.CartaoRepository;
import br.com.zupacademy.antonio.proposta.carteira.analisecarteira.AnaliseCarteiraDigitalDto;
import br.com.zupacademy.antonio.proposta.carteira.analisecarteira.AnaliseCarteiraDigitalFeign;
import br.com.zupacademy.antonio.proposta.carteira.analisecarteira.AnaliseCarteiraDigitalForm;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/carteiras/cartoes/{id}")
public class CarteiraDigitalController {

    private final Logger logger = LoggerFactory.getLogger(CarteiraDigitalController.class);

    @Autowired
    private CarteiraDigitalRepository carteiraDigitalRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private AnaliseCarteiraDigitalFeign analiseCarteiraDigitalFeign;

    @PostMapping
    public ResponseEntity<CarteiraDigitalDto> salva(@PathVariable String id,
                                                    @Valid @RequestBody CarteiraDigitalForm carteiraDigitalForm,
                                                    UriComponentsBuilder uriComponentsBuilder) {
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            if (cartaoOptional.get().verificaAssociacao(carteiraDigitalForm.getModeloCarteira())) {
                logger.info("Cartão com numero {} ja esta associado com sucesso com {}",
                        cartaoOptional.get().getId(), carteiraDigitalForm.getModeloCarteira());
                return ResponseEntity.unprocessableEntity().build();
            }
            try {
                AnaliseCarteiraDigitalForm analiseCarteiraDigitalForm = new AnaliseCarteiraDigitalForm(
                        carteiraDigitalForm);
                AnaliseCarteiraDigitalDto analiseCarteiraDigitalDto = analiseCarteiraDigitalFeign.buscaNumeroCarteira(
                        id, analiseCarteiraDigitalForm);

                if (analiseCarteiraDigitalDto.getResultado().equals("ASSOCIADA")) {
                    CarteiraDigital carteiraDigital = carteiraDigitalRepository.save(
                            carteiraDigitalForm.converteParaModelCarteiraDigital(analiseCarteiraDigitalDto,
                                    cartaoOptional.get()));

                    URI uri = uriComponentsBuilder.path("/carteiras/cartoes/{id}")
                            .buildAndExpand(carteiraDigital.getId()).toUri();

                    logger.info("Cartão com numero {} associado com sucesso com o id {}",
                            cartaoOptional.get().getId(), carteiraDigital.getId());

                    return ResponseEntity.created(uri).body(new CarteiraDigitalDto(carteiraDigital));
                }
            } catch (FeignException feignException) {
                logger.error("Ocorreu uma FALHA com status {} na associacao do cartão com o numero {}",
                        feignException.status(), id);
                return ResponseEntity.unprocessableEntity().build();
            }
        }
        logger.info("Numero do cartão não encontrado para o id {} informado", id);
        return ResponseEntity.notFound().build();
    }
}
