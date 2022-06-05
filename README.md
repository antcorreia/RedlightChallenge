# RedlightChallenge
Challenge given on the last phase of the Redlight Summer Internship 2022 recruitment

To create all the necessary tables in the database run the following sql code as a query:


CREATE TABLE drink (
    drink_id     BIGINT,
    name     VARCHAR(512) UNIQUE NOT NULL,
    quantity     INTEGER NOT NULL,
    image_url VARCHAR(512),
    PRIMARY KEY(drink_id)
);

CREATE TABLE cocktail (
    cocktail_id BIGINT,
    name     VARCHAR(512) UNIQUE NOT NULL,
    PRIMARY KEY(cocktail_id)
);

CREATE TABLE drink_cocktail (
    drink_id     BIGINT,
    cocktail_id BIGINT,
    PRIMARY KEY(drink_id,cocktail_id)
);

ALTER TABLE drink_cocktail ADD CONSTRAINT drink_cocktail_fk1 FOREIGN KEY (drink_id) REFERENCES drink(drink_id);
ALTER TABLE drink_cocktail ADD CONSTRAINT drink_cocktail_fk2 FOREIGN KEY (cocktail_id) REFERENCES cocktail(cocktail_id);

CREATE SEQUENCE public.drink_sequence INCREMENT 1 START 1 MINVALUE 1;
ALTER TABLE ONLY drink ALTER COLUMN drink_id SET DEFAULT nextval('drink_sequence'::regclass);
CREATE SEQUENCE public.cocktail_sequence INCREMENT 1 START 1 MINVALUE 1;
ALTER TABLE ONLY cocktail ALTER COLUMN cocktail_id SET DEFAULT nextval('cocktail_sequence'::regclass);


The database should be configured with the username "postgres" and password "password" on the port 5432 with the name "redlight". Otherwise, the file application.properties (under /src/main/resources) should be modified with the custom database configuration
