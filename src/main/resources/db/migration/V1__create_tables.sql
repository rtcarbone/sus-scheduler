-- habilita extensão para UUID
CREATE
EXTENSION IF NOT EXISTS "pgcrypto";

-- ==============================
-- PACIENTES
-- ==============================
CREATE TABLE pacientes
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome            VARCHAR(120) NOT NULL,
    cpf             VARCHAR(14)  NOT NULL UNIQUE,
    data_nascimento DATE         NOT NULL
);


-- ==============================
-- MÉDICOS
-- ==============================
CREATE TABLE medicos
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome          VARCHAR(120) NOT NULL,
    especialidade VARCHAR(40)  NOT NULL,
    unidade       VARCHAR(120) NOT NULL
);

-- índice para busca por especialidade
CREATE INDEX idx_medicos_especialidade ON medicos (especialidade);

-- ==============================
-- CONSULTAS
-- ==============================
CREATE TABLE consultas
(
    id            UUID PRIMARY KEY                  DEFAULT gen_random_uuid(),
    paciente_id   UUID                     NOT NULL REFERENCES pacientes (id) ON DELETE CASCADE,
    medico_id     UUID                     NOT NULL REFERENCES medicos (id) ON DELETE CASCADE,
    especialidade VARCHAR(40)              NOT NULL,
    prioridade    VARCHAR(16)              NOT NULL,
    status        VARCHAR(16)              NOT NULL,
    data_prevista TIMESTAMP WITH TIME ZONE NOT NULL,
    criado_em     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);