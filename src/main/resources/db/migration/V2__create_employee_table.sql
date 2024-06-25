CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    admission_date DATE NOT NULL,
    salary_value DECIMAL(10, 2) NOT NULL,
    status BOOLEAN NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id)
);
