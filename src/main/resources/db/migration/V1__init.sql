CREATE EXTENSION IF NOT EXISTS ltree;

CREATE TABLE edge
(
    from_id INTEGER NOT NULL,
    to_id   INTEGER NOT NULL,
    path    LTREE,
    PRIMARY KEY (from_id, to_id)
);

CREATE INDEX edge_from_id_idx ON edge (to_id);
CREATE INDEX edge_path_idx ON edge USING GIST (path);