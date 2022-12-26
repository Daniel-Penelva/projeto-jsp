alter table model_login add column useradmin boolean not null default false;
select * from model_login where useradmin is false;

select * from model_login where upper(nome) like upper('%a%') and useradmin is false;