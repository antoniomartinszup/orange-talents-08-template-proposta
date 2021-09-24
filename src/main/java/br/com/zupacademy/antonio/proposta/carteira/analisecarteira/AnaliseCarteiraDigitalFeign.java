package br.com.zupacademy.antonio.proposta.carteira.analisecarteira;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "analiseCarteiraDigitalFeign", url = "${feign.client.cartao}")
public interface AnaliseCarteiraDigitalFeign {

    @PostMapping("/api/cartoes/{id}/carteiras")
    AnaliseCarteiraDigitalDto buscaNumeroCarteira(@PathVariable String id,
                                                  AnaliseCarteiraDigitalForm analiseCarteiraDigitalForm);
}
