INSERT INTO customers (name, surname, credit_limit, used_credit_limit) VALUES ('Ozan', 'Gumus', 1000000.0, 0.0);
INSERT INTO customers (name, surname, credit_limit, used_credit_limit) VALUES ('Furkan', 'Gumus', 2000000.0, 500000.0);
INSERT INTO customers (name, surname, credit_limit, used_credit_limit) VALUES ('Can', 'Gumus', 500000.0, 0.0);

INSERT INTO loans (customer_id, loan_amount, number_of_installment, create_date, is_paid) VALUES (1, 30000.0, 6, '2025-02-05', false);
INSERT INTO loans (customer_id, loan_amount, number_of_installment, create_date, is_paid) VALUES (2, 50000.0, 12, '2024-05-08', false);

INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (1, 5.5, 0.0, '2025-03-01', null, false);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (1, 5.5, 0.0, '2025-04-01', null, false);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (1, 5.5, 0.0, '2025-05-01', null, false);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (1, 5.5, 0.0, '2025-06-01', null, false);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (1, 5.5, 0.0, '2025-07-01', null, false);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (1, 5.5, 0.0, '2025-08-01', null, false);


INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (2, 5416.67, 0.0, '2024-06-01', null, true);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (2, 5.5, 5416.67, '2024-07-01', null, true);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (2, 5.5, 5416.67, '2024-08-01', null, true);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (2, 5.5, 5416.67, '2024-09-01', null, true);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (2, 5.5, 5416.67, '2024-10-01', null, true);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (2, 5.5, 5416.67, '2024-11-01', null, true);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (2, 5.5, 5416.67, '2024-12-01', null, true);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (2, 5.5, 5416.67, '2025-01-01', null, false);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (2, 5.5, 5416.67, '2025-02-01', null, false);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (2, 5.5, 5416.67, '2025-03-01', null, false);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (2, 5.5, 5416.67, '2025-04-01', null, false);
INSERT INTO installments (loan_id, amount, paid_amount, due_date, payment_date, is_paid) VALUES (2, 5.5, 5416.67, '2025-05-01', null, false);


