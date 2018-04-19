package music.util;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import music.user.entity.User;

public class EmailUtil {
	//发件人的email
	public static final String FROM = "you_music@163.com";
	//发件人密码--邮箱密码
    public static final String PWD = "px72131020";
    public static final String PORT = "465";
    public static final String HOST ="smtp.163.com";
    //激活邮件过期时间24小时
    public static final int TIMELIMIT = 1000*60*60*24; 
   
    public static final String TITLE = "you-music账户激活邮件";
    
    public static final String SMTP = "SMTP";
    public static User activateMail(HttpServletRequest request,User user) throws EmailException{
    	//注册邮箱
        String to  = user.getEmail();
        //当前时间戳
        Long curTime = System.currentTimeMillis();
        //激活的有效时间
        Long activateTime = curTime+TIMELIMIT;
        //激活码--用于激活邮箱账号
        String token = to+curTime;
        token = Md5Util.MD5(token);
        user.setToken(token);
        user.setActivatetime(new Date(activateTime));
        //项目主页
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()+ path + "/";
        String content = "<p><b>您好</b><br><br>欢迎注册you-music!<br><br>"
        		+ "帐户需要激活才能使用，赶紧激活成为you-music正式的一员吧:)<br><br>"
        		+ "请在24小时内点击下面的链接立即激活帐户：<br>"
        		+ "<a href='"+basePath+"rest/userAction/activatemail/?token="+token+"&email="+to+"'>"
        		+ basePath+"rest/userAction/activatemail/?token="+token+"&email="+to+"</a><br><br>"
        		+ "<b>系统邮件，请勿回复!</b></p>";
       //调用发送邮箱服务
        EmailUtil.sendMail(to, TITLE, content);
        return user;
    }
    /**
     * @Title: sendMail 
     * @Description: TODO(发送163邮件) 
     * @param @param to
     * @param @param title
     * @param @param content
     * @param @throws AddressException
     * @param @throws MessagingException    设定文件 
     * @return void    返回类型 
     * @throws 
     * @author peiyongdong
     * @date 2018年4月19日 下午2:49:14
     */
    public static void send163Mail(String to,String title,String content) throws AddressException, MessagingException {
    	//加载一个配置文件  
        Properties props = new Properties(); 
        // 使用smtp：简单邮件传输协议  
        props.setProperty("mail.transport.protocol", "smtp");
         //存储发送邮件服务器的信息  
        props.put("mail.smtp.host", HOST);
        //同时通过验证  
        props.put("mail.smtp.auth", "true");
        //根据属性新建一个邮件会话  
        Session session = Session.getInstance(props);
        //session.setDebug(true); //有他会打印一些调试信息。  
        //由邮件会话新建一个消息对象  
        MimeMessage message = new MimeMessage(session);
        //设置发件人的地址  
        message.setFrom(new InternetAddress(FROM));
        //设置收件人,并设置其接收类型为TO  
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        //设置标题 
        message.setSubject(title); 
        //设置信件内容  
        //message.setText(mailContent); //发送 纯文本 邮件 todo  
        //发送HTML邮件，内容样式比较丰富  
        message.setContent(content, "text/html;charset=utf-8"); 
        //设置发信时间 
        message.setSentDate(new Date()); 
        //存储邮件信息  
        message.saveChanges();
        //发送邮件  
        Transport transport = session.getTransport(SMTP);  
        //Transport transport = session.getTransport();  
        transport.connect(FROM,PWD);
        //发送邮件,其中第二个参数是所有已设好的收件人地址  
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();  
    }
    
    /**
     * 发送邮件
     * @param to
     * @param title
     * @param content
     * @throws EmailException
     */
    public static void sendMail(String to,String title,String content) throws EmailException{
    	SimpleEmail mail = new SimpleEmail();  
    	 //设置邮箱服务器信息  
        mail.setSmtpPort(465);
        mail.setSSLOnConnect(true);
        mail.setHostName(HOST);  
        //设置密码验证器  
        mail.setAuthentication(FROM,PWD);  
        // 设置邮件发送者  
        mail.setFrom(EmailUtil.FROM);
        // 设置邮件接收者  
        mail.addTo(to); 
        // 设置邮件编码  
        mail.setCharset("UTF-8");  
        // 设置邮件主题  
        mail.setSubject(title);  
        // 设置邮件内容  
        mail.setContent(content,"text/html;charset=utf-8");
    	// 设置邮件发送时间  
        mail.setSentDate(new Date()); 
        // 发送邮件  
        mail.send();
    }
}
