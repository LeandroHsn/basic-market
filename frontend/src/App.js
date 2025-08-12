import React, { useState } from "react";
import ProductList from "./components/ProductList";
import Cart from "./components/Cart";
import { api } from "./api";

function App() {
  const [cart, setCart] = useState([]);
  const [message, setMessage] = useState("");

  const addToCart = (product) => {
    setCart((prev) => {
      const exists = prev.find((p) => p.id === product.id);
      if (exists) {
        return prev.map((p) =>
          p.id === product.id ? { ...p, quantity: p.quantity + 1 } : p
        );
      }
      return [...prev, { ...product, quantity: 1 }];
    });
  };

  const updateQuantity = (id, quantity) => {
    setCart((prev) =>
      prev
        .map((item) =>
          item.id === id ? { ...item, quantity: Math.max(quantity, 1) } : item
        )
        .filter((item) => item.quantity > 0)
    );
  };

  const checkout = async () => {
    try {
      const res = await api.post("/orders", {
        items: cart.map((item) => ({
          productId: item.id,
          quantity: item.quantity
        }))
      });
      setMessage(`Pedido criado com sucesso! ID: ${res.data.id}`);
      setCart([]);
    } catch (err) {
      if (err.response?.status === 409) {
        setMessage(`Itens indisponíveis: ${JSON.stringify(err.response.data)}`);
      } else {
        setMessage("Erro ao finalizar pedido.");
      }
    }
  };

  return (
    <div style={{ display: "flex", gap: "20px" }}>
      <div>
        <ProductList addToCart={addToCart} />
        {message && <p>{message}</p>}
      </div>
      <Cart cart={cart} updateQuantity={updateQuantity} checkout={checkout} />
    </div>
  );
}

export default App;
