package ru.andronov.services.auth;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import ru.andronov.Constants;
import ru.andronov.model.User;
import ru.andronov.services.utils.ClientMsgSenderService;
import ru.andronov.services.utils.ClientMsgSenderServiceImpl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final Socket socket;

    @SneakyThrows
    @Override
    public User authenticate() {
        ClientMsgSenderService senderService = new ClientMsgSenderServiceImpl(socket.getOutputStream());
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while (true) {
            try {
                senderService.sendMsg("Choose\n" +
                        "1 - to sign in\n" +
                        "2 - to sign up");
                int menuNumber = Integer.parseInt(clientReader.readLine());
                if (menuNumber == Constants.SIGN_IN_MENU_NUMBER) {
                    SignInService signIn = new SignInServiceImpl(senderService, clientReader);
                    User user = signIn.login();
                    senderService.sendMsg(user.getLogin() +" has signed in!");
                    return user;
                }
                if (menuNumber == Constants.SIGN_UP_MENU_NUMBER) {
                    SignUpService signUp = new SignUpServiceImpl(senderService, clientReader);
                    User user = signUp.register();
                    senderService.sendMsg("User with login " + user.getLogin() + " has been signed up!");
                    return user;
                } else {
                    senderService.sendMsg("Wrong menu number " + menuNumber + ". Please try again!");
                }
            } catch (NumberFormatException ex) {
                senderService.sendMsg("Invalid number format. Please try again!");
            }
        }
    }
}
