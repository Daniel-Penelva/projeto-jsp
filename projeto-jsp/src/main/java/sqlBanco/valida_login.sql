/* Retorna um boolean, no caso, true (t) para exista um login e false (f) caso NÃO exista um login */

select count(1) > 0 from model_login where upper(login) = upper('admin');
select count(1) > 0 from model_login where upper(login) = upper('daniel-penelva');
select count(1) > 0 from model_login where upper(login) = upper('admin1');

select count(1) > 0 as existe from model_login where upper(login) = upper('admin');