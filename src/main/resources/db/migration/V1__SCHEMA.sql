CREATE TABLE account_role(
	id uuid NOT NULL PRIMARY KEY,
	name varchar not null,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL
);

CREATE TABLE account(
	id uuid NOT NULL primary key,
	email VARCHAR NOT NULL,
	name VARCHAR NOT NULL,
	last_name VARCHAR NOT NULL,
	password VARCHAR NOT NULL,
	token VARCHAR,
	account_role_id uuid not null,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL
);

ALTER TABLE account
ADD CONSTRAINT fk_actor_role
FOREIGN KEY (account_role_id) REFERENCES account_role(id);

CREATE TABLE customer(
	id uuid NOT NULL primary key,
	name varchar not null,
	last_name varchar not null,
	photo varchar,
	created_at TIMESTAMP  NOT NULL,
	updated_at TIMESTAMP  NOT NULL,
	updated_by uuid NOT NULL,
	created_by uuid NOT NULL
);

ALTER TABLE customer
ADD CONSTRAINT fk_customer_updated_by
FOREIGN KEY (updated_by) REFERENCES account(id);
ALTER TABLE customer
ADD CONSTRAINT fk_customer_created_by
FOREIGN KEY (created_by) REFERENCES account(id);

CREATE TABLE privilege(
	id uuid NOT NULL PRIMARY KEY,
	name varchar not null,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL
);

CREATE TABLE account_role_privilege(
	id uuid NOT NULL PRIMARY KEY,
	privilege_id UUID NOT NULL,
	account_role_id UUID NOT NULL,
	created_at TIMESTAMP NOT NULL,
	updated_at TIMESTAMP NOT NULL
);

ALTER TABLE account_role_privilege
ADD CONSTRAINT fk_account_role_privilege_role
FOREIGN KEY (account_role_id) REFERENCES account_role(id);

ALTER TABLE account_role_privilege
ADD CONSTRAINT fk_account_role_privilege_privilege
FOREIGN KEY (privilege_id) REFERENCES privilege(id);

INSERT INTO account_role values('cf2e8ffc-0aa6-4bc4-b414-2b99038bc8ad','User', current_timestamp, current_timestamp);
INSERT INTO account_role values('cf2e8ffc-0aa6-4bc4-b414-2b99038bc8ae','Admin', current_timestamp, current_timestamp);

INSERT INTO privilege VALUES('dfddce8a-719d-11ec-90d6-0242ac120003','ADMIN', current_timestamp, current_timestamp);
INSERT INTO account_role_privilege VALUES('dfddd132-719d-11ec-90d6-0242ac120003', 'dfddce8a-719d-11ec-90d6-0242ac120003', 'cf2e8ffc-0aa6-4bc4-b414-2b99038bc8ae', current_timestamp, current_timestamp);