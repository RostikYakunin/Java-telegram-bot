package com.example.javabot.service;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
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
    private SendMessage messageForAdmin;
    private SendMessage sendMessageToUser;
    private Message messageFromUser;

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
        sendMessageToUser = new SendMessage();
        messageFromUser = update.getMessage();

        sendMessageToUser.setChatId(String.valueOf(messageFromUser.getChatId()));

        // action in start
        if (messageFromUser.getText().equals("/start")) {
            getGreetingMessageAndEnableKeyboard(sendMessageToUser, messageFromUser);
        }

        // button action list products
        else if (messageFromUser.getText().equals("Каталог товарів")) {
            getReferenceToSite(sendMessageToUser);
        }

        // button action contacts
        else if (messageFromUser.getText().equals("Наші контакти")) {
            getContacts(sendMessageToUser);
        }

        // button action instagram reference
        else if (messageFromUser.getText().equals("Інстаграмм")) {
            getInstagramReference(sendMessageToUser);
        }

        // button action promotions
        else if (messageFromUser.getText().equals("Акції")) {
            getPromotions(sendMessageToUser);
        }

        // user`s input messages
        else {
            sendMessageToAdmin(sendMessageToUser, messageFromUser);
        }

        // only execute
        try {
            execute(sendMessageToUser);

            if (messageForAdmin != null) {
                execute(messageForAdmin);
            }

        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessageToAdmin(SendMessage sendMessage, Message messageFromUser) {
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

        messageForAdmin = new SendMessage();
        messageForAdmin.setChatId(String.valueOf(5699067150L));
        messageForAdmin.setText(text);
    }

    private void getPromotions(SendMessage sendMessage) {
        sendMessage.setText("На даний час акції відсутні");
    }

    private void getInstagramReference(SendMessage sendMessage) {
        String message = "На цій інстаграм сторінці ви знайдете найсвіжіші новини, список товарів та послуг";
        sendMessage.setText(message);
        sendMessage.setReplyMarkup(getInlineKeyboardForInstagram());
    }

    private void getContacts(SendMessage sendMessage) {
        sendMessage.setText("email: fresh.greeneery.ua@gmail.com \n" +
                "моб.тел: +38 095 752 08 25 \n" +
                "Username адміністрації у телеграмм: @Dev_Fresh ");
    }

    private void getReferenceToSite(SendMessage sendMessage) {
        sendMessage.setText("Перейдіть по посиланню вказаному нижче");
        sendMessage.setReplyMarkup(getInlineKeyboardForCatalog());
    }

    private void getGreetingMessageAndEnableKeyboard(SendMessage sendMessage, Message messageFromUser) {
        String helloText = "Привіт " + messageFromUser.getFrom().getFirstName() + " " + " мене зовуть FreshBot і я радий вас привітати))) \n"
                + "На даний час я можу вам надати: \n \n" +
                "1. Каталог товарів \n" +
                "2. Посилання на інстаграм \n" +
                "3. Контакти \n" +
                "4. Акції \n \n";

        helloText += "Якщо є якісь запитання то можешь написати мені, я передам твої запитання та пропозиції до адміністрації, а вони тобі відпишуться" ;

        sendMessage.setText(helloText);
        sendMessage.enableMarkdown(true);
        sendMessage.setReplyMarkup(getKeyboard());
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

    private InlineKeyboardMarkup getInlineKeyboardForCatalog() {
        // new inline keyboard
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        // new inline button
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Перейти до каталогу");
        inlineKeyboardButton.setUrl("https://sites.google.com/view/freshgreenery-pp-ua/");

        // add to list
        inlineKeyboardButtonList.add(inlineKeyboardButton);

        // add to keyboard markup
        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtonList));

        return inlineKeyboardMarkup;
    }

    private InlineKeyboardMarkup getInlineKeyboardForInstagram() {
        // new inline keyboard
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> inlineKeyboardButtonList = new ArrayList<>();

        // new inline button
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Перейти до Instagram");
        inlineKeyboardButton.setUrl("https://www.instagram.com/fresh_greenery.ua");

        // add to list
        inlineKeyboardButtonList.add(inlineKeyboardButton);

        // add to keyboard markup
        inlineKeyboardMarkup.setKeyboard(Collections.singletonList(inlineKeyboardButtonList));

        return inlineKeyboardMarkup;
    }

}
