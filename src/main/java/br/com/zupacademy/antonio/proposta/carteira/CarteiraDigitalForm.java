package br.com.zupacademy.antonio.proposta.carteira;

import br.com.zupacademy.antonio.proposta.cartao.Cartao;
import br.com.zupacademy.antonio.proposta.carteira.analisecarteira.AnaliseCarteiraDigitalDto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CarteiraDigitalForm {

    @NotBlank
    @Email
    private String email;

    @NotNull
    private ModeloCarteira modeloCarteira;

    public CarteiraDigitalForm(String email, ModeloCarteira modeloCarteira) {
        this.email = email;
        this.modeloCarteira = modeloCarteira;
    }

    public CarteiraDigital converteParaModelCarteiraDigital(
            AnaliseCarteiraDigitalDto analiseCarteiraDigitalDto, Cartao cartao) {
        return new CarteiraDigital(analiseCarteiraDigitalDto.getId(), this.email, this.modeloCarteira, cartao);
    }

    public String getEmail() {
        return email;
    }

    public ModeloCarteira getModeloCarteira() {
        return modeloCarteira;
    }
}
