package br.com.zupacademy.antonio.proposta.cartao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, String> {

    List<Cartao> findByCartaoStatusLike(CartaoStatus pedidoBloqueio);

}
