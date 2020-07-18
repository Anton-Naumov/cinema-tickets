CREATE TABLE client_wallet (
      id SERIAL PRIMARY KEY,
      client_name varchar(200) REFERENCES oauth2_authorized_client(principal_name),
      money_amount DECIMAL(10, 2)
);

CREATE TRIGGER create_client_wallet
AFTER INSERT
ON oauth2_authorized_client FOR EACH ROW
BEGIN
    INSERT INTO client_wallet(client_name, money_amount)
    VALUES(NEW.principal_name, 0);
END
