CREATE SEQUENCE public.telefone_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

create table telefone(
 id integer not null DEFAULT nextval('telefone_seq'::regclass),
 numero character varying(50) not null, 
 usuario_pai_id bigint not null,   
 usuario_cad_id bigint not null,

 constraint telefone_pkay primary key(id),
 constraint usuario_pai_fk foreign key(usuario_pai_id) references model_login(id),
 constraint usuario_cad_fk foreign key(usuario_cad_id) references model_login(id)
)