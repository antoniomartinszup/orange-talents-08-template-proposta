package br.com.zupacademy.antonio.proposta.bloqueio.analisebloqueio;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "analiseBloqueioFeign", url = "${feign.client.cartao}")
public interface AnaliseBloqueioFeign {

    @PostMapping("/api/cartoes/{id}/bloqueios")
    AnaliseBloqueioDto analisebloqueia(@PathVariable String id, AnaliseBloqueioForm analiseBloqueioForm);
}
