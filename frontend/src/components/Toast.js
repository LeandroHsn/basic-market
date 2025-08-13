import React, { useEffect } from "react";

export default function Toast({ message, onClose, duration = 6000 }) {
  useEffect(() => {
    if (!message) return;
    const timer = setTimeout(onClose, duration);
    return () => clearTimeout(timer);
  }, [message, onClose, duration]);

  if (!message) return null;

  return (
    <div className="toast" role="alert" aria-live="assertive">
      {message}
    </div>
  );
}