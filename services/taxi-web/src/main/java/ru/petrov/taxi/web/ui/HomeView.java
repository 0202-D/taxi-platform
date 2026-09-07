package ru.petrov.taxi.web.ui;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route("")
@PermitAll
public class HomeView extends VerticalLayout {

    public HomeView() {
        add(new H1("Привет, "));
        add(new Button("Выйти", e -> UI.getCurrent().getPage().setLocation("/logout")));
    }
}