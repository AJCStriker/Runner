
package net.countercraft.runner.managers;

//Java Imports
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException; 
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// Xerces classes.
import net.countercraft.runner.Controller;
import org.bukkit.entity.Player;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;
import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

public class ErrorManager {
    private int fileCount;
    private File errorLogFile = new File(Controller.getDataFolder() + "/errorReports");
    
    private ErrorManager() {
    }
    
    public static ErrorManager getInstance() {
        return ErrorManagerHolder.INSTANCE;
    }
    
    private static class ErrorManagerHolder {

        private static final ErrorManager INSTANCE = new ErrorManager();
    }
    
    
    public String createErrorReport(Exception ex, String executorConcerned){
        try {
            fileSanityTest();
            Player pConcerned = null;
            String daemonName = "";
            
            
            
            int fileNum = errorLogFile.list().length;
            fileNum = fileNum + 1;
            String unHashedFileName = fileNum + "";
            String filenameHashed = generateMD5ofString(unHashedFileName);
            String filename = filenameHashed.substring(0, 6);
            String outFile = errorLogFile + "/" + filename + ".xml";
            String pluginVersion = Controller.getPluginInstance().getVersion();
            
            String ErrorTitle = "";
            ErrorTitle = ex.getMessage();
            String stackTrace = "";
            stackTrace = createStackTraceasString(ex);
            
            String date = getCurrentTimeStamp();
            
            FileOutputStream fos = new FileOutputStream(outFile);
            
            OutputFormat of = new OutputFormat("XML","ISO-8859-1",true);
            of.setIndent(1);
            of.setIndenting(true);
            XMLSerializer serializer = new XMLSerializer(fos,of);
           
            ContentHandler hd = serializer.asContentHandler();
            hd.startDocument();
            
            // Processing instruction sample
            //hd.processingInstruction("xml-stylesheet","type=\"text/xsl\" href=\"users.xsl\"");
            
            AttributesImpl atts = new AttributesImpl();
            
            hd.startElement("","","Error",atts);
            
              hd.startElement("","","StackTrace",atts);
              hd.characters(stackTrace.toCharArray(), 0, stackTrace.length());
              hd.endElement("","","StackTrace");
              
              //So lets add our Exception Title here
              hd.startElement("","","ExceptionType",atts);
              System.out.println(ErrorTitle);
              hd.characters(ErrorTitle.toCharArray(),0,ErrorTitle.length());
              hd.endElement("","","ExceptionType");
              
              //Date and Time stamp
              
              hd.startElement("","","Date",atts);
              hd.characters(date.toCharArray(),0,date.length());
              hd.endElement("","","Date");
              
              
              
              hd.startElement("","","StackTrace",atts);
              hd.characters(stackTrace.toCharArray(), 0, stackTrace.length());
              hd.endElement("","","StackTrace");
            
            hd.endElement("","","Error");
            hd.endDocument();
            fos.close();
            
            
            System.out.println("[Runner] Error created. Id was #" + filename + " . Please send this file to support@countercraft.net");
            
            return "#" + filename;
            
        } catch (NoSuchAlgorithmException error) {
            
            
        }catch (FileNotFoundException fileError){
            System.out.println("[Runner] File exceptions in the ERS, please review immediately.");
        
        }catch (IOException error){
            System.out.println("[Runner] File IO error in the ERS, please review immediately.");
            
        }catch (SAXException error){
            System.out.println("[Runner] Sax error in the ERS, please review immediately.");
        }catch (NullPointerException error){
            System.out.println("[Runner] has encountered an issue while trying to create an error report.");
        }
        
        System.out.print("Our error system has failed.");
        return "Our error system has failed";
    }
    
    public void fileSanityTest(){
        if(!errorLogFile.exists()){
            errorLogFile.mkdirs();
        }
        errorLogFile.setReadable(true);
        errorLogFile.setWritable(true);
    }
    
    
    private String createStackTraceasString(Throwable t){
        final Writer result = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(result);
        t.printStackTrace(printWriter);
        return result.toString();
    }
    
    private String getCurrentTimeStamp(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
        
    public String generateMD5ofString(String input) throws NoSuchAlgorithmException{
        
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update((input).getBytes());
            byte[] messageDigest = digest.digest();
            StringBuffer hexString = new StringBuffer();
            
            for (int i=0;i<messageDigest.length;i++) {
              int j = 0xFF & messageDigest[i];
              if (j < 16) hexString.append("0");
              hexString.append(Integer.toHexString(j));
            }
            return new String(hexString);
    }
}
