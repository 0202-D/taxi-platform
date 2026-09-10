package ru.petrov.monitoring.web.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route("login")
@AnonymousAllowed
public class LoginView extends VerticalLayout {

    public LoginView() {
        setWidthFull();
        setHeightFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        Div container = new Div();
        container.getStyle()
                .set("width", "420px")
                .set("padding", "40px")
                .set("border-radius", "16px")
                .set("box-shadow", "0 8px 30px rgba(0,0,0,0.08)")
                .set("background", "white");

        H1 title = new H1("Monitoring Platform");

        Paragraph description = new Paragraph(
                "Контролируйте доступность ваших сайтов и сервисов"
        );

        Button loginButton = new Button(
                "Войти",
                event -> login(false)
        );

        loginButton.setWidthFull();

        Button registerButton = new Button(
                "Зарегистрироваться",
                event -> login(true)
        );

        registerButton.setWidthFull();

        container.add(
                title,
                description,
                loginButton,
                registerButton
        );

        add(container);
    }

    private void login(boolean registration) {
        UI.getCurrent()
                .getPage()
                .setLocation(
                        registration
                                ? "/oauth2/authorization/keycloak?prompt=create"
                                : "/oauth2/authorization/keycloak"
                );
    }
}