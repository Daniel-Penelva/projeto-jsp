CREATE SEQUENCE public.versionadorbanco_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;


  CREATE TABLE public.versionadorbanco
(
  id integer NOT NULL DEFAULT nextval('versionadorbanco_seq'::regclass),
  arquivo_sql character varying(50) not null,
  constraint id_pk primary key (id)
 )