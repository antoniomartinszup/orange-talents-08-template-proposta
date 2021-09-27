package br.com.zupacademy.antonio.proposta.validate;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {UniqueValueValidator.class})
@Target(ElementType.FIELD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueValue {
    String message() default "Item já cadastrado";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}