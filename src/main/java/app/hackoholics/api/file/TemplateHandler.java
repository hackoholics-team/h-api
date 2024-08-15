package app.hackoholics.api.file;

import static app.hackoholics.api.model.exception.ApiException.ExceptionType.SERVER_EXCEPTION;

import app.hackoholics.api.model.exception.ApiException;
import app.hackoholics.api.model.exception.BadRequestException;
import com.lowagie.text.DocumentException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Component
public class TemplateHandler<T> {

  public byte[] generatePdf(T object, String template) {
    ITextRenderer renderer = new ITextRenderer();
    loadStyle(renderer, object, template);
    renderer.layout();
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    try {
      renderer.createPDF(outputStream);
    } catch (DocumentException e) {
      throw new ApiException(SERVER_EXCEPTION, e.getMessage());
    }
    return outputStream.toByteArray();
  }

  private void loadStyle(ITextRenderer renderer, T object, String template) {
    renderer.setDocumentFromString(parseTemplateToString(object, template));
  }

  private String parseTemplateToString(T object, String template) {
    TemplateEngine templateEngine = configureTemplate();
    Context context = configureContext(object);
    return templateEngine.process(template, context);
  }

  private Context configureContext(T object) {
    Context context = new Context();
    context.setVariable("object", object);
    return context;
  }

  private TemplateEngine configureTemplate() {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix("/templates/");
    templateResolver.setSuffix(".html");
    templateResolver.setCharacterEncoding("UTF-8");
    templateResolver.setTemplateMode(TemplateMode.HTML);

    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    return templateEngine;
  }
}
