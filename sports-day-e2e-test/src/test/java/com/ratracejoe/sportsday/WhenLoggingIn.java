package com.ratracejoe.sportsday;

import com.ratracejoe.sportsday.actions.SportsDayAuthActions;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SerenityJUnit5Extension.class)
public class WhenLoggingIn {
    String accessToken;
    SportsDayAuthActions authApi;

    @Test
    @DisplayName("User can login to auth controller")
    public void login() {
        authApi.givenUserConnectedToAuth();
        authApi.whenUserLogsIn();
        authApi.thenUserReceivesToken();
    }
}
