-- Account table
CREATE TABLE account (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    authenticated BOOLEAN DEFAULT false
);

ALTER TABLE account ADD CONSTRAINT unique_account_email UNIQUE (email);

-- Forest table
CREATE TABLE forest (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    account_id SERIAL NOT NULL,
    created_at TIMESTAMP DEFAULT now()
);

ALTER TABLE forest
    ADD CONSTRAINT fk_forest_account
        FOREIGN KEY (account_id) REFERENCES account(id);

-- Tree table
CREATE TABLE tree (
    id SERIAL PRIMARY KEY,
    forest_id SERIAL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255),
    tree_type VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT now(),
    top_limit INT,
    period VARCHAR(100)
);

ALTER TABLE tree
    ADD CONSTRAINT fk_tree_forest
        FOREIGN KEY (forest_id) REFERENCES forest(id);

-- Log table
CREATE TABLE incrementation_log (
    id SERIAL PRIMARY KEY,
    tree_id SERIAL,
    date TIMESTAMP DEFAULT now(),
    comment VARCHAR(255)
);

ALTER TABLE incrementation_log
    ADD CONSTRAINT fk_inclog_tree
        FOREIGN KEY (tree_id) REFERENCES tree(id);