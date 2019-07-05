package com.training.serviceimpl;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.training.service.EmailService;
import com.training.entity.Data;
import com.training.repository.ProblemRepo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

	public String fromAddress=null;
    
	@Autowired
	ProblemRepo repo;

	@Value("${username1}")
	private String userName;
	@Value("${password}")
	private String password;

	@Value("${pophost}")
	private String pop3Host;


	@Override
	public void receiveEmail() {

		Properties props = new Properties();
		props.put("mail.store.protocol", "pop3");
		props.put("mail.pop3.host", pop3Host);
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.starttls.enable", "true");

		Session session = Session.getDefaultInstance(props);
		MimeBodyPart part = null;
		PDDocument doc = null;

		try {
			Store store = session.getStore("pop3s");
			store.connect(pop3Host, userName, password);

			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			Message[] messages = emailFolder.getMessages();
			System.out.println("Total Message" + messages.length);

			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				Address[] toAddress = message.getRecipients(Message.RecipientType.TO);
				System.out.println("---------------------------------");
				System.out.println("Details of Email Message " + (i + 1) + " :");
				System.out.println("Subject: " + message.getSubject());
				fromAddress= message.getFrom()[0].toString();
	
				System.out.println("From: " + message.getFrom()[0]);
				

				System.out.println("To: ");
				for (int j = 0; j < toAddress.length; j++) {
					System.out.println(toAddress[j].toString());
				}
				Object content = message.getContent();
				if (content instanceof String) {
				} else if (content instanceof Multipart) {
					Multipart multipart = (Multipart) content;

					for (int k = 0; k < multipart.getCount(); k++) {
						part = (MimeBodyPart) multipart.getBodyPart(k);

						if (part.getDisposition() != null && part.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {

							doc = PDDocument.load(part.getInputStream());
						}
					}
				}
			}
			if (doc == null) {
				System.out.println("Email does not contain any attachment or email  not arrived");
			} else {
				fileExtract(doc);
			}
		} catch (

		NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void fileExtract(PDDocument doc) {
		try {

			doc.getClass();

			if (!doc.isEncrypted()) {

				PDFTextStripperByArea stripper = new PDFTextStripperByArea();
				stripper.setSortByPosition(true);

				PDFTextStripper tStripper = new PDFTextStripper();

				String pdfFileInText = tStripper.getText(doc);
				String lines[] = pdfFileInText.split("\n");
				String invoiceNo = "", invoiceDate = "", address = "", amount = "";
				String customerPO = "";
				for (int i = 0; i < lines.length; i++) {
					if (lines[i].trim().equals("Invoice No")) {
						invoiceNo = lines[i + 1].trim();
					}
					if (lines[i].trim().equals("Invoice Date"))
						invoiceDate = lines[i + 1].trim();
					if (lines[i].trim().equals("Customer P.O."))
						customerPO = lines[i + 1].trim();
					if (lines[i].trim().equals("Sold To")) {
						while (!lines[++i].trim().startsWith("Ship To"))
							address += lines[i] + " ";
					}
				}
				outer: for (int i = 0; i < lines.length; i++) {
					if (lines[i].trim().equals("Total Invoice")) {
						for (int j = i + 1; j < lines.length; j++) {
							if (!lines[j + 1].trim().startsWith("$")) {
								amount = lines[j].trim();
								amount = amount.replace(",", "");
								amount = amount.replace("$", "");
								break outer;
							}
						}
					}
				}
				doc.close();
				Data data = new Data();
				data.setInvoiceNumber(invoiceNo);
				data.setInvoiceDate(invoiceDate);
				data.setCustoPO(customerPO);
				data.setAddress(address);
				data.setTotalInvoice(amount);
				repo.save(data);
			}
		} catch (Exception e) {
			if (e instanceof NullPointerException) {
				System.out.println("email does not contain attachement");
			}
		}
	}
	@Override
	public void sendingEmail(String emailid){  
        //Get properties object    
        Properties props = new Properties();    
        props.put("mail.smtp.host", "smtp.gmail.com");    
        props.put("mail.smtp.socketFactory.port", "465");    
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");    
        props.put("mail.smtp.auth", "true");    
        props.put("mail.smtp.port", "465");    
        //get Session   
        Session session = Session.getDefaultInstance(props,    
         new javax.mail.Authenticator() {    
         protected PasswordAuthentication getPasswordAuthentication() {    

       return new PasswordAuthentication(userName,password);  
         }    
        });    
        //compose message    
        try {    
         MimeMessage message = new MimeMessage(session); 
/*         Matcher m = Pattern.compile("<(.+?)>").matcher(fromAddress);
         while (m.find()) {
        	    System.out.println(m.group());
         }*/
     
		message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailid));    
         message.setSubject("Aknowledgement");    
         message.setText(" Hi, \nInvoice is approved \nThanks");    
         //send message  
         Transport.send(message);    
         System.out.println("message sent successfully");    
        } catch (MessagingException e) {throw new RuntimeException(e);}    
	
	}	
}

