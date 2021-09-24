package br.com.zupacademy.antonio.proposta.carteira;

public class CarteiraDigitalDto {

    private String id;
    private ModeloCarteira modeloCarteira;

    public CarteiraDigitalDto(CarteiraDigital carteiraDigital) {
        this.id = carteiraDigital.getId();
        this.modeloCarteira = carteiraDigital.getModeloCarteira();
    }

    public String getId() {
        return id;
    }

    public ModeloCarteira getModeloCarteira() {
        return modeloCarteira;
    }
}
