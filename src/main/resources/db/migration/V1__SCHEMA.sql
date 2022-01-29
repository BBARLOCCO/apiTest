
CREATE TABLE customer(
	id uuid NOT NULL primary key,
	name varchar not null,
	last_name varchar not null,
	photo varchar,
	created_at TIMESTAMP  NOT NULL,
	updated_at TIMESTAMP  NOT NULL,
	updated_by varchar NOT NULL,
	created_by varchar NOT NULL
);