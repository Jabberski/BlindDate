package pl.coderslab.blinddate.Utils;

import pl.coderslab.blinddate.entity.Dates;
import pl.coderslab.blinddate.entity.User;

import java.time.format.DateTimeFormatter;

public class MessageSchemmas {
    public  static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM hh:mm");

    public static String dateMessage(User user, Dates date){
        return "Gratulacje!\n<br/> Spodobaliście się sobie nawzajem i macie już gotowy termin spotkaia!\n" +
                "Twoje spotkanie z użytkownikiem "+user.getEmail()+" odbędzie się "+formatter.format(date.getDateTime())+
                " w "+date.getPlace().getName()+" "+date.getPlace().getAddress()+"\n Pamiętaj aby być na miejscu punktualnie. \n Trzymamy kciuki!";
    }

    public static String noSuitableTimeMessage(){
        return  "Masz nową parę!\n Niestety w waszych kaledarzach brak wspólnego wolnego terminu :(\n" +
                "Spróbuj zwiększyć pulę godzin w których jesteś dostępny, może właśnie przeszła Ci koło nosa miłość życia!";
    }

    public static String welcomeMessage(User user){
        return "Witaj "+user.getUserDetails().getName()+"!\n Bardzo nam miło, że postanowiłeś skorzystać z usług naszej strony." +
                "\n Aby zacząć swoją przygodę z tym serwisem, musisz:\n -Wypełnić swój kalendarz, który znajdziesz w zakladce 'Twój kalendarz'\n" +
                "-Wejść na ekran główny i polubić osoby, z którymi chcesz się spotkać\n" +
                "-Jeśli one również cię polubią, otrzymasz odpowiednią wiadomość a także nowe spotkanie w zakładce 'Twoje spotkania'\n" +
                "-Przyjdź punktualnie na spotkanie w wyznaczonym miejscu i daj z siebie wszystko! \n" +
                "Pamiętaj, że nasza aplikacja opiera się na zaufaniu, dlatego szanuj czas drugiej osoby i nie opuszczaj umówionych spotkań\n" +
                "To wszystko, reszta jest w twoich rękach. \n POWODZENIA!";
    }
}
