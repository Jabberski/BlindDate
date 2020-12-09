package pl.coderslab.blinddate.service;

import pl.coderslab.blinddate.entity.Dates;
import pl.coderslab.blinddate.entity.Messages;
import pl.coderslab.blinddate.entity.User;

import java.util.List;

public interface MessageService {
    void dateMessage(Dates date);
    void noSuitableTimeMessage(User user1, User user2);
    List<Messages> getAllMessages(User user);
    void deleteMessage(Long id);
    Messages getMessage(Long id);
}
