CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE company (
  id BIGINT NOT NULL,
   name VARCHAR(20),
   description VARCHAR(255),
   founded INT NOT NULL,
   website VARCHAR(255),
   is_work_from_home BOOLEAN NOT NULL,
   CONSTRAINT pk_company PRIMARY KEY (id)
);

CREATE TABLE vacancy (
  id BIGINT NOT NULL,
   title VARCHAR(255),
   description VARCHAR(255),
   is_open BOOLEAN NOT NULL,
   company_id BIGINT NOT NULL,
   CONSTRAINT pk_vacancy PRIMARY KEY (id)
);

ALTER TABLE company ADD CONSTRAINT uc_company_name UNIQUE (name);

ALTER TABLE vacancy ADD CONSTRAINT FK_VACANCY_ON_COMPANY FOREIGN KEY (company_id) REFERENCES company (id);