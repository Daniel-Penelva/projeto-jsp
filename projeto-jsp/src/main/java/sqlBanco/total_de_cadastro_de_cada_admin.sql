/* Para saber o total de quantos cadastros o usuario 1 fez (no caso, usuario 1 é o admin) */ 
select count(1) from model_login where usuario_id = 1;