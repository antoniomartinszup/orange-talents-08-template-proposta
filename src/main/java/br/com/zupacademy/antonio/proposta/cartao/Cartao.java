package br.com.zupacademy.antonio.proposta.cartao;

import br.com.zupacademy.antonio.proposta.biometria.Biometria;
import br.com.zupacademy.antonio.proposta.bloqueio.Bloqueio;
import br.com.zupacademy.antonio.proposta.carteira.CarteiraDigital;
import br.com.zupacademy.antonio.proposta.carteira.ModeloCarteira;
import br.com.zupacademy.antonio.proposta.proposta.Proposta;
import br.com.zupacademy.antonio.proposta.viagem.InformaViagem;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cartao {

    @Id
    @NotBlank
    private String id;

    @NotNull
    private String titular;

    @NotNull
    private LocalDateTime geradoEm;

    @NotNull
    private BigDecimal limite;

    @Enumerated(EnumType.STRING)
    private CartaoStatus cartaoStatus = CartaoStatus.ATIVO;

    @NotNull
    @OneToOne(mappedBy = "cartao")
    private Proposta proposta;

    @OneToOne(mappedBy = "cartao")
    private Bloqueio bloqueio;

    @OneToMany(mappedBy = "cartao")
    private List<Biometria> biometrias = new ArrayList<>();

    @OneToMany(mappedBy = "cartao")
    private List<InformaViagem> informaViagemList;

    @OneToMany(mappedBy = "cartao")
    private List<CarteiraDigital> carteiraDigitalList;

    @Deprecated
    public Cartao() {
    }

    public Cartao(String id, String titular, LocalDateTime geradoEm, BigDecimal limite, Proposta proposta) {
        this.id = id;
        this.titular = titular;
        this.geradoEm = geradoEm;
        this.limite = limite;
        this.proposta = proposta;
    }

    public void alteraStatusCartaoPedidoBloqueio() {
        this.cartaoStatus = CartaoStatus.PEDIDO_BLOQUEIO;
    }

    public void alteraStatusCartaoBloqueio() {
        this.cartaoStatus = CartaoStatus.BLOQUEADO;
    }

    public void alteraStatusCartaoAtivo() {
        this.cartaoStatus = CartaoStatus.ATIVO;
    }

    public void verificaStatusCartao() {
        if (this.cartaoStatus == CartaoStatus.PEDIDO_BLOQUEIO) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    public boolean verificaAssociacao(ModeloCarteira modeloCarteira) {
        for (CarteiraDigital carteiraDigital : carteiraDigitalList) {
            if (carteiraDigital.getModeloCarteira().equals(modeloCarteira)) {
                return true;
            }
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public String getTitular() {
        return titular;
    }

    public LocalDateTime getGeradoEm() {
        return geradoEm;
    }

    public Proposta getProposta() {
        return proposta;
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public List<Biometria> getBiometrias() {
        return biometrias;
    }

    public CartaoStatus getCartaoStatus() {
        return cartaoStatus;
    }

    public Bloqueio getBloqueio() {
        return bloqueio;
    }

    public List<InformaViagem> getInformaViagemList() {
        return informaViagemList;
    }

    public List<CarteiraDigital> getCarteiraDigitalList() {
        return carteiraDigitalList;
    }

}
