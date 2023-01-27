package com.example.javabot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class BotService extends TelegramLongPollingBot {
    private SendDocument sendDocument;
    private SendMessage messageForMe;

    @Override
    public String getBotUsername() {
        return "fresh_greenery_bot";
    }

    @Override
    public String getBotToken() {
        return "5963407737:AAGA4D7rrRxIP7rM8TmAI3GgvgIEUHwiFzA";
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        Message messageFromUser = update.getMessage();

        sendMessage.setChatId(String.valueOf(messageFromUser.getChatId()));

        // action in start
        if (messageFromUser.getText().equals("/start")) {

            String helloText = "Привіт " + messageFromUser.getFrom().getFirstName() + " " + " мене зовуть FreshBot і я радий вас привітати))) \n"
                    + "На даний час я можу вам надати: \n \n" +
                    "1. Каталог товарів \n" +
                    "2. Посилання на інстаграм \n" +
                    "3. Контакти \n" +
                    "4. Акції \n \n";

            helloText += "Якщо є якісь запитання то можешь написати до адміністрації в інстаграмм " +
                    "або в особисті повідомлення у телеграм за номером який знайдете у контактах ! ";

            sendMessage.setText(helloText);
            sendMessage.enableMarkdown(true);
            sendMessage.setReplyMarkup(getKeyboard());
        }

        // button action list products
        else if (messageFromUser.getText().equals("Каталог товарів")) {

//            File file = new File("D:\\DownloadFromInternet\\java-bot\\java-bot\\src\\main\\resources\\instructions.pdf");
//            InputFile inputFile = new InputFile(file, "docFileForFresh");
//            sendDocument = new SendDocument(String.valueOf(message.getChatId()), inputFile);

//            sendMessage.setText("https://sites.google.com/view/freshgreenery-pp-ua/");
            sendMessage.setText("Перейдіть по посиланню вказаному нижче");
            sendMessage.setReplyMarkup(getInlineKeyboard());
        }

        // button action list products
        else if (messageFromUser.getText().equals("Наші контакти")) {
            sendMessage.setText("email: fresh.greeneery.ua@gmail.com \n" +
                    "моб.тел: +38 095 752 08 25 \n" +
                    "Username адміністрації у телеграмм: @Dev_Fresh ");
        }

        // button action list products
        else if (messageFromUser.getText().equals("Інстаграмм")) {
            String message = "https://www.instagram.com/fresh_greenery.ua \n";
            message += "На цій інстаграм сторінці ви знайдете найсвіжіші новини, актуальний список товарів та послуг";

            sendMessage.setText(message);
        }

        // button action list products
        else if (messageFromUser.getText().equals("Акції")) {
            sendMessage.setText("На даний час акції відсутні");
        }

        // any other message
        else {
            sendMessage.setText("Привіт " + messageFromUser.getFrom().getFirstName() + " ми отримали твоє повідомлення: "
                    + messageFromUser.getText() + "\n" + "Ви скоро отримаєте відповідь від адміна)");

            String text = ("==================================================" +
                    "\n You have got a message: " + messageFromUser.getText() +
                    "\n from user: " + messageFromUser.getFrom().getUserName() +
                    "\n first name: " + messageFromUser.getFrom().getFirstName() +
                    "\n last name: " + messageFromUser.getFrom().getLastName() +
                    "\n user`s id: " + messageFromUser.getFrom().getId() +
                    "\n message`s time: " + Instant.now() +
                    "\n==================================================");

            messageForMe = new SendMessage();
            messageForMe.setChatId(String.valueOf(5699067150L));
            messageForMe.setText(text);
        }

        // only execute
        try {
            if (sendDocument != null) {
                execute(sendDocument);
            } else {
                execute(sendMessage);
                if (messageForMe != null) {
                    execute(messageForMe);
                }
            }
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    // Keyboard for bot
    private ReplyKeyboardMarkup getKeyboard() {
        // visible fro keyboard
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        // new keyboard
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        // first row
        KeyboardRow keyboardButtons1 = new KeyboardRow();
        keyboardButtons1.add("Каталог товарів");
        keyboardButtons1.add("Наші контакти");

        // second row
        KeyboardRow keyboardButtons2 = new KeyboardRow();
        keyboardButtons2.add("Інстаграмм");
        keyboardButtons2.add("Акції");

        // add to list
        keyboardRowList.add(keyboardButtons1);
        keyboardRowList.add(keyboardButtons2);

        // add list to keyboard markup
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
        return replyKeyboardMarkup;
    }

    private InlineKeyboardMarkup getInlineKeyboard () {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List <InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Перейти до каталогу");
        inlineKeyboardButton.setUrl("https://sites.google.com/view/freshgreenery-pp-ua/");

        inlineKeyboardButtonList.add(inlineKeyboardButton);
        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtonList));

        return inlineKeyboardMarkup;
    }


}
