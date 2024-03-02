package com.example.auth.entity;

public enum Code {
    SUCCESS("Operacja zakończona powidzeniem"),
    A1("Nie udało się zalogować"),
    A2("Użytkownik o tej nazwie już istnieje"),
    A3("Wskazany token jest pusty lub nieważny"),
    A4("Użytkownik o tym emailu już istnieje"),
    A5("Użytkownik o tej nazwie już istnieje"),
    LOGIN_FAILED("login failed"),
    PERMIT("Przyznano dostęp");
    public String label;

    private Code(String label) {
        this.label = label;
    }
}
