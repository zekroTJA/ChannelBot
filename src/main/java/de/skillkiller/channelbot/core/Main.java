package de.skillkiller.channelbot.core;

import de.skillkiller.channelbot.commands.CommandHandler;
import de.skillkiller.channelbot.commands.CreateAutoChannel;
import de.skillkiller.channelbot.listeners.LSTVoice;
import de.skillkiller.channelbot.util.FileManager;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.io.IOException;

/**
 * Created by Skillkiller on 25.06.2017.
 */
public class Main {

    static JDA bot;

    public static String commandPrefix;

    public static void main(String[] args) {
        FileManager config = new FileManager("", "config.txt");
        try {
            config.loadFile();
            commandPrefix = config.get("commandPrefix");
            if (commandPrefix == null) {
                commandPrefix = ".";
                config.set("commandPrefix", commandPrefix);
            }



            if (config.get("token") == null || config.get("token").equals("Token angeben")) {
                config.set("token", "Token angeben");
                config.saveFile();
            }

            JDABuilder botBuilder = new JDABuilder(AccountType.BOT);
            botBuilder.setToken(config.get("token"));
            botBuilder.setAutoReconnect(true);
            botBuilder.setStatus(OnlineStatus.ONLINE);
            botBuilder.setToken(config.get("token"));

            botBuilder.addEventListener(new CommandHandler());
            botBuilder.addEventListener(new LSTVoice());

            bot = botBuilder.buildBlocking();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LoginException e) {
            e.printStackTrace();
        } catch (RateLimitedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        CommandHandler.commands.put("createAutoChannel", new CreateAutoChannel());
    }

}