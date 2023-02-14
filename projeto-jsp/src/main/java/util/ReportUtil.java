package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.util.ByteArrayBuilder;

import jakarta.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ReportUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, ServletContext servletcontext)
			throws Exception {

		/* Cria a lista de dados que vem do sql da consulta feita */
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);

		/*
		 * Vamos passar o nome do package 'relatorio' File.separator é para separar as
		 * barras (depende do sistema operacional)
		 */
		String caminhoJasper = servletcontext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

		/* Carregar o jasper passando os dados para ele */
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrbcds);

		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}

	
	/* Gerar Sub relatorio de telefone */
	public byte[] geraRelatorioPDF(List listaDados, String nomeRelatorio, HashMap<String, Object> params,
			ServletContext servletcontext) throws Exception {

		/* Cria a lista de dados que vem do sql da consulta feita */
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);

		/*
		 * Vamos passar o nome do package 'relatorio' File.separator é para separar as
		 * barras (depende do sistema operacional)
		 */
		String caminhoJasper = servletcontext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

		/* Carregar o jasper passando os dados para ele */
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);

		return JasperExportManager.exportReportToPdf(impressoraJasper);
	}
	
	
	/* Gerar relatorio em excel */
	public byte[] geraRelatorioExcel(List listaDados, String nomeRelatorio, HashMap<String, Object> params,
			ServletContext servletcontext) throws Exception {

		/* Cria a lista de dados que vem do sql da consulta feita */
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listaDados);

		/*
		 * Vamos passar o nome do package 'relatorio' File.separator é para separar as
		 * barras (depende do sistema operacional)
		 */
		String caminhoJasper = servletcontext.getRealPath("relatorio") + File.separator + nomeRelatorio + ".jasper";

		/* Carregar o jasper passando os dados para ele */
		JasperPrint impressoraJasper = JasperFillManager.fillReport(caminhoJasper, params, jrbcds);
		
		/* Para gerar o excel */
		
		JRExporter exporter = new JRXlsExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,baos);
		exporter.exportReport();

		return baos.toByteArray();
	}

}
