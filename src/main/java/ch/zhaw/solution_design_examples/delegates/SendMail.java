/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.zhaw.solution_design_examples.delegates;

import ch.zhaw.solution_design_examples.exceptions.SendMailException;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 *
 * @author kell
 */
public class SendMail implements JavaDelegate {

    @Override
    public void execute(DelegateExecution de) throws Exception {
        System.out.println("SendMail: expecting following local variables:");
        System.out.println("from");
        System.out.println("to");
        System.out.println("subject");
        System.out.println("message");
        System.out.println("messageBodyParts [optional]: expecting byte[] as application/pdf");
        System.out.println("throws SendMailException");
        //furthermore mailserver properties must be available
        //mailserver
        //mailuser
        //mailuserpwd
        //mailserverport
        //if properties are not available nothing is sent but no BpmnError is thrown
        
        String from, to, subject, message;
        Map<String,Object> messageBodyParts;
        try{
            Map<String,Object> vars = de.getVariablesLocal();
            if(de.hasVariableLocal("from")){
                from = (String) de.getVariableLocal("from");
            } else {
                throw new SendMailException("Missing local variable 'from'");
            }
            if(de.hasVariableLocal("to")){
                to = (String) de.getVariableLocal("to");
            } else {
                throw new SendMailException("Missing local variable 'to'");
            }
            if(de.hasVariableLocal("subject")){
                subject = (String) de.getVariableLocal("subject");
            } else {
                throw new SendMailException("Missing local variable 'subject'");
            }
            if(de.hasVariableLocal("message")){
                message = (String) de.getVariableLocal("message");
            } else {
                throw new SendMailException("Missing local variable 'message'");
            }
            messageBodyParts = (Map) de.getVariableLocal("messageBodyParts");

            //get mailserver properties
            Map<String,String> properties = (Map<String,String>) de.getVariable("properties");
            String mailserver = properties.get("mailserver");
            String mailserverport = properties.get("mailserverport");
            final String mailuser = properties.get("mailuser");
            final String mailuserpwd = properties.get("mailuserpwd");
            if(mailserver.equals(null) || mailserverport.equals(null) || mailuser.equals(null) || mailuserpwd.equals(null)){
                System.out.println("sending mail ...");
                System.out.println(from + " to " + to);
                System.out.println(subject);
                System.out.println(message);
            } else {
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", mailserver);
                props.put("mail.smtp.port", mailserverport);
                    // Get the Session object.
                Session session = Session.getInstance(props,
                   new javax.mail.Authenticator() {
                      @Override
                      protected PasswordAuthentication getPasswordAuthentication() {
                         return new PasswordAuthentication(mailuser, mailuserpwd);
                      }
                   });
                // Create a default MimeMessage object.
                Message msg = new MimeMessage(session);

                // Set From: header field of the header.
                msg.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                msg.setRecipients(Message.RecipientType.TO,
                   InternetAddress.parse(to));

                // Set Subject: header field
                msg.setSubject(subject);

                // Create the message part
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setText(message);
                
                // Create a multipart message
                Multipart multipart = new MimeMultipart();
                
                // Set text message part
                multipart.addBodyPart(messageBodyPart);

                // add messageBodyParts from local variables to the mail
                for (Map.Entry<String, Object> entry : messageBodyParts.entrySet()){
                    messageBodyPart = new MimeBodyPart();
                    DataSource source = new ByteArrayDataSource((byte[]) entry.getValue(), "application/pdf");
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(entry.getKey());
                    multipart.addBodyPart(messageBodyPart);
                }
                // Send the complete message parts
                msg.setContent(multipart);
                Transport.send(msg);
            }
            
        } catch (SendMailException ex){
            throw new BpmnError("SendMailException", ex.getMessage());
        } catch (MessagingException ex){
            throw new BpmnError("SendMailException", ex.getMessage());
        }
        
    }
    
}
