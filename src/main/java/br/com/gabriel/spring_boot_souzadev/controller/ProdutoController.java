package br.com.gabriel.spring_boot_souzadev.controller;


import br.com.gabriel.spring_boot_souzadev.database.model.ProdutoEntity;
import br.com.gabriel.spring_boot_souzadev.dto.ProdutoDTO;
import br.com.gabriel.spring_boot_souzadev.exeption.NotFoundException;
import br.com.gabriel.spring_boot_souzadev.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/produtos")
@RequiredArgsConstructor
public class ProdutoController {

   /* @Autowired
    private ProdutoService produtoService;*/
    private final ProdutoService produtoService;

    /*public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }*/


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProdutoEntity> findAll(){
        return produtoService.findAll();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoEntity createProduct(@RequestBody ProdutoDTO produtoDTO){
        return produtoService.createProduct(produtoDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoEntity updateProduct(@PathVariable Integer id , @RequestBody ProdutoDTO produtoDto) throws NotFoundException {

        return produtoService.atualizarProduto(produtoDto, id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeProduct(@PathVariable Integer id){
        produtoService.removerProduto(id);
    }

}
