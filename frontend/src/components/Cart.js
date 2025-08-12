import React from "react";

export default function Cart({ cart, updateQuantity, checkout }) {
  const subtotal = cart.reduce((sum, item) => sum + item.price * item.quantity, 0).toFixed(2);

  return (
    <aside style={{ border: "1px solid #ccc", padding: "10px", width: "300px" }}>
      <h3>Carrinho</h3>
      {cart.map((item) => (
        <div key={item.id}>
          {item.name} - {item.quantity} un
          <button aria-label="Diminuir quantidade" onClick={() => updateQuantity(item.id, item.quantity - 1)}>-</button>
          <button aria-label="Aumentar quantidade" onClick={() => updateQuantity(item.id, item.quantity + 1)}>+</button>
        </div>
      ))}
      <p>Subtotal: R$ {subtotal}</p>
      <button onClick={checkout}>Finalizar</button>
    </aside>
  );
}
