package pl.coderslab.blinddate.service;

import pl.coderslab.blinddate.entity.Dates;
import pl.coderslab.blinddate.entity.User;

public interface MessageService {
    void dateMessage(Dates date);
    void noSuitableTimeMessage(User user1, User user2);
}
