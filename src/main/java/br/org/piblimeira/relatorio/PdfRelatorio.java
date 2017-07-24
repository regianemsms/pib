package br.org.piblimeira.relatorio;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class PdfRelatorio {
    
	public InputStream gerarPdfRelatorio(String caminho, Map<String, Object> parametros, List<Object>lista) throws JRException, IOException {
		JasperReport report = JasperCompileManager.compileReport(retornarCaminhoRelatorio(caminho));
		JasperPrint print = JasperFillManager.fillReport(report,  parametros,new JRBeanCollectionDataSource(lista));
	
		// exportacao do relatorio para outro formato, no caso PDF
		return new ByteArrayInputStream(JasperExportManager.exportReportToPdf(print));
	}
	
	private String retornarCaminhoRelatorio(String relatorio) throws IOException{
	 return Paths.get("").toAbsolutePath().toString().concat(relatorio);
	}
	
}
