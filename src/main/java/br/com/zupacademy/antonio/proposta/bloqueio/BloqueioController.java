package br.com.zupacademy.antonio.proposta.bloqueio;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;
import br.com.zupacademy.antonio.proposta.cartao.CartaoRepository;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bloqueios/cartoes/{id}")
public class BloqueioController {

    private final Logger logger = LoggerFactory.getLogger(BloqueioController.class);
    private final Tracer tracer;

    @Autowired
    private BloqueioRepository bloqueioRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    public BloqueioController(Tracer tracer) {
        this.tracer = tracer;
    }

    @PostMapping
    public ResponseEntity<BloqueioDto> salva(@RequestHeader(value = "User-Agent") String agentUser,
                                                     @RequestHeader(value = "Host") String clientIp,
                                                     @PathVariable String id) {

        BloqueioForm bloqueioForm = new BloqueioForm(clientIp, agentUser);
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            cartaoOptional.get().verificaStatusCartao();

            Span activeSpan = tracer.activeSpan().setBaggageItem("user.email",
                    cartaoOptional.get().getProposta().getEmail());

            Bloqueio bloqueio = bloqueioRepository.save(bloqueioForm.converteParaModelBloqueio(cartaoOptional.get()));
            cartaoOptional.get().alteraStatusCartaoPedidoBloqueio();
            cartaoRepository.save(cartaoOptional.get());

            logger.info("O cartão com o numero {} foi {} com sucesso", cartaoOptional.get().getId(),
                    cartaoOptional.get().getCartaoStatus());

            return ResponseEntity.ok().body(new BloqueioDto(bloqueio));
        }
        logger.info("Numero do cartão não encontrado para o id {} informado", id);
        return ResponseEntity.notFound().build();
    }
}
