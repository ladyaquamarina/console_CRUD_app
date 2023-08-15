CREATE TABLE writer
(
    writer_id serial primary key,
    first_name character varying(50),
    last_name character varying(50)
);

CREATE TABLE post
(
    post_id serial primary key,
    content text,
    created timestamp DEFAULT CURRENT_TIMESTAMP,
    updated timestamp DEFAULT CURRENT_TIMESTAMP,
    status character varying(20) NOT NULL DEFAULT 'active',
    writer_id integer,
    FOREIGN KEY (writer_id) REFERENCES writer (writer_id)
);

CREATE TABLE label
(
    label_id serial primary key,
    name character varying(50)
);

CREATE TABLE post_label
(
    post_id integer,
    label_id integer,
    FOREIGN KEY (label_id) REFERENCES label (label_id),
    FOREIGN KEY (post_id) REFERENCES post (post_id)
)