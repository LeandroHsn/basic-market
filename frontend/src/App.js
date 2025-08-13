import React, { useState } from "react";
import ProductList from "./components/ProductList";
import Cart from "./components/Cart";
import Toast from "./components/Toast";
import { api } from "./api";
import './App.css';

function App() {
  const [cart, setCart] = useState([]);
  const [message, setMessage] = useState("");
  const [reloadSignal, setReloadSignal] = useState(0);

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
          item.id === id ? { ...item, quantity } : item
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
      console.log(res.data);
      setMessage(`Pedido criado com sucesso!`);
      setCart([]);
      setReloadSignal((s) => s + 1); 
    } catch (err) {
      if (err.response?.status === 409) {
        setMessage(`Itens indisponíveis: ${JSON.stringify(err.response.data)}`);
      } else {
        setMessage("Erro ao finalizar pedido.");
      }
    }
  };

  return (
    <div className="App">
      <div className="main-content">
        <div style={{ flex: 2 }}>
          <ProductList addToCart={addToCart} reloadSignal={reloadSignal} />
        </div>
        <Cart cart={cart} updateQuantity={updateQuantity} checkout={checkout} />
      </div>
      <Toast message={message} onClose={() => setMessage("")} />
    </div>
  );
}

export default App;