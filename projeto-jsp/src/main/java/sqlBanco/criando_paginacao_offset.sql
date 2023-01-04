/* O comando OFFSET indica o início da leitura, e o LIMIT o máximo de registros a serem lidos. 
 * Vai ser carregado de cinco em cinco até a quantidade de registros cadastrados.*/

select * from model_login where usuario_id = 1 order by nome offset 0 limit 5

select * from model_login where usuario_id = 1 order by nome offset 5 limit 5

select * from model_login where usuario_id = 1 order by nome offset 10 limit 5