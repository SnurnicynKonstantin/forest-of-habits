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
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL
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
    type VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
    limit_action_count INT
);

ALTER TABLE tree
    ADD CONSTRAINT fk_tree_forest
        FOREIGN KEY (forest_id) REFERENCES forest(id);

-- Action table
CREATE TABLE action (
    id SERIAL PRIMARY KEY,
    tree_id SERIAL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
    comment VARCHAR(255)
);

ALTER TABLE action
    ADD CONSTRAINT fk_action_tree
        FOREIGN KEY (tree_id) REFERENCES tree(id);