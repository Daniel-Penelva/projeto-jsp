alter table model_login add id serial primary key;

alter table model_login add nome character varying(300);

alter table model_login add email character varying(300);

update model_login set nome='';
alter table model_login alter column nome set not null;

update model_login set email='';
alter table model_login alter column email set not null;

alter table model_login alter column id set not null;
alter table model_login alter column login set not null;
alter table model_login alter column senha set not null;

/* ou */

CREATE TABLE public.model_login
(
  login character varying(200) NOT NULL,
  senha character varying(200) NOT NULL,
  id integer NOT NULL DEFAULT nextval('model_login_id_seq'::regclass),
  nome character varying(300) NOT NULL,
  email character varying(300) NOT NULL,
  CONSTRAINT model_login_pkey PRIMARY KEY (id),
  CONSTRAINT login_unique UNIQUE (login)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.model_login
  OWNER TO postgres;