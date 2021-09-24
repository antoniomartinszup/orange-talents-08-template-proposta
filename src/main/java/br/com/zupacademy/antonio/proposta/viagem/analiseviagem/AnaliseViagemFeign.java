package br.com.zupacademy.antonio.proposta.viagem.analiseviagem;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "analiseViagemFeign", url = "${feign.client.cartao}")
public interface AnaliseViagemFeign {

    @PostMapping("/api/cartoes/{id}/avisos")
    AnaliseViagemDto informaViagem(@PathVariable String id, AnaliseViagemForm analiseViagemForm);
}
