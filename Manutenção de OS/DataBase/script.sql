-- Geração de Modelo físico
-- Sql ANSI 2003 - SNIPER_MANUTENCAO


## ---------------- TABELAS ---------------##

CREATE TABLE IF NOT EXISTS funcionario (
  id INT PRIMARY KEY AUTO_INCREMENT,
  tipo INT NOT NULL,
  nome VARCHAR(155) NOT NULL,
  email VARCHAR(255) NOT NULL,
  user VARCHAR(155) NOT NULL,
    pwd VARCHAR(256) NOT NULL
);

CREATE TABLE IF NOT EXISTS telefone_funcionario (
    id_funcionario INT PRIMARY KEY,
    telefone VARCHAR(14),
    FOREIGN KEY(id_funcionario) REFERENCES funcionario (id)
);

CREATE TABLE IF NOT EXISTS cliente (
  garra INT PRIMARY KEY AUTO_INCREMENT,
  nome VARCHAR(155) NOT NULL,
  email VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS telefone_cliente (
    id_cliente INT PRIMARY KEY,
    telefone VARCHAR(14),
    FOREIGN KEY(id_cliente) REFERENCES cliente (garra)
);

CREATE TABLE IF NOT EXISTS endereco_cliente (
    id_cliente INT PRIMARY KEY,
    rua VARCHAR(155),
    numero VARCHAR(8),
    complemento VARCHAR(255),
    bairro VARCHAR(100),
    cidade VARCHAR(100),
    FOREIGN KEY (id_cliente) REFERENCES cliente(garra)
);

CREATE TABLE IF NOT EXISTS manutencao(
    protocolo INT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(1000) NOT NULL,
    data_criacao DATE NOT NULL,
    status BOOLEAN NOT NULL,
    id_cliente INT NOT NULL,
    id_funcionario INT,
    data_finalizacao DATE,
    data_prevista, DATE,
    servicos VARCHAR(1000) NOT NULL,
    FOREIGN KEY (id_cliente) REFERENCES cliente(garra),
    FOREIGN KEY (id_funcionario) REFERENCES funcionario(id)
);


##------------------ STORED PROCEDURES  -------------------##

##---------- LOGAR FUNCIONARIO ---------##
DELIMITER $$
DROP PROCEDURE IF EXISTS login $$
CREATE PROCEDURE login(user_in VARCHAR(155), pwd_in VARCHAR(256))
BEGIN
    SELECT * FROM funcionario WHERE user LIKE user_in AND pwd LIKE pwd_in;
END $$

##---------- ADICIONA UM NOVO FUNCIONARIO ---------##
DELIMITER $$
DROP PROCEDURE IF EXISTS add_usuario $$
CREATE PROCEDURE add_usuario(nome_in VARCHAR(155), email_in VARCHAR(255), user_in VARCHAR(155), pwd_in VARCHAR(256), tipo_in INT )
BEGIN
    INSERT INTO funcionario(nome,email, user, pwd, tipo)  
    VALUES (nome_in, email_in, user_in, pwd_in, tipo_in);
END $$

##--------------- LISTAR TODOS OS FUNCIONARIOS ----------------##
DELIMITER $$
DROP PROCEDURE IF EXISTS list_usuario $$
CREATE PROCEDURE list_usuario()
BEGIN
SELECT * FROM funcionario;
END $$

##--------------- LISTAR TODOS OS FUNCIONARIOS TÉCNICOS ----------------##
DELIMITER $$
DROP PROCEDURE IF EXISTS list_tecnico $$
CREATE PROCEDURE list_tecnico()
BEGIN
SELECT * FROM funcionario where tipo LIKE 2;
END $$

##---------------- DELETA UM FUNCIONARIO --------------##
DELIMITER $$
DROP PROCEDURE IF EXISTS delete_usuario $$
CREATE PROCEDURE delete_usuario(id_sel int)
BEGIN
DELETE FROM funcionario WHERE id LIKE (id_sel);
END $$

##---------------- ENCONTRA O TÉCNICO SELECIONADO -------##
DELIMITER $$
DROP PROCEDURE IF EXISTS search_employee $$
CREATE PROCEDURE search_employee(id_in INT)
BEGIN
    SELECT * FROM funcionario WHERE id LIKE (id_in);
END $$

##---------------- DELETA UM CLIENTE --------------##
DELIMITER $$
DROP PROCEDURE IF EXISTS delete_cliente $$
CREATE PROCEDURE delete_cliente(id_sel int)
BEGIN
DELETE FROM manutencao WHERE id_cliente LIKE (id_sel);
DELETE FROM endereco_cliente WHERE id_cliente LIKE (id_sel);
DELETE FROM telefone_cliente WHERE id_cliente LIKE (id_sel);
DELETE FROM cliente WHERE garra LIKE (id_sel);
END $$

##---------- CRIA UMA NOVA ORDEM DE SERVIÇO ------------##
DELIMITER $$
DROP PROCEDURE IF EXISTS create_os $$
CREATE PROCEDURE create_os(descricao_in VARCHAR(1000), data_abertura DATE, idc_in INT, status BOOLEAN, data_prev DATE, nome_in VARCHAR(155))
BEGIN
    INSERT INTO manutencao(descricao, data_criacao, id_cliente, status, data_prevista, nome_cliente) 
    VALUES (descricao_in, data_abertura, idc_in, status, data_prev, nome_in);
END $$

##---------- LISTA TODAS AS ORDENS DE SERVIÇO ------------##
DELIMITER $$
DROP PROCEDURE IF EXISTS list_os $$
CREATE PROCEDURE list_os()
    SELECT * FROM manutencao WHERE status LIKE false AND id_funcionario is NULL;
END $$

##---------- LISTA TODAS AS ORDENS DE SERVIÇO ------------##
DELIMITER $$
DROP PROCEDURE IF EXISTS list_os_aberta $$
CREATE PROCEDURE list_os_aberta()
    SELECT * FROM manutencao WHERE status LIKE false AND manutencao.id_funcionario LIKE (NULL);
END $$

##---------- LISTA AS ORDENS DE SERVIÇO DO TÉCNICO LOGADO ------------##
DELIMITER $$
DROP PROCEDURE IF EXISTS list_os_tecnica $$
CREATE PROCEDURE list_os_tecnica(id_in int)
    SELECT * FROM manutencao WHERE status LIKE false AND manutencao.id_funcionario LIKE (id_in);
END $$

##---------- LISTA AS ORDENS DE SERVIÇO ENCERRADAS ------------##
DELIMITER $$
DROP PROCEDURE IF EXISTS list_os_ended $$
CREATE PROCEDURE list_os_ended()
    SELECT * FROM manutencao WHERE status LIKE True;
END $$

##----------------- SCRIPT DE POVOAMENTO DA BASE DE DADOS -------------------##

INSERT INTO cliente(garra, nome, email) VALUES 
(1, "Luis Inácio Lula da Silva", "departamento.policiafederalpr@email.gov.com"), 
(2, "Dilmãe Linda Presidenta", "dilminha@email.menostop.com"), 
(3, "Jair Bolsonabo Mito da Silva", "emelhor_jairme.aprovando@email.topperson.com"),
(4, "Barack Obama Negrasso", "seriaeu_opiorpresidenteeua@email.com"),
(5, "Jacinto Pinto Aquin Orego", "jacintoleiteaquinocopo@email.com"),
(6, "Isadora Pinto", "isadorapinto@email.com"),
(7, "Donaldo Trumpe", "realdonaldotrump@email.com"),
(8, "Michel Temerson", "foratemer@email.com"),
(9, "Ciro Comes", "cirocraciadocangaco@email.com"),
(10, "Marina Silva", "voltodaquiquatroanos@email.com");

INSERT INTO endereco_cliente(rua, numero, complemento, bairro, cidade, id_cliente) VALUES
("Av. Curitiba", "666", "Sede da Policia Federal", "", "Curitiba", 1),
("Ninguem sabe", "XXX", "Devia ser na PF também", "", "Brasília", 2),
("Av. do Planalto", "666", "Palacio do Planalto", "Planalto", "Brasília", 3),
("Av. antiga casa branca", "", "Perto da área dos bons presidentes", "", "Washington D.C", 4),
("Av. to precisando de nota", "meaprova", "Me aprova pfvr", "To falando sério", "Serio pssor!", 5),
("R. Plutão ja foi planeta", "666", "Pink floyd anti-fascista", "The beatles", "Roger Aguas", 6),
("Av. da White Casa", "999", "Casa bege, a unica da rua", "n sei", "Washington", 7),
("Av. do Planalto", "666", "Casarão grande desnecessário", "Planalto", "Brasília", 8),
("Deserto do Cangaço", "000", "Antiga residência do dr. Lampião", "Cangaço", "Sertão", 9),
("Av. mata atlantica", "111", "No meio da Amazônia", "Matagal", "Maria Juana", 10);

INSERT INTO telefone_cliente(telefone, id_cliente) VALUES
("(41)1234-5678", 1),
("(41)98765-4321", 1),
("(55)2173-1237",2),
("(21)3332-2323", 3),
("(21)99878-9889",3),
("(44)98888-2222", 4),
("(33)3434-9823", 5),
("(31)98734-2389", 5),
("(33)3333-2222", 6),
("(34)98888-8888", 6),
("(55)3224-2222",7),
("(55)98888-9000", 7),
("(12)3422-8777", 8),
("(23)98988-8989", 8),
("(43)3344-7888", 9),
("(46)3877-8888", 10);
