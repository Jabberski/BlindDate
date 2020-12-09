package pl.coderslab.blinddate.Utils;

import pl.coderslab.blinddate.entity.Dates;
import pl.coderslab.blinddate.entity.User;

import java.time.format.DateTimeFormatter;

public class MessageSchemmas {
    public  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM hh:mm");

    public static String dateMessage(User user, Dates date){
        return "Gratulacje!\nSpodobaliście się sobie nawzajem i macie już gotowy termin spotkaia!\n" +
                "Twoje spotkanie z użytkownikiem "+user.getEmail()+" odbędzie się "+formatter.format(date.getDateTime())+
                " w "+date.getPlace().getName()+" "+date.getPlace().getAddress()+"\n Pamiętaj aby być na miejscu punktualnie. \n Trzymamy kciuki!";
    }

    public static String noSuitableTimeMessage(){
        return  "Masz nową parę!\n Niestety w waszych kaledarzach brak wspólnego wolnego terminu :(\n" +
                "Spróbuj zwiększyć pulę godzin w których jesteś dostępny, może właśnie przeszła Ci koło nosa miłość życia!";
    }
}
