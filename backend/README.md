Top 3 Produtos Mais Vendidos

SELECT p.id, p.name, SUM(oi.quantity) AS total_vendido
FROM order_items oi
JOIN products p ON p.id = oi.product_id
GROUP BY p.id, p.name
ORDER BY total_vendido DESC
LIMIT 3;

EXPLAIN da Query

EXPLAIN SELECT p.id, p.name, SUM(oi.quantity) AS total_vendido
FROM order_items oi
JOIN products p ON p.id = oi.product_id
GROUP BY p.id, p.name
ORDER BY total_vendido DESC
LIMIT 3;
