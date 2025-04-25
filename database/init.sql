CREATE TABLE IF NOT EXISTS logging_call_history (
    id BIGSERIAL PRIMARY KEY,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    endpoint TEXT NOT NULL,
    request TEXT,
    response TEXT,
    status_code INTEGER NOT NULL
    );