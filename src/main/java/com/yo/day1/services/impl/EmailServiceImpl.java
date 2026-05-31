package com.yo.day1.services.impl;

import com.yo.day1.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Async
    @Override
    public void sendAccountInfoEmail(String toEmail, String fullName, String username, String password) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject("Thông tin tài khoản truy cập hệ thống YOEDU");

            String htmlContent = String.format("""
                    <div style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">
                        <h2 style="color: #0056b3;">Kính gửi %s,</h2>
                        <p>Tài khoản truy cập hệ thống YOEDU của bạn đã được tạo thành công.</p>
                        <p>Dưới đây là thông tin đăng nhập của bạn:</p>
                        <table style="border-collapse: collapse; width: 100%%; max-width: 500px; margin-top: 15px; margin-bottom: 15px;">
                            <tr>
                                <td style="padding: 8px; border: 1px solid #ddd; font-weight: bold; width: 40%%;">Tên đăng nhập:</td>
                                <td style="padding: 8px; border: 1px solid #ddd; color: #d9534f; font-weight: bold;">%s</td>
                            </tr>
                            <tr>
                                <td style="padding: 8px; border: 1px solid #ddd; font-weight: bold;">Mật khẩu:</td>
                                <td style="padding: 8px; border: 1px solid #ddd; color: #d9534f; font-weight: bold;">%s</td>
                            </tr>
                        </table>
                        <p>Vui lòng <strong>không chia sẻ</strong> thông tin này với bất kỳ ai.</p>
                        <p>Bạn nên đổi mật khẩu ngay trong lần đăng nhập đầu tiên để đảm bảo an toàn.</p>
                        <br/>
                        <p>Trân trọng,</p>
                        <p><strong>Ban Quản Trị Hệ Thống YOEDU</strong></p>
                    </div>
                    """, fullName, username, password);

            helper.setText(htmlContent, true);
            javaMailSender.send(message);
            log.info("Email thông báo tài khoản đã được gửi đến: {}", toEmail);
        } catch (MessagingException e) {
            log.error("Lỗi khi gửi email đến {}: {}", toEmail, e.getMessage());
        }
    }
}
