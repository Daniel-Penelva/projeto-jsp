alter table model_login add column usuario_id bigint not null default 1;

alter table model_login add constraint usuario_fk foreign key (usuario_id) references model_login (id);

update model_login set usuario_id = 13 where id > 13;