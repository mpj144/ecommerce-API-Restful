package com.residencia.ecommerce.services;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.residencia.ecommerce.entities.Cliente;
import com.residencia.ecommerce.entities.Pedido;
import com.residencia.ecommerce.exception.EmailException;
import com.residencia.ecommerce.vo.ClienteVO;


@Component
public class EmailService {
	@Autowired
	private JavaMailSender emailSender;

	@Value("${spring.mail.username}")
	private String emailRemetente;
	
	@Value("${spring.mail.host}")
	private String emailServerHost;
	
	@Value("${spring.mail.port}")
	private Integer emailServerPort;
	
	@Value("${spring.mail.username}")
	private String emailServerUserName;
	
	@Value("${spring.mail.password}")
	private String emailServerPassword;
	
	@Value("${spring.mail.protocol}")
	private String emailServerProtocol;
	
	public JavaMailSender javaMailSender() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		Properties prop = new Properties();

		mailSender.setHost(emailServerHost);
		mailSender.setPort(emailServerPort);
		mailSender.setUsername(emailServerUserName);
		mailSender.setPassword(emailServerPassword);
		mailSender.setProtocol("smtp");
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", false);
		mailSender.setJavaMailProperties(prop);

		return mailSender;

	}

	public void emailCadastro(ClienteVO clienteVO, String senha) throws MessagingException, EmailException {

		this.emailSender = javaMailSender();

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		try {
			helper.setFrom(emailRemetente);
			helper.setTo(clienteVO.getEmail());
			helper.setSubject("Cadastro Efetuado com Sucesso " + clienteVO.getNome());

			SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
			DecimalFormat dfMoeda = new DecimalFormat("R$ ,##0.00");

			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<html>\r\n");
			sBuilder.append("	<body>\r\n");
			sBuilder.append("		<div align=\"center\">\r\n");
			sBuilder.append("			BEM VINDO AO ECOMMERCE BRASIL\r\n");
			sBuilder.append("		</div>\r\n");
			sBuilder.append("		<br/>\r\n");
			sBuilder.append("		<center>\r\n");
			sBuilder.append("		<table border='1' cellpadding='5'  >\r\n");
			sBuilder.append("<tr>  <th>ID Cliente</th>  <th>Nome</th>  <th>Login</th> <th>Senha</th> <th>CPF</th>  </tr>\r\n");

			sBuilder.append("		<tr>\r\n");
			
			sBuilder.append("			<td>\r\n");
			sBuilder.append(clienteVO.getClientId());
			sBuilder.append("			</td>\r\n");
			
			sBuilder.append("			<td>\r\n");
			sBuilder.append(clienteVO.getNome());
			sBuilder.append("			</td>\r\n");
			
			sBuilder.append("			<td>\r\n");
			sBuilder.append(clienteVO.getUsername());
			sBuilder.append("			</td>\r\n");
			
			sBuilder.append("			<td>\r\n");
			sBuilder.append(senha);
			sBuilder.append("			</td>\r\n");
			
			sBuilder.append("			<td>\r\n");
			sBuilder.append(clienteVO.getCpf());
			sBuilder.append("			</td>\r\n");
			
			sBuilder.append("		</tr>\r\n");

			sBuilder.append("		</table>\r\n");
			sBuilder.append("		</center>\r\n");
			sBuilder.append("	</body>\r\n");
			sBuilder.append("</html>");

			helper.setText(sBuilder.toString(), true);

			emailSender.send(message);

		} catch (Exception e) {
			throw new EmailException("Houve erro ao enviar o email de cadastro: " + e.getMessage());
		}

	}
	
	
	public void emailNovaSenha(Cliente cliente, String newPass) throws MessagingException, EmailException {

		this.emailSender = javaMailSender();

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		try {
			helper.setFrom(emailRemetente);
			helper.setTo(cliente.getEmail());
			helper.setSubject("Recuperacao de senha " + cliente.getNome());

			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<html>\r\n");
			sBuilder.append("	<body>\r\n");
			sBuilder.append("		<div align=\"center\">\r\n");
			sBuilder.append("			SUA NOVA SENHA E\r\n");
			sBuilder.append("		</div>\r\n");
			sBuilder.append("		<br/>\r\n");
			sBuilder.append("		<center>\r\n");
			sBuilder.append("		<table border='1' cellpadding='5'  >\r\n");
			sBuilder.append("<tr><th>NOVA SENHA</th></tr>\r\n");

			sBuilder.append("		<tr>\r\n");
			sBuilder.append("			<td>\r\n");
			sBuilder.append(newPass);
			sBuilder.append("			</td>\r\n");
			

			sBuilder.append("		</table>\r\n");
			sBuilder.append("		</center>\r\n");
			sBuilder.append("	</body>\r\n");
			sBuilder.append("</html>");

			helper.setText(sBuilder.toString(), true);

			emailSender.send(message);

		} catch (Exception e) {
			throw new EmailException("Houve erro ao enviar o email de novao senha: " + e.getMessage());
		}

	}
	
	public void emailNotaFiscal(Pedido pedido) throws MessagingException, EmailException {

		this.emailSender = javaMailSender();

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		try {
			helper.setFrom(emailRemetente);
			helper.setTo(pedido.getCliente().getEmail());
			helper.setSubject("Nota Fiscal Nº " + pedido.getNumeroDoPedido());

			SimpleDateFormat sdfData = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
			DecimalFormat dfMoeda = new DecimalFormat("R$ ,##0.00");

			StringBuilder sBuilder = new StringBuilder();
			sBuilder.append("<html>\r\n");
			sBuilder.append("	<body>\r\n");
			sBuilder.append("		<div align=\"center\">\r\n");
			sBuilder.append("			NOTA FISCAL\r\n");
			sBuilder.append("		</div>\r\n");
			sBuilder.append("		<br/>\r\n");
			sBuilder.append("		<center>\r\n");
			sBuilder.append("		<table border='1' cellpadding='6'  >\r\n");
			sBuilder.append("<tr> <th>Nº Pedido</th><th>Cliente</th><th>Valor Total</th><th>Data Entrega</th> <th>Produto</th> <th>Quantidade</th>  </tr>\r\n");

			sBuilder.append("		<tr>\r\n");
			
			sBuilder.append("			<td>\r\n");
			sBuilder.append(pedido.getNumeroDoPedido());
			sBuilder.append("			</td>\r\n");
			
			sBuilder.append("			<td>\r\n");
			sBuilder.append(pedido.getCliente().getNome());
			sBuilder.append("			</td>\r\n");
			
			sBuilder.append("			<td>\r\n");
			sBuilder.append(dfMoeda.format(pedido.getValorTotalDoPedido()));
			sBuilder.append("			</td>\r\n");
			
			sBuilder.append("			<td>\r\n");
			sBuilder.append(pedido.getDataDoPedido().plusDays(ThreadLocalRandom.current().nextInt(3,15)));
			sBuilder.append("			</td>\r\n");
			sBuilder.append("		</tr>\r\n");
			
			sBuilder.append("			<td>\r\n");
			sBuilder.append(pedido.getProdutoPedido().getProduto().getNome());
			sBuilder.append("			</td>\r\n");
			sBuilder.append("		</tr>\r\n");
			
			sBuilder.append("			<td>\r\n");
			sBuilder.append(pedido.getProdutoPedido().getQuantidade());
			sBuilder.append("			</td>\r\n");
			sBuilder.append("		</tr>\r\n");

			sBuilder.append("		</table>\r\n");
			sBuilder.append("		</center>\r\n");
			sBuilder.append("	</body>\r\n");
			sBuilder.append("</html>");

			helper.setText(sBuilder.toString(), true);

			emailSender.send(message);

		} catch (Exception e) {
			throw new EmailException("Houve erro ao enviar o email de Nota Fiscal: " + e.getMessage());
		}

	}
	
	
	
	
	
	
	
}