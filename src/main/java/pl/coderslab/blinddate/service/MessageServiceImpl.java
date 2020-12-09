package pl.coderslab.blinddate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.blinddate.Utils.MessageSchemmas;
import pl.coderslab.blinddate.entity.Dates;
import pl.coderslab.blinddate.entity.Messages;
import pl.coderslab.blinddate.entity.User;
import pl.coderslab.blinddate.repository.MessagesRepository;


@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService{

    private final MessagesRepository messagesRepository;

    @Override
    public void dateMessage(Dates date) {
        log.warn("Sending new date message");
        Messages message1 = new Messages();
        Messages message2 = new Messages();
        message1.setTitle("Nowe spotkanie!");
        message2.setTitle("Nowe spotkanie!");
        message1.setText(MessageSchemmas.dateMessage(date.getUser2(), date));
        message2.setText(MessageSchemmas.dateMessage(date.getUser1(), date));
        message1.setUser(date.getUser1());
        message2.setUser(date.getUser2());
        messagesRepository.save(message1);
        messagesRepository.save(message2);

    }

    @Override
    public void noSuitableTimeMessage(User user1, User user2) {
        log.warn("Sending no suitable time message");
        Messages message1 = new Messages();
        Messages message2 = new Messages();
        message1.setTitle("Prawie to masz!");
        message2.setTitle("Prawie to masz!");
        message1.setText(MessageSchemmas.noSuitableTimeMessage());
        message2.setText(MessageSchemmas.noSuitableTimeMessage());
        message1.setUser(user1);
        message2.setUser(user2);
        messagesRepository.save(message1);
        messagesRepository.save(message2);

    }
}
