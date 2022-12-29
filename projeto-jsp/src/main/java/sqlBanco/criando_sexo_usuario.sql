ALTER TABLE model_login ADD COLUMN sexo character varying(200);

/* Corrigir os valores para os dados já persistidos no BD*/

UPDATE model_login set perfil = 'AUXILIAR' WHERE perfil is null;
UPDATE model_login set sexo = 'MASCULINO' WHERE sexo is null;