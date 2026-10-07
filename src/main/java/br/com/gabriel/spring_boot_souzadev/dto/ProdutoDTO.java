package br.com.gabriel.spring_boot_souzadev.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProdutoDTO {

    private String nome;
    private BigDecimal preco;
    private Integer quantidade;
}
