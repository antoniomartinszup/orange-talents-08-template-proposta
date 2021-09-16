package br.com.zupacademy.antonio.proposta.cartao.analisecartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "analiseCartaoFeign", url = "${feign.client.cartao}")
public interface AnaliseCartaoFeign {

    @GetMapping(value = "/api/cartoes")
    AnaliseCartaoDto buscaNumeroCartao(@RequestParam(value = "idProposta") Long id);
}

