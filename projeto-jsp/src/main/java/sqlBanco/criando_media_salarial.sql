select avg(rendamensal) as media_salarial, perfil from model_login where usuario_id = ? group by perfil