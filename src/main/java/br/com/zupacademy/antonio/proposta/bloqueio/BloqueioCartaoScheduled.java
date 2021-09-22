package br.com.zupacademy.antonio.proposta.bloqueio;

import br.com.zupacademy.antonio.proposta.bloqueio.analisebloqueio.AnaliseBloqueioDto;
import br.com.zupacademy.antonio.proposta.bloqueio.analisebloqueio.AnaliseBloqueioFeign;
import br.com.zupacademy.antonio.proposta.bloqueio.analisebloqueio.AnaliseBloqueioForm;
import br.com.zupacademy.antonio.proposta.cartao.Cartao;
import br.com.zupacademy.antonio.proposta.cartao.CartaoRepository;
import br.com.zupacademy.antonio.proposta.cartao.CartaoStatus;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BloqueioCartaoScheduled {

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private BloqueioRepository bloqueioRepository;

    @Autowired
    private AnaliseBloqueioFeign analiseBloqueioFeign;

    private final Logger logger = LoggerFactory.getLogger(BloqueioCartaoScheduled.class);

    @Scheduled(fixedRateString = "${bloquear.cartao}")
    public void bloqueioLegado() {

        AnaliseBloqueioForm analiseBloqueioForm = new AnaliseBloqueioForm();

        List<Cartao> cartaoList = cartaoRepository.findByCartaoStatusLike(CartaoStatus.PEDIDO_BLOQUEIO);

        if (!cartaoList.isEmpty()) {
            for (Cartao cartao : cartaoList) {
                try {
                    AnaliseBloqueioDto analiseBloqueioDto = analiseBloqueioFeign.analisebloqueia(cartao.getId(), analiseBloqueioForm);
                    if (analiseBloqueioDto.getRespostaAnalise().equals("BLOQUEADO")) {
                        cartao.alteraStatusCartaoBloqueio();
                        cartaoRepository.save(cartao);
                        logger.info("O cartão com o numero {} foi {} com sucesso", cartao.getId(), analiseBloqueioDto.getRespostaAnalise());
                    }
                } catch (FeignException feignException) {
                    logger.error("Ocorreu uma FALHA com status {} no bloqueio do cartão com o numero {}", feignException.status(), cartao.getId());

                    cartao.alteraStatusCartaoAtivo();
                    cartaoRepository.save(cartao);

                    var idRemove = cartao.getBloqueio().getId();
                    var idRetiraDeBloqueio = bloqueioRepository.findById(idRemove).get();
                    bloqueioRepository.delete(idRetiraDeBloqueio);
                    logger.info("Removendo o cartão numero {} da lista de bloqueio com o id {}", cartao.getId(), cartao.getBloqueio().getId());
                }
            }
        }
    }
}
