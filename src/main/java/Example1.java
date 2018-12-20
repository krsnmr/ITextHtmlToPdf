import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Example1 {

    /**
     * Из примера на сайте developers.itextpdf.com ParseHtml4
     * HTML Images - Some examples converting HTML to PDF involving images
     * https://developers.itextpdf.com/ru/examples/xml-worker-itext5/html-images
     */

    private static final String DEST = "C:\\temp\\pdf\\test-15.pdf";
    public static void main(String[] args) throws IOException, DocumentException {
        System.out.println("123");
        // html c картинкой
        String html = "<html><head><title>Test PDF</title></head><body><div>" +
                "<img src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAACAAAAAgCAIAAAD8GO2jAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAAYdEVYdFNvZnR3YXJlAHBhaW50Lm5ldCA0LjEuNBNAaMQAAAQESURBVEhLtZb/U5RFHMdNQ04hQS3TqMxkggO8O+D4JjCcBEJYk6KeowKhU01YUKaYZYVk2kzaiIn2hUpTGam5ophpssw0ppgma8zB4Z59vt8+zz3+G723Jehs5mQbb+b9w+7dfvb17Gc/X3baF6FQQsUA1y0rQYoBRK9eixw5obd0ahtfMPf1Rv+46lA6sfT/aRIQHbmsbeiQl9aQO0uk+SVkcUCtbrEv/Oxo+r8NRDUJMDq6yb2V0nS3NC2L6bZs6Y4CY1tX9Lcr9sVfIsdOmV09U9K+o7TvrD1y+UaAnFUnuTzju3PNcMvZ9fb54cihPjXQhMNNSVkrlbKg3vaaNXQ+BsB2n/j8Cc3y2t9cMJ7bS+4qDaf4SGatUtQYR7K/kWTVkfQi+Flv3RULuGHrf4QP4QCSXW/secfqH4wjembQfLNXXdEszcwj91SKAdS6LdHf/8TK+EJQwKXcGZjeegAU6T0lJeXCFmMBgOJ9LNLzCe48vqzBc7hhFoSiAJJWoHgeVQJNN1FZUF5SzW3FAOyj5hTAXilZJ6Xm4y+yqFwpXqvkr+YOkRdXyaXr5YdqpWQ2hcQA4dle2NO+Afp+P9t0bpG2po2+d8Y8cBzhLyXn6c+8Gvmg33j+DTmzhtsKnmBBmda6y9ENxIkW7CDuR4yuHkfV7Esjav1WklZIB4YcRbPOfq2ubOW2ggCXRynfYP8wbH93SfE3Sik+vXmH/e2P9GRIfmAF4tJ4+aB97qLZfUTOrOW2ggBMk3JIup+kF0q357Bpci4bp/t51Idneclcv5TqQ43htoKAmXmIosjx05F3T8rZKFzL4BmUd3NvD7l7OUJAb96Jv/Sn9yCHua0YAOUFfcIZk5xrYXV1G+qSsfvt6OgYPIZSiNJLP/7MGR2jJz5XqzZzW0FAWiGSGbUPvyDepYxKfetua+h7+tGA4ntcmu3FUTBFLWLTv23FAHA0WVSBTdHyyIIy+ETObcA9a+vaeZ1HNdW2vKSg0qX4uK0gYIabLFyuNjzJghJdD/VyaQ3OhB3Dqfnh6W45bxX+VYrW8jSExAAEmbW+HSuvU6qteopkVBqvHHJM0/5pBOkddnno6S+RB/TTkFqxkdsKAtBDmnYwgGVpa7bJDz5sdh3G2B7+ValuwSFYomk67f8KzZzbigEQ++S+Kv2JTn3TiywQXcsQtfqm7Vrjs7h/OFApDWotneg26Gi4FTmnIRZws5bJpkm57CjzS8YTzeXBmMwrnljMls0pwJgsLDd2vhUDiNf0D36I0I7p7HHkrlcDm43t+/HkiQEY7d0ko2LyEP99trx+eEo6cAy3Hb0yij1jAOzhFWzH1TEPzCsm9wfgzVv58IIS+3RMkMYBCVQo9Bf+ZiLTYfUSswAAAABJRU5ErkJggg==\" />"+
                "</div><div>Hello world</div></body></html>";
        new Example1().createPdf(DEST, html);
        System.out.println(DEST);
    }

    private void createPdf(String filePath, String htmlContBase64Image) throws IOException, DocumentException {

        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
        // step 3
        document.open();
        // step 4

        // CSS
        CSSResolver cssResolver =
                XMLWorkerHelper.getInstance().getDefaultCssResolver(true);

        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        htmlContext.setImageProvider(new Base64ImageProvider());

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new ByteArrayInputStream(htmlContBase64Image.getBytes()));

        // step 5
        document.close();
    }
}
