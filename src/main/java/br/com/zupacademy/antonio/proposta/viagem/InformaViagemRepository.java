package br.com.zupacademy.antonio.proposta.viagem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InformaViagemRepository extends JpaRepository<InformaViagem, String> {
}
