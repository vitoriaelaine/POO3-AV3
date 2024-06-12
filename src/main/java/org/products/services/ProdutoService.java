package org.products.services;

import org.products.entities.Fornecedor;
import org.products.entities.Produto;

import java.util.List;

public interface ProdutoService {
    void adicionarProduto(Produto produto);
    List<Produto> buscarProdutosPorCategoria(Long categoriaId);
}
