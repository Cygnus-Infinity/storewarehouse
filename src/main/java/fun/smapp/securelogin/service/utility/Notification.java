package fun.smapp.securelogin.service.utility;

import org.springframework.stereotype.Service;

@Service
public interface Notification {

    public Boolean send(String to, String from, String content, String subject);
}
