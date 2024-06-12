package org.products.services;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.products.entities.Categoria;
import org.products.entities.Fornecedor;
import org.products.entities.Produto;
import org.products.util.HibernateUtil;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class ProdutoServiceImpl implements ProdutoService{
    @Override
    public void adicionarProduto(Produto produto) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Categoria categoriaExistente = buscarCategoria(produto.getCategoria(), session);
            if (categoriaExistente != null) {
                produto.setCategoria(categoriaExistente);
            } else {
                session.save(produto.getCategoria());
            }
            session.save(produto);

            for (Fornecedor fornecedor : produto.getFornecedores()) {
                Fornecedor fornecedorExistente = buscarFornecedor(fornecedor, session);
                if (fornecedorExistente != null) {
                    fornecedor.setId(fornecedorExistente.getId());
                } else {
                    session.saveOrUpdate(fornecedor);
                }
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public List<Produto> buscarProdutosPorCategoria(Long categoriaId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produto> produtos = null;

        try {
            String hql = "FROM Produto WHERE categoria.id = :categoriaId";
            Query<Produto> query = session.createQuery(hql, Produto.class);
            query.setParameter("categoriaId", categoriaId);
            produtos = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        return produtos;
    }

    public Categoria buscarCategoria(Categoria categoria, Session session) {
        Query<Categoria> query = session.createQuery("FROM Categoria WHERE nome = :nome", Categoria.class);
        query.setParameter("nome", categoria.getNome());
        return query.uniqueResult();
    }

    public Fornecedor buscarFornecedor(Fornecedor fornecedor, Session session) {
        Query<Fornecedor> query = session.createQuery("FROM Fornecedor WHERE nome = :nome", Fornecedor.class);
        query.setParameter("nome", fornecedor.getNome());
        return query.uniqueResult();
    }
}
