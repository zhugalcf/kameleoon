CREATE TABLE users (
    id bigint auto_increment PRIMARY KEY,
    username varchar(64) UNIQUE NOT NULL,
    password varchar(128) NOT NULL,
    email varchar(64) UNIQUE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE quotes (
    id bigint auto_increment PRIMARY KEY,
    content varchar UNIQUE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    score bigint,
    user_id bigint NOT NULL,
    version bigint NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE votes (
    id bigint auto_increment PRIMARY KEY,
    type varchar NOT NULL,
    user_id bigint NOT NULL,
    quote_id bigint NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (quote_id) REFERENCES quotes(id) ON DELETE CASCADE
);



