package br.com.zupacademy.antonio.proposta.cartao;

import br.com.zupacademy.antonio.proposta.cartao.analisecartao.AnaliseCartaoDto;
import br.com.zupacademy.antonio.proposta.cartao.analisecartao.AnaliseCartaoFeign;
import br.com.zupacademy.antonio.proposta.proposta.Proposta;
import br.com.zupacademy.antonio.proposta.proposta.PropostaRepository;
import br.com.zupacademy.antonio.proposta.proposta.PropostaStatus;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartaoProposta {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private AnaliseCartaoFeign analiseCartaoFeign;

    @Scheduled(fixedDelay = 20000)
    public void cartaoParaProposta() {
        List<Proposta> propostas = propostaRepository.findByPropostaStatusLikeAndCartaoNull(PropostaStatus.ELEGIVEL);
        for (Proposta proposta : propostas) {
            try {
                AnaliseCartaoDto analiseCartaoDto = analiseCartaoFeign.buscaNumeroCartao(proposta.getId());
                Cartao cartao = analiseCartaoDto.converteParaCartao(proposta);
                proposta.setCartao(cartao);
                propostaRepository.save(proposta);
            } catch (FeignException feignException) {
                feignException.printStackTrace();
            }
        }
    }
}
