package test.com.offerme.mail;

import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.util.Properties;
import java.util.UUID;

import javax.mail.Authenticator;
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
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

public class HMail {

	  private Session session;

	    private String username;
	    private String password;
	    private String nick;
	    private String host;
	    private String text;

	    public void gmailSend(String to,String subject,String message) throws AddressException, MessagingException {
	        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
//	        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	        // Get a Properties object
	        Properties props = System.getProperties();

	        props.setProperty("mail.smtp.host", "localhost");
//	        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
	        props.setProperty("mail.smtp.socketFactory.fallback", "false");
	        props.setProperty("mail.smtp.port", "25");
//	        props.setProperty("mail.smtp.socketFactory.port", "465");
	        props.put("mail.smtp.auth", "true");

	        Session session = Session.getDefaultInstance(props,
	                new Authenticator() {
	                    protected PasswordAuthentication getPasswordAuthentication() {
	                        return new PasswordAuthentication(getUsername(), getPassword());
	                    }
	                });
	        // -- Create a new message --
	        Message msg = new MimeMessage(session);
	        String nick = "";
	        nick = getNick();
	        if(nick!= null && !"".equals(nick))
	        {
	        	 try {  
	                 nick=javax.mail.internet.MimeUtility.encodeText(nick);  
	             } catch (UnsupportedEncodingException e) {  
	                 e.printStackTrace();  
	             }   
	        }
//	        msg.setFrom(new InternetAddress(getUsername()));
	        msg.setFrom(new InternetAddress(nick+" <"+getUsername()+">"));
	        // Set the to address
	        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
	        msg.setSubject(subject);
	        msg.setText(message);
	        // msg.setSentDate(new Date());
	        msg.saveChanges();
	        Transport.send(msg);
	        System.out.println("Message sent.");        
	}


	    public String gmailFetch(int sum, String username, String password) {
	        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
	        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
	        // Get a Properties object
	        Properties props = System.getProperties();
	        props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
	        props.setProperty("mail.pop3.socketFactory.fallback", "false");
	        props.setProperty("mail.pop3.port", "995");
	        props.setProperty("mail.pop3.socketFactory.port", "995");
	        Session session = Session.getInstance(props, null);
	        URLName urln = new URLName("pop3", "pop.gmail.com", 995, null,
	                username, password);
	        Store store;
	        try {
	            store = session.getStore(urln);
	        } catch (NoSuchProviderException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	            String result = "{\"type\":\"error\",\"body\":{\"reason\":\"" + e
	                    + "\"}}";
	            return result;
	        }
	        Folder inbox = null;
	        try {
	            store.connect();
	            inbox = store.getFolder("INBOX");
	            inbox.open(Folder.READ_ONLY);
	            // FetchProfile profile = new FetchProfile();
	            // profile.add(FetchProfile.Item.ENVELOPE);
	            Message[] messages = inbox.getMessages();
	            // inbox.fetch(messages, profile);
	            System.out.println("收件箱的邮件数：" + messages.length);
	            String result = "";
	            result += "{\"type\":\"api\",\"body\":{\"emails\":[";
	            for (int i = messages.length - 1; i >= messages.length - sum
	                    && i >= 0; i--) {
	                // 邮件发送者
	                String from;
	                try {
	                    from = decodeText(messages[i].getFrom()[0].toString());
	                } catch (UnsupportedEncodingException e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                    result = "{\"type\":\"error\",\"body\":{\"reason\":\"" + e
	                            + "\"}}";
	                    return result;
	                }
	                InternetAddress ia = new InternetAddress(from);
	                System.out.println("FROM:" + ia.getPersonal() + '('
	                        + ia.getAddress() + ')');
	                // 邮件标题
	                System.out.println("TITLE:" + messages[i].getSubject());
	                // 邮件大小
	                //System.out.println("SIZE:" + messages[i].getSize());
	                // 邮件发送时间
	                System.out.println("DATE:" + messages[i].getSentDate());
	                System.out.println("CONTENT:");
	                text = "";
	                try {
	                    getMailContent(messages[i]);
	                } catch (Exception e) {
	                    // TODO Auto-generated catch block
	                    e.printStackTrace();
	                    result = "{\"type\":\"error\",\"body\":{\"reason\":\"" + e
	                            + "\"}}";
	                    return result;
	                }
	                result += "{\"subject\":\"" + messages[i].getSubject() + "\"";
	                result += ",\"size\":\"" + messages[i].getSize() + "\"";
	                result += ",\"date\":\"" + messages[i].getSentDate() + "\"";
	                result += ",\"content\":\"" + text + "\"}";
	                if (i == 0 || i == messages.length - sum) {
	                    result += "]}}";
	                } else {
	                    result += ",";
	                }

	            }
	            return result;
	        } catch (MessagingException e1) {
	            // TODO Auto-generated catch block
	            String result = "{\"type\":\"error\",\"body\":{\"reason\":\"" + e1
	                    + "\"}}";
	            return result;
	        }
	    }
	    public void getMailContent(Part part) throws Exception {

	        StringBuffer bodytext = new StringBuffer();
	        String contenttype = part.getContentType();
	        int nameindex = contenttype.indexOf("name");
	        boolean conname = false;
	        if (nameindex != -1)
	            conname = true;
	        //System.out.println("CONTENTTYPE: " + contenttype);

	        if (part.isMimeType("text/plain") && !conname) {
	            bodytext.append((String) part.getContent());
	            //bodytext.append(new String(((String) part.getContent())
	            //        .getBytes("ISO-8859-1"), "utf-8"));
	            System.out.println(bodytext.toString());
	            text += bodytext.toString();
	        } else if (part.isMimeType("text/html") && !conname) {
	            bodytext.append((String) part.getContent());
	            System.out.println(bodytext.toString());
	            text += bodytext.toString();
	        } else if (part.isMimeType("multipart/*")) {
	            Multipart multipart = (Multipart) part.getContent();
	            int counts = multipart.getCount();
	            for (int i = 0; i < counts; i++) {
	                getMailContent(multipart.getBodyPart(i));
	            }
	        } else if (part.isMimeType("message/rfc822")) {
	            getMailContent((Part) part.getContent());
	        } else {
	        }
	    }

