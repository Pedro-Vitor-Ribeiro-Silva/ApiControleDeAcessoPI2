-- Schema do banco Cloudflare D1 "projetointegrador" (SQLite)
CREATE TABLE IF NOT EXISTS pessoa (
    cpf  TEXT PRIMARY KEY,
    nome TEXT NOT NULL,
    foto TEXT
);

CREATE TABLE IF NOT EXISTS logs (
    id         INTEGER PRIMARY KEY AUTOINCREMENT,
    pessoa_cpf TEXT NOT NULL,
    tipo       TEXT NOT NULL,
    data_hora  DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pessoa_cpf) REFERENCES pessoa(cpf) ON DELETE CASCADE
);
