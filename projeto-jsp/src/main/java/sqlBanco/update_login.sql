/* ex: */

UPDATE model_login SET login='carlos-mendonça' WHERE id=17;

UPDATE model_login SET login=?, senha=?, nome=?, email=? WHERE id= " + modelLogin.getId()
