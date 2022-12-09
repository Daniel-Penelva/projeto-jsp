CREATE TABLE public.model_login
(
  login character varying(200),
  senha character varying(200),
  CONSTRAINT login_unique UNIQUE (login)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE public.model_login
  OWNER TO postgres;

  
INSERT INTO public.model_login(login, senha)VALUES ('admin', 'admin');

select * from model_login;
