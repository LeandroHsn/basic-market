import React, { useState, useEffect } from "react";
import debounce from "lodash.debounce";
import { api } from "../api";

export default function ProductList({ addToCart }) {
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

  return (
    <div>
      <input
        aria-label="Buscar produtos"
        placeholder="Buscar..."
        value={search}
        onChange={(e) => {
          setSearch(e.target.value);
          debouncedSearch(e.target.value);
        }}
      />

      <ul>
        {products.map((p) => (
          <li key={p.id}>
            {p.name} - R$ {p.price} - Estoque: {p.stock}
            <button onClick={() => addToCart(p)}>+</button>
          </li>
        ))}
      </ul>

      <div>
        <button disabled={page === 0} onClick={() => setPage(page - 1)}>Anterior</button>
        <span>{page + 1} / {totalPages}</span>
        <button disabled={page + 1 >= totalPages} onClick={() => setPage(page + 1)}>Próximo</button>
      </div>
    </div>
  );
}
