package br.com.zupacademy.antonio.proposta.carteira.analisecarteira;

import br.com.zupacademy.antonio.proposta.carteira.CarteiraDigitalForm;
import br.com.zupacademy.antonio.proposta.carteira.ModeloCarteira;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AnaliseCarteiraDigitalForm {

    @NotBlank
    @Email
    private String email;

    @NotNull
    private ModeloCarteira carteira;

    public AnaliseCarteiraDigitalForm(String email, ModeloCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public AnaliseCarteiraDigitalForm(CarteiraDigitalForm carteiraDigitalForm) {
        this.email = carteiraDigitalForm.getEmail();
        this.carteira = carteiraDigitalForm.getModeloCarteira();
    }

    public String getEmail() {
        return email;
    }

    public ModeloCarteira getCarteira() {
        return carteira;
    }
}
