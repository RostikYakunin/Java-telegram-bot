package com.example.javabot.tools;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class BotKeyboard {
    public ReplyKeyboardMarkup getKeyboard() {
        // options for keyboard
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
}
