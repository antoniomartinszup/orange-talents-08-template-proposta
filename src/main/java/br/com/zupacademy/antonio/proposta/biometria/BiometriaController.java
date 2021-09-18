package br.com.zupacademy.antonio.proposta.biometria;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;
import br.com.zupacademy.antonio.proposta.cartao.CartaoRepository;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/biometrias/cartoes/{id}")
public class BiometriaController {

    @Autowired
    private BiometriaRepository biometriaRepository;

    @Autowired
    private CartaoRepository cartaoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<BiometriaDto> salva(@Valid @RequestBody BiometriaForm biometriaForm,
                                   @PathVariable String id,
                                   UriComponentsBuilder uriComponentsBuilder) {

        Optional<Cartao> cartaoOptional = cartaoRepository.findById(id);
        if (cartaoOptional.isPresent()) {
            if (Base64.isBase64(biometriaForm.getFingerPrint())) {
                Biometria biometria = biometriaForm.converteParaModelBiometria(cartaoOptional.get());
                biometriaRepository.save(biometria);
                URI uri = uriComponentsBuilder.path("/biometrias/cartoes/{id}").buildAndExpand(biometria.getId()).toUri();
                return ResponseEntity.created(uri).body(new BiometriaDto(biometria));
            }
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.notFound().build();
    }
}
