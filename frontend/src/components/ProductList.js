import React, { useState, useEffect } from "react";
import debounce from "lodash.debounce";
import { api } from "../api";

export default function ProductList({ addToCart, reloadSignal }) {
  const [products, setProducts] = useState([]);
  const [search, setSearch] = useState("");
  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  const fetchProducts = async (searchValue, pageValue) => {
    try {
      const res = await api.get(`/products`, {
        params: { search: searchValue, page: pageValue, size: 5 }
      });
      setProducts(res.data.content);
      setTotalPages(res.data.totalPages);
    } catch (err) {
      console.error(err);
    }
  };

  const debouncedSearch = debounce((value) => {
    setPage(0);
    fetchProducts(value, 0);
  }, 300);

  useEffect(() => {
    fetchProducts(search, page);
  }, [page]);

  useEffect(() => {
    fetchProducts(search, page);
  }, [reloadSignal]);

  return (
    <div className="product-list-container">
      <div className="search-bar">
        <input
          type="text"
          aria-label="Buscar produtos"
          placeholder="Buscar produtos..."
          value={search}
          onChange={(e) => {
            setSearch(e.target.value);
            debouncedSearch(e.target.value);
          }}
        />
      </div>

      <table className="product-table" aria-label="Lista de produtos">
        <thead>
          <tr>
            <th>Produto</th>
            <th>Preço</th>
            <th>Estoque</th>
            <th>Ação</th>
          </tr>
        </thead>
        <tbody>
          {products.map((p) => (
            <tr key={p.id}>
              <td>{p.name}</td>
              <td>R$ {p.price}</td>
              <td>{p.stock}</td>
              <td>
                <button
                  aria-label={`Adicionar ${p.name} ao carrinho`}
                  onClick={() => addToCart(p)}
                >+</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      <div className="pagination">
        <button
          aria-label="Página anterior"
          disabled={page === 0}
          onClick={() => setPage(page - 1)}
        >
          Anterior
        </button>
        <span aria-label={`Página ${page + 1} de ${totalPages}`}>{page + 1} / {totalPages}</span>
        <button
          aria-label="Próxima página"
          disabled={page + 1 >= totalPages}
          onClick={() => setPage(page + 1)}
        >
          Próximo
        </button>
      </div>
    </div>
  );
}