package com.example.javabot.tools;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.Instant;

@Component
public class BotMessageSender {
    private BotKeyboard botKeyboard;
    private SendMessage sendMessage;
    private SendDocument sendDocument;
    private Message message;
    private Update update;

    public SendMessage getStartMessage() {
        message = update.getMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));

        // action in start
        if (message.getText().equals("/start")) {
            String helloText = "Привіт " + message.getFrom().getFirstName() + " " + " мене зовуть FreshBot і я радий вас привітати))) \n"
                    + "На даний час я можу вам надати: \n" +
                    "1. Каталог товарів у форматі PDF \n" +
                    "2. Посилання на інстаграм \n" +
                    "3. Контакти \n" +
                    "4. Акції \n \n";

            helloText += "Якщо є якісь запитання то можешь написати до адміністрації в інстаграмм " +
                    "або в особисті повідомлення у телеграм за номером який знайдете у контактах ! ";

            sendMessage.setText(helloText);

            // show keyboard
            sendMessage.enableMarkdown(true);
            sendMessage.setReplyMarkup(botKeyboard.getKeyboard());
        }

        return sendMessage;
    }

    public SendMessage getFreeMessage() {
        sendMessage.setText("Привіт " + message.getChat().getUserName() +
                " ми отримали твоє повідомлення: "
                + message.getText() + "\n" +
                "Ви скоро отримаєте нашу відповідь)");
        return sendMessage;
    }

    // button action list products
    public SendMessage getCatalog() {
//            File file = new File("D:\\DownloadFromInternet\\java-bot\\java-bot\\src\\main\\resources\\instructions.pdf");
//            InputFile inputFile = new InputFile(file, "docFileForFresh");
//            sendDocument = new SendDocument(String.valueOf(message.getChatId()), inputFile);

        sendMessage.setText("Вибач мене, але цей розділ знаходиться у стані розробки....");
        return sendMessage;
    }

    // button action list products
    public SendMessage getContacts() {
        sendMessage.setText("email: fresh.greeneery.ua@gmail.com \n" +
                "моб.тел: +38 095 752 08 25 \n" +
                "Username адміністрації у телеграмм: @Dev_Fresh ");

        return sendMessage;
    }


    // button action list products
    public SendMessage getInstagram() {
        String message = "https://www.instagram.com/fresh_greenery.ua \n";
        message += "На цій інстаграм сторінці ви знайдете найсвіжіші новини, актуальний список товарів та послуг";

        sendMessage.setText(message);
        return sendMessage;
    }

    // button action receding
    public SendMessage getReceding() {
        sendMessage.setText("На даний час акції відсутні");
        return sendMessage;
    }

    // any other message

    public void getConsoleMessage() {
        System.out.println("==================================================" +
                "\n You have got a message: " + message.getText() +
                "\n from user: " + message.getFrom().getUserName() +
                "\n message`s time:" + Instant.now() +
                "\n==================================================");
    }
}
