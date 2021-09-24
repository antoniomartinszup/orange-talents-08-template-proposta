package br.com.zupacademy.antonio.proposta.carteira;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarteiraDigitalRepository extends JpaRepository<CarteiraDigital, String> {
}
