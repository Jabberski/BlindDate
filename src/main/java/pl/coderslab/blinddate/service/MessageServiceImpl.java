package pl.coderslab.blinddate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.blinddate.entity.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService{

    @Override
    public void dateMessage(User user1, User user2) {

    }
}
