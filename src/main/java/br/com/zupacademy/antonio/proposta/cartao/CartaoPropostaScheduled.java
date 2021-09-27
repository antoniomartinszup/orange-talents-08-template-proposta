package br.com.zupacademy.antonio.proposta.cartao;

import br.com.zupacademy.antonio.proposta.cartao.analisecartao.AnaliseCartaoDto;
import br.com.zupacademy.antonio.proposta.cartao.analisecartao.AnaliseCartaoFeign;
import br.com.zupacademy.antonio.proposta.proposta.Proposta;
import br.com.zupacademy.antonio.proposta.proposta.PropostaRepository;
import br.com.zupacademy.antonio.proposta.proposta.PropostaStatus;
import br.com.zupacademy.antonio.proposta.security.Mascara;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartaoPropostaScheduled {

    private final Logger logger = LoggerFactory.getLogger(CartaoPropostaScheduled.class);

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private AnaliseCartaoFeign analiseCartaoFeign;

    @Scheduled(fixedRateString = "${analise.cartao}")
    public void cartaoParaProposta() {
        List<Proposta> propostas = propostaRepository.findByPropostaStatusLikeAndCartaoNull(PropostaStatus.ELEGIVEL);
        for (Proposta proposta : propostas) {
            try {
                AnaliseCartaoDto analiseCartaoDto = analiseCartaoFeign.buscaNumeroCartao(proposta.getId());
                Cartao cartao = analiseCartaoDto.converteParaCartao(proposta);
                proposta.setCartao(cartao);
                propostaRepository.save(proposta);
                logger.info("A proposta com id {} foi associada ao cartao com id {}", proposta.getId(), Mascara.numeroCartao(cartao.getId()));
            } catch (FeignException feignException) {
                logger.error("Ocorreu uma FALHA com status {} no bloqueio do cartão com o numero {}",
                        feignException.status(), Mascara.numeroCartao(proposta.getCartao().getId()));
                feignException.printStackTrace();
            }
        }
    }
}
