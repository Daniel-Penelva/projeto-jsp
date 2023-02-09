package util;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@SuppressWarnings({"rawtypes","unchecked"})
public class ReportUtil implements Serializable{

	private static final long serialVersionUID = 1L;

	public byte[] geraRelatorioPDF( List listaDados, String nomeRelatorio, ServletContext servletcontext) throws Exception{
		
		/* Cria a lista de dados que vem do sql da consulta feita */
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);
		
		/* Vamos passar o nome do package 'relatorio' 
		 * File.separator é para separar as barras (depende do sistema operacional) */
		String caminhoJasper = servletcontext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";
		
		/* Carregar o jasper passando os dados para ele */
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrbcds);
		
		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}

}
