/* O comando OFFSET indica o início da leitura, e o LIMIT o máximo de registros a serem lidos. */

select * from model_login where usuario_id = 1 order by nome offset 0 limit 5