package ru.petrov.taxi.web.ui;

import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@PageTitle("Login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    public LoginView() {
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        add(new H1("Добро пожаловать"));

        Anchor keycloakLogin = new Anchor("/oauth2/authorization/keycloak", "Войти через Keycloak");
        keycloakLogin.getElement().setAttribute("router-ignore", true);
        add(keycloakLogin);
    }
}