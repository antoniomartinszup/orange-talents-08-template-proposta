package br.com.zupacademy.antonio.proposta.proposta.analise;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "analiseFinanceiraFeign", url = "${feign.client.financeiro}")
public interface AnaliseFinanceiraFeign {

    @PostMapping(value = "/api/solicitacao")
    AnalisePropostaDto buscaAprovacao(@RequestBody AnalisePropostaForm analisePropostaForm);
}
