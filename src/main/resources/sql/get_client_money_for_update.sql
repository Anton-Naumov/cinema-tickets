SELECT money_amount
FROM client_wallet
WHERE client_name = :clientName
FOR UPDATE
