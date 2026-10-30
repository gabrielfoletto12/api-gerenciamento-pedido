package br.com.gabriel.spring_boot_souzadev.controller;


import br.com.gabriel.spring_boot_souzadev.database.model.ProdutoEntity;
import br.com.gabriel.spring_boot_souzadev.dto.PageResponse;
import br.com.gabriel.spring_boot_souzadev.dto.ProdutoDTO;
import br.com.gabriel.spring_boot_souzadev.dto.ProdutoPatchDTO;
import br.com.gabriel.spring_boot_souzadev.exeption.NotFoundException;
import br.com.gabriel.spring_boot_souzadev.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;


@RestController
@RequestMapping("/v1/produtos")
@RequiredArgsConstructor
@Tag(name = "Produtos", description = "CRUD de produtos (REST)")
public class ProdutoController {

    private final ProdutoService produtoService;


    @GetMapping
    @Operation(summary = "Lista produtos com filtros e paginação",
            description = "Filtros opcionais: nome, precoMin, precoMax. Paginação: page (começa em 0) e size (1 a 100).")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista paginada de produtos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    public ResponseEntity<PageResponse<ProdutoEntity>> findAll(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) BigDecimal precoMin,
            @RequestParam(required = false) BigDecimal precoMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(produtoService.findAll(nome, precoMin, precoMax, page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um produto pelo id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "400", description = "Id inválido"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    public ResponseEntity<ProdutoEntity> findById(@PathVariable Integer id) throws NotFoundException {
        return ResponseEntity.ok(produtoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria um produto")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Produto criado (header Location com a URI)"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "409", description = "Já existe produto com esse nome")
    })
    public ResponseEntity<ProdutoEntity> createProduct(@Valid @RequestBody ProdutoDTO produtoDTO){
        ProdutoEntity criado = produtoService.createProduct(produtoDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(criado.getId())
                .toUri();
        return ResponseEntity.created(location).body(criado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Substitui o produto inteiro (todos os campos)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "409", description = "Já existe produto com esse nome")
    })
    public ResponseEntity<ProdutoEntity> updateProduct(@PathVariable Integer id , @Valid @RequestBody ProdutoDTO produtoDto) throws NotFoundException {
        return ResponseEntity.ok(produtoService.atualizarProduto(produtoDto, id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza apenas os campos enviados (parcial)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto atualizado parcialmente"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou corpo vazio"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "409", description = "Já existe produto com esse nome")
    })
    public ResponseEntity<ProdutoEntity> patchProduct(@PathVariable Integer id, @Valid @RequestBody ProdutoPatchDTO patchDto) throws NotFoundException {
        return ResponseEntity.ok(produtoService.atualizarParcialmente(patchDto, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove um produto (idempotente)",
            description = "Devolve 204 mesmo se o produto já tiver sido removido: repetir a chamada não muda o estado final.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Produto removido")
    })
    public void removeProduct(@PathVariable Integer id){
        produtoService.removerProduto(id);
    }

}
