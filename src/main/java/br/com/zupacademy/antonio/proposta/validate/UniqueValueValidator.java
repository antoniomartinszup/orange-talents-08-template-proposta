package br.com.zupacademy.antonio.proposta.validate;

import br.com.zupacademy.antonio.proposta.security.CryptoUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue,Object> {

    @PersistenceContext
    private final EntityManager entityManager;

    public UniqueValueValidator(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        String jpql = "SELECT 1 FROM Proposta WHERE documento = :value";
        List<?> list = entityManager.createQuery(jpql).setParameter("value", CryptoUtil.encrypt((String) value)).getResultList();

        return list.isEmpty();
    }
}
