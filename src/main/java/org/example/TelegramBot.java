package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class TelegramBot extends TelegramLongPollingBot
{
    public static final String BOT_TOKEN = "6145779010:AAG1GvFomfoKkGZhRTc5tU6qGZfanUlLURU";
    public static final String USER_NAME = "strgazer_bot";
    public static final String
            URI = "https://api.nasa.gov/planetary/apod?api_key=dtivni1k4Heq3hcWVpfC7Y1FakLqxLR65BJnohZl";

    public static long chat_id;

    @Override
    public String getBotUsername()
    {
        return USER_NAME;
    }

    public TelegramBot() throws TelegramApiException
    {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

        try
        {
            botsApi.registerBot(this);
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public String getBotToken()
    {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update)
    {
        if (update.hasMessage())
        {
            chat_id = update.getMessage().getChatId();
            switch (update.getMessage().getText())
            {
                case "/help":
                    sendMessage("Hi, I'm a NASA bot! I send links to pictures on request.\n" +
                            "                            \"Just a reminder that the pictures on the NASA website are updated once a day.");
                    break;
                case "/give":
                    try
                    {
                        sendMessage(Utils.getUrl(URI));
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                    break;
                case "/give_full_info":
                    try
                    {
                        sendMessage(Utils.getResponse(URI));
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    sendMessage("Invalid command :(");
            }
        }
    }

    private void sendMessage(String messageText)
    {
        SendMessage message = new SendMessage();
        message.setChatId(chat_id);
        message.setText(messageText);
        try
        {
            execute(message);
        }
        catch (TelegramApiException e)
        {
            e.printStackTrace();
        }
    }
}
