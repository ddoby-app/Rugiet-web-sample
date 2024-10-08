package Base;

import org.openqa.selenium.Platform;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Properties;


public class BaseMethods {

    public final static String project = "Rugiet-web-sample";

    //public RemoteWebDriver driver;
    public final static String home = System.getProperty("user.home");
    public final LocalDate now = LocalDate.now();
    public final String today = now.toString();
    public String separator = File.separator;


    public String todayPlus (int numOfWeeks) throws ParseException {
        SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyy");
        LocalDate date = LocalDate.now().plusWeeks(numOfWeeks);
        Date parsedDate = mdy.parse(String.valueOf(date));
        return date.toString();
    }
    public String reportFilePath(String project, String environmentLabel, String reportType, String extension) {
        String reportFilePath = home.concat(separator).concat("IdeaProjects").concat(separator).concat(project).concat(separator).concat("TestResults").concat(separator).concat(environmentLabel).concat("-").concat(reportType).concat("-").concat(today).concat(extension);
        return reportFilePath;
    }


    public String driverFilePath(String project, String driver) {
        String driverFilePath = home.concat(separator).concat("IdeaProjects").concat(separator).concat(project).concat(separator).concat("Drivers").concat(separator).concat(driver);
        return driverFilePath;
    }

    public String attachFilepath (String fileName, String extension) {
        Platform platform = Platform.getCurrent();
        String attachFilePath = "";
        if (platform.is(Platform.WINDOWS)) {
            attachFilePath = home.concat(separator).concat("IdeaProjects").concat(separator).concat("hadrian-flow-automation").concat(separator).concat("HadrianFiles").concat(separator).concat(fileName).concat(".exe");
        } else if (platform.is(Platform.MAC)) {
            attachFilePath = home.concat(separator).concat("IdeaProjects").concat(separator).concat("hadrian-flow-automation").concat(separator).concat("HadrianFiles").concat(separator).concat(fileName).concat(extension);
        }
        return attachFilePath;
    }

    public void sendEmail(String project, String environmentLabel, String reportType, String extension, String recipient, String subject, String body) {
        String to = recipient;
        String from = "domoniq@hadrian.co";

        //Get the session object
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("domoniq@hadrian.co", "tfysxiouukqnbcel");
            }
        });

        //compose the message
        try{
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(subject);

            //send attachment
            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPart1 = new MimeBodyPart();
            String filename = reportFilePath(project,environmentLabel, reportType, extension);
            DataSource source = new FileDataSource(filename);
            messageBodyPart1.setDataHandler(new DataHandler(source));
            messageBodyPart1.setFileName(filename);
            BodyPart messageBodyPart2 = new MimeBodyPart();
            messageBodyPart2.setText(body);
            multipart.addBodyPart(messageBodyPart1);
            multipart.addBodyPart(messageBodyPart2);
            message.setContent(multipart);

            // Send message
            Transport.send(message);
            System.out.println("message sent successfully....");
            System.out.println("EMAIL TO " + recipient + " CREATED SUCCESSFULLY!!!");

        }catch (MessagingException mex) {mex.printStackTrace();}
    }

    public  Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }
}
