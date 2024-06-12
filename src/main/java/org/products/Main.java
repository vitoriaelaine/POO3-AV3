package org.products;

import org.products.entities.Categoria;
import org.products.entities.Produto;
import org.products.entities.Fornecedor;
import org.products.services.*;

import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ProdutoService produtoService = new ProdutoServiceImpl();

        Categoria categoria1 = new Categoria();
        categoria1.setNome("Vestuario");

        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setNome("Vans");
        Fornecedor fornecedor2 = new Fornecedor();
        fornecedor2.setNome("Maresia");
        Fornecedor fornecedor3 = new Fornecedor();
        fornecedor3.setNome("Riachuelo");

        List<Fornecedor> fornecedores = new ArrayList<>();
        fornecedores.add(fornecedor1);
        fornecedores.add(fornecedor2);
        fornecedores.add(fornecedor3);

        Produto produto1 = new Produto();
        produto1.setNome("Camisa");
        produto1.setPreco(340.00);
        produto1.setCategoria(categoria1);
        produto1.setFornecedores(fornecedores);

        produtoService.adicionarProduto(produto1);

        System.out.println("Fornecedores do produto " + produto1.getNome() + ":");
        List<Fornecedor> fornecedoresProduto = produto1.getFornecedores();
        for (Fornecedor fornecedor: fornecedoresProduto) {
            System.out.println(" - " + fornecedor.getNome());
        }

        List<Produto> produtosPorCategoria = produtoService.buscarProdutosPorCategoria(produto1.getCategoria().getId());
        System.out.println("Produtos na categoria " + produto1.getCategoria().getNome() + ":");
        for (Produto produto: produtosPorCategoria) {
            System.out.println("Produto: " + produto.getNome());
        }
    }
}