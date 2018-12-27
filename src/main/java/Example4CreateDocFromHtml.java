import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;

import com.itextpdf.text.PageSize;
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

import java.io.FileOutputStream;
import java.io.IOException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Example4CreateDocFromHtml {

    private enum TypeDoc {None, TurnOff, FixLeak}

    public static void main(String[] args) throws IOException, DocumentException {

        // получить путь до pdf, для создания
        File dir = new File("out/production/resources");
        File file1 = File.createTempFile("example_", ".pdf", dir);
        String pdfPath = file1.getAbsolutePath();

        //1 заполнить поля шаблона значениями из объекта
        String content = ReplaceFields(TypeDoc.TurnOff);
        //2 заполнить тег картинки
        content = ReplaceImageTag(content);

        // создание pdf при помощи itext
        Document document = new Document();
        document.setPageSize(PageSize.A4.rotate());

        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
        document.open();

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

        p.parse(new ByteArrayInputStream(content.getBytes()));
        document.close();

        System.out.println("file1 - " + pdfPath);
    }

    /**
     * Заменить поля на значения из одного шаблона полями одного объекта, второго - второго
     */
    private static String ReplaceFields(TypeDoc typeDoc) throws FileNotFoundException, IOException {
        Path path1 = null;
        if (typeDoc == TypeDoc.TurnOff)
            path1 = Paths.get(Example4CreateDocFromHtml.class.getResource("Page1.html").getFile().replaceFirst("/", ""));
        else if (typeDoc == TypeDoc.FixLeak)
            path1 = Paths.get(Example4CreateDocFromHtml.class.getResource("Page2.html").getFile().replaceFirst("/", ""));


        Charset charset = StandardCharsets.UTF_8;
        String content = new String(Files.readAllBytes(path1), charset);

        Map<String, String> map = new HashMap<>();
        if (typeDoc == TypeDoc.TurnOff)
            map = TurnOffRepo.GetTurnOffItem();
        else if (typeDoc == TypeDoc.FixLeak)
            map = FixLeakRepo.GetFixLeakIem();


        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            String oldText = "<span id=\"" + key + "\">tmp</span>";
            String newText = "<span>" + value + "</span>";
            content = content.replaceFirst(oldText, newText);// замена по ид в элементе span, на данные из объекта
        }

        return content;
    }

    /*
    в разметке заменить пустой код картинки
     */
    private static String ReplaceImageTag(String content) throws FileNotFoundException, IOException {

        String imgFilePath1 = Example4CreateDocFromHtml.class.getResource("300.png").getFile();

        File imgFile = new File(imgFilePath1);
        String encodedfile = EncodeFileToBase64Binary(imgFile);

        String oldText = "<img src=\"\" />";
        String newText = "<img src=\"data:image/png;base64," + encodedfile + "\" />";

        content = content.replaceFirst(oldText, newText);

        return content;
    }


    /**
     * Получить из файла картинки строку base64
     */
    private static String EncodeFileToBase64Binary(File file) {
        String encodedfile = null;
        try {
            FileInputStream fileInputStreamReader = new FileInputStream(file);
            byte[] bytes = new byte[(int) file.length()];
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.getEncoder().encodeToString(bytes);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encodedfile;
    }

}
