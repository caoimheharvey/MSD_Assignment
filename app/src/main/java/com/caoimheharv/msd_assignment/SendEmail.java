package com.caoimheharv.msd_assignment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Config;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by CaoimheHarvey on 11/13/16.
 */
public class SendEmail extends AsyncTask<Void,Void,Void> {

//Declaring Variables
    private Context context;
    private Session session;

//Information to send email
    private String email;
    private String subject;
    private String textMessage;

//Progressdialog to show while sending email
    private ProgressDialog progressDialog;

//Class Constructor
    public SendEmail(Context context,String email,String subject,String textMessage) {
        //Initializing variables
        this.context = context;
        this.email = email;
        this.subject = subject;
        this.textMessage = textMessage;
    }

        protected void onPreExecute(){
            super.onPreExecute();
            //Showing progress dialog while sending email
            progressDialog = ProgressDialog.show(context,"Sending message","Please wait...",false,false);
        }


        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            //Dismissing the progress dialog
            progressDialog.dismiss();
            //Showing a success message
            Toast.makeText(context, "Clocked Out", Toast.LENGTH_LONG).show();
        }


        protected Void doInBackground(Void...params) {
            //Creating properties
            Properties props = new Properties();

            //Configuring properties for gmail
            //If you are not using gmail you may need to change the values
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            //Creating a new session
            session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        //Authenticating the password
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("msdassignment@gmail.com", "Medium2tr3ngth");
                        }
                    });

            try {
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("msdassignment@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject(subject);
                message.setContent(textMessage, "text/html; charset=utf-8");
                Transport.send(message);
            } catch (MessagingException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

