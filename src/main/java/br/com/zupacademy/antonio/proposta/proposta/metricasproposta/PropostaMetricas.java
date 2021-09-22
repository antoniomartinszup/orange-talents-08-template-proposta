package br.com.zupacademy.antonio.proposta.proposta.metricasproposta;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Timer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class PropostaMetricas {

    private final MeterRegistry meterRegistry;

    public PropostaMetricas(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    public void contador() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "ELO"));
        tags.add(Tag.of("banco", "Itaú"));

        Counter contadorPropostas = this.meterRegistry.counter("proposta_criada", tags);
        contadorPropostas.increment();
    }

    public void timer() {
        Collection<Tag> tags = new ArrayList<>();
        tags.add(Tag.of("emissora", "ELO"));
        tags.add(Tag.of("banco", "Itaú"));

        Timer timerProposta = this.meterRegistry.timer("consultar_proposta", tags);
        timerProposta.record(this::detalheProposta);
    }

    private void detalheProposta() {
    }
}
