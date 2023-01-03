package dao;

public class Teste {

	public static void main(String[] args) {
		// Exercitando a lógica
		
		Double cadastros = 10.0;
		
		Double porpagina = 5.0;
		
		Double pagina = cadastros / porpagina;
		
		Double resto = pagina % 2;
		
		/* Acrescenta uma página se for maior que zero */
		if(resto > 0) {
			pagina ++;
		}
		
		System.out.println(pagina.intValue());
	}

}
