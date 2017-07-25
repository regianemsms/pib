package br.org.piblimeira.relatorio;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PdfRelatorio {
	private static final Logger LOGGER = Logger.getLogger(PdfRelatorio.class);
	
	public InputStream gerarPdfRelatorio(String caminho, Map<String, Object> parametros, List<Object>lista)  {
		try {
			JasperReport report;
			JasperPrint print;
			report = JasperCompileManager.compileReport(retornarCaminhoRelatorio(caminho));
			print = JasperFillManager.fillReport(report,  parametros,new JRBeanCollectionDataSource(lista));
			return new ByteArrayInputStream(JasperExportManager.exportReportToPdf(print));
		} catch (JRException | IOException e) {
			LOGGER.error("Erro ao gerar PDF" + e.getMessage());
			return null;
		}
	
	}
	
	private String retornarCaminhoRelatorio(String relatorio) throws IOException{
		return Paths.get("").toAbsolutePath().toString().concat(relatorio);
	}
	
}