	    public void login(String host,String username,String password, String nick) {
	        

	        if (host.equals("") || username.equals("") || password.equals("")) {
	            String result = "{\"type\":\"error\",\"body\":{\"reason\":\"参数host、username、password都不能为空！\"}}";
	            System.out.println(result);
	            return;
	        }
	        setHost(host);
	        setUsername(username);
	        setPassword(password);
	        setNick(nick);
	    }

	    protected static String decodeText(String text)
	            throws UnsupportedEncodingException {
	        if (text == null)
	            return null;
	        if (text.startsWith("=?GB") || text.startsWith("=?gb"))
	            text = MimeUtility.decodeText(text);
	        else
	            text = new String(text.getBytes("ISO8859_1"));
	        return text;
	    }

	    public void setUsername(String username) {
	        this.username = username;
	    }

	    public String getUsername() {
	        return username;
	    }

	    public void setHost(String host) {
	        this.host = host;
	    }

	    public String getHost() {
	        return host;
	    }

	    public void setText(String text) {
	        this.text = text;
	    }

	    public String getText() {
	        return text;
	    }
	    
	    public String getPassword() {
	        return password;
	    }

	    public void setPassword(String password) {
	        this.password = password;
	    }
	    
	    public String getNick() {
	        return nick;
	    }

	    public void setNick(String nick) {
	        this.nick = nick;
	    }
	    

	    public void setSession(Session session) {
	        this.session = session;
	    }

	    public Session getSession() {
	        return session;
	    }

	    public static void main(String[] args){
	    	HMail email = new HMail();
	    	StringBuilder sb = new StringBuilder();
	    	sb.append("以下是来自【伯乐去哪儿了】随机生成的32位随机码\n");
	    	sb.append(UUID.randomUUID().toString().replaceAll("-", ""));
	    	sb.append("\n");
//	    	sb.append("请填写");
	    	
	        email.login("smtp.gmail.com", "noreply@offerme.com", "noreply","noreply");    
	        try {
	            email.gmailSend("edouardzyc@hotmail.com","Test",sb.toString());
	            email.gmailSend("695397528@qq.com","Test",sb.toString());
	            email.gmailSend("724358126@qq.com","Test",sb.toString());
	            email.gmailSend("121487146@qq.com","Test",sb.toString());
	            email.gmailSend("228091069@qq.com","Test",sb.toString());
	            email.gmailSend("edouardzyc@gmail.com","Test",sb.toString());
	            email.gmailSend("youchengzhang@sina.com","Test",sb.toString());
	            email.gmailSend("jwy_3000@hotmail.com","Test",sb.toString());
	            email.gmailSend("mengdi428@gmail.com","Test",sb.toString());
	            email.gmailSend("yuemin.qin@yahoo.com","Test",sb.toString());
	        } catch (AddressException e) {
	            e.printStackTrace();
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
//	        email.gmailFetch(1,"**********@gmail.com", "********");//第一个参数为显示邮件的个数
	    }
	}
