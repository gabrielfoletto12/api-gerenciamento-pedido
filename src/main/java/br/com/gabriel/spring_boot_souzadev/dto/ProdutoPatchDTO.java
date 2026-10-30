package br.com.gabriel.spring_boot_souzadev.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.math.BigDecimal;

/**
 * Usado no PATCH: todos os campos são opcionais.
 * Só o que for enviado é alterado.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProdutoPatchDTO {

    @Pattern(regexp = ".*\\S.*", message = "O nome não pode ser vazio")
    private String nome;

    @Positive(message = "O preço deve ser maior que zero")
    private BigDecimal preco;

    @PositiveOrZero(message = "A quantidade não pode ser negativa")
    private Integer quantidade;
}
