package br.com.zupacademy.antonio.proposta.bloqueio;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;
import br.com.zupacademy.antonio.proposta.cartao.CartaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bloqueios/cartoes/{id}")
public class BloqueioController {

    @Autowired
    private BloqueioRepository bloqueioRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @PostMapping
    public ResponseEntity<BloqueioDto> salva(@RequestHeader(value = "User-Agent") String agentUser,
                                                     @RequestHeader(value = "Host") String clientIp,
                                                     @PathVariable String id) {

        BloqueioForm bloqueioForm = new BloqueioForm(clientIp, agentUser);
        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            cartaoOptional.get().verificaStatusCartao();

            Bloqueio bloqueio = bloqueioRepository.save(bloqueioForm.converteParaModelBloqueio(cartaoOptional.get()));

            cartaoOptional.get().alteraStatusCartao();
            cartaoRepository.save(cartaoOptional.get());

            return ResponseEntity.ok().body(new BloqueioDto(bloqueio));
        }
        return ResponseEntity.notFound().build();
    }
}
