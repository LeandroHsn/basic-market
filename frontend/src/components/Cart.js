import React from "react";

export default function Cart({ cart, updateQuantity, checkout }) {
  if (cart.length === 0) return null;

  const subtotal = cart.reduce((sum, item) => sum + item.price * item.quantity, 0).toFixed(2);

  return (
    <aside className="cart-container" aria-label="Carrinho de compras">
      <h3>Carrinho</h3>
      {cart.map((item) => (
        <div key={item.id} className="cart-item">
          <span>{item.name} - {item.quantity} un</span>
          <button
            aria-label={`Diminuir quantidade de ${item.name}`}
            onClick={() => updateQuantity(item.id, item.quantity - 1)}
          >-</button>
          <button
            aria-label={`Aumentar quantidade de ${item.name}`}
            onClick={() => updateQuantity(item.id, item.quantity + 1)}
          >+</button>
        </div>
      ))}
      <p><strong>Subtotal:</strong> R$ {subtotal}</p>
      <button
        aria-label="Finalizar compra"
        onClick={checkout}
        disabled={cart.length === 0}
      >Finalizar</button>
    </aside>
  );
}