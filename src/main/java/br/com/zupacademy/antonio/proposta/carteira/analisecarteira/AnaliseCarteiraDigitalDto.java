package br.com.zupacademy.antonio.proposta.carteira.analisecarteira;

public class AnaliseCarteiraDigitalDto {

    private String resultado;
    private String id;

    public AnaliseCarteiraDigitalDto(String resultado, String id) {
        this.resultado = resultado;
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }
}
