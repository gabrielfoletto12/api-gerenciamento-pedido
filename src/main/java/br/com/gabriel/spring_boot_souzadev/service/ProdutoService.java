package br.com.gabriel.spring_boot_souzadev.service;


import br.com.gabriel.spring_boot_souzadev.database.model.ProdutoEntity;
import br.com.gabriel.spring_boot_souzadev.dto.PageResponse;
import br.com.gabriel.spring_boot_souzadev.dto.ProdutoDTO;
import br.com.gabriel.spring_boot_souzadev.dto.ProdutoPatchDTO;
import br.com.gabriel.spring_boot_souzadev.exeption.BadRequestException;
import br.com.gabriel.spring_boot_souzadev.exeption.ConflictException;
import br.com.gabriel.spring_boot_souzadev.exeption.NotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProdutoService {


        private static final List<ProdutoEntity> PRODUTOS = new ArrayList<>();

        static{
            PRODUTOS.add(ProdutoEntity.builder()
                    .id(1)
                    .nome("Notebook")
                    .preco(new BigDecimal(5000))
                    .quantidade(10)
                    .build());
            PRODUTOS.add(ProdutoEntity.builder()
                            .id(2)
                            .nome("Iphone")
                            .preco(new BigDecimal(7000))
                            .quantidade(10)
                            .build());
            PRODUTOS.add(ProdutoEntity.builder()
                            .id(3)
                            .nome("Mouse")
                            .preco(new BigDecimal(500))
                            .quantidade(10)
                            .build());
        }

        // GET /v1/produtos -> filtros + paginação
        public synchronized PageResponse<ProdutoEntity> findAll(String nome, BigDecimal precoMin,
                                                                BigDecimal precoMax, int page, int size){
            if (page < 0) {
                throw new BadRequestException("O parâmetro 'page' deve ser maior ou igual a 0");
            }
            if (size < 1 || size > 100) {
                throw new BadRequestException("O parâmetro 'size' deve estar entre 1 e 100");
            }
            if (precoMin != null && precoMax != null && precoMin.compareTo(precoMax) > 0) {
                throw new BadRequestException("'precoMin' não pode ser maior que 'precoMax'");
            }

            List<ProdutoEntity> filtrados = PRODUTOS.stream()
                    .filter(p -> nome == null || nome.isBlank()
                            || p.getNome().toLowerCase().contains(nome.trim().toLowerCase()))
                    .filter(p -> precoMin == null || p.getPreco().compareTo(precoMin) >= 0)
                    .filter(p -> precoMax == null || p.getPreco().compareTo(precoMax) <= 0)
                    .sorted(Comparator.comparing(ProdutoEntity::getId))
                    .toList();

            long total = filtrados.size();
            int totalPages = (int) Math.ceil(total / (double) size);

            List<ProdutoEntity> conteudo = filtrados.stream()
                    .skip((long) page * size)
                    .limit(size)
                    .toList();

            return PageResponse.<ProdutoEntity>builder()
                    .content(conteudo)
                    .page(page)
                    .size(size)
                    .totalElements(total)
                    .totalPages(totalPages)
                    .build();
        }

        // GET /v1/produtos/{id}
        public synchronized ProdutoEntity buscarPorId(Integer id) throws NotFoundException {
            return PRODUTOS.stream()
                    .filter(p -> p.getId().equals(id))
                    .findAny()
                    .orElseThrow(() -> new NotFoundException("Produto com id " + id + " não encontrado"));
        }


        public synchronized ProdutoEntity createProduct(ProdutoDTO produtoDto){
            if (existeNome(produtoDto.getNome(), null)) {
                throw new ConflictException("Já existe um produto com o nome '" + produtoDto.getNome().trim() + "'");
            }

            Integer identificador = PRODUTOS.stream()
                    .mapToInt(ProdutoEntity::getId)
                    .max()
                    .orElse(0) + 1;

            ProdutoEntity novoProduto = ProdutoEntity.builder()
                    .id(identificador)
                    .nome(produtoDto.getNome().trim())
                    .preco(produtoDto.getPreco())
                    .quantidade(produtoDto.getQuantidade())
                    .build();

            PRODUTOS.add(novoProduto);

            return novoProduto;
        }

        // PUT -> substitui o produto inteiro
        public synchronized ProdutoEntity atualizarProduto(ProdutoDTO produtoDto, Integer id) throws NotFoundException{

            ProdutoEntity produto = buscarPorId(id);

            if (existeNome(produtoDto.getNome(), id)) {
                throw new ConflictException("Já existe um produto com o nome '" + produtoDto.getNome().trim() + "'");
            }

            produto.setNome(produtoDto.getNome().trim());
            produto.setPreco(produtoDto.getPreco());
            produto.setQuantidade(produtoDto.getQuantidade());

            return produto;

        }

        // PATCH -> altera só os campos enviados
        public synchronized ProdutoEntity atualizarParcialmente(ProdutoPatchDTO patch, Integer id) throws NotFoundException {
            if (patch.getNome() == null && patch.getPreco() == null && patch.getQuantidade() == null) {
                throw new BadRequestException("Informe ao menos um campo para atualizar: nome, preco ou quantidade");
            }

            ProdutoEntity produto = buscarPorId(id);

            if (patch.getNome() != null) {
                if (existeNome(patch.getNome(), id)) {
                    throw new ConflictException("Já existe um produto com o nome '" + patch.getNome().trim() + "'");
                }
                produto.setNome(patch.getNome().trim());
            }
            if (patch.getPreco() != null) {
                produto.setPreco(patch.getPreco());
            }
            if (patch.getQuantidade() != null) {
                produto.setQuantidade(patch.getQuantidade());
            }

            return produto;
        }

        public synchronized void removerProduto(Integer id){
            PRODUTOS.removeIf(p -> p.getId().equals(id));
        }

        private boolean existeNome(String nome, Integer ignorarId) {
            return PRODUTOS.stream()
                    .anyMatch(p -> p.getNome().equalsIgnoreCase(nome.trim())
                            && !p.getId().equals(ignorarId));
        }
}
