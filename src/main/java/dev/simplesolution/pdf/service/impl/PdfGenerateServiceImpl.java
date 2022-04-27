package dev.simplesolution.pdf.service.impl;

import com.lowagie.text.DocumentException;

import dev.simplesolution.pdf.PdfGenerationError;
import dev.simplesolution.pdf.entity.Customer;
import dev.simplesolution.pdf.entity.QuoteItem;
import dev.simplesolution.pdf.service.PdfGenerateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PdfGenerateServiceImpl implements PdfGenerateService {
    private Logger logger = LoggerFactory.getLogger("Application-Info");

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${pdf.directory}")
    private String pdfDirectory;

    @Override
    public void generatePdfFile(String templateName, Map<String, Object> data, String pdfFileName) {
    	if(logger.isDebugEnabled()) {
    		Function<Map.Entry<String,Object>,String> convertData =  entry -> {
    			StringBuilder dataStr = new StringBuilder();
    			dataStr.append(entry.getKey());
    			dataStr.append(" ");
    			if(entry.getValue() instanceof Customer) {
    				Customer cs = (Customer)entry.getValue();
    				dataStr.append(cs.toString());
    			};
    			if(entry.getValue() instanceof ArrayList) {
    				@SuppressWarnings("unchecked")
					ArrayList<QuoteItem> quoteItems = (ArrayList<QuoteItem>)entry.getValue();
    				quoteItems.forEach(item -> dataStr.append(item.toString()));
    			};
    			return dataStr.toString();
    		};
    		String dataStr = data.entrySet().stream().map(convertData).collect(Collectors.joining("\n"));
    		logger.debug("Entering {} generatePdfFile with PARAMS:{},{},{}",this.getClass().getName(),templateName,dataStr,pdfFileName);
    	}	
        Context context = new Context();
        context.setVariables(data);
        String htmlContent = templateEngine.process(templateName, context);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pdfDirectory + pdfFileName);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(fileOutputStream, false);
            renderer.finishPDF();
        } catch (FileNotFoundException e) {
            throw new PdfGenerationError(e);
        } catch (DocumentException e) {
        	throw new PdfGenerationError(e);
        }
    }
}