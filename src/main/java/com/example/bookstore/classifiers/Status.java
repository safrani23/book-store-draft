package com.example.bookstore.classifiers;

public enum Status {

    CREATED("Создан"),
    ACCEPTED("Принят"),
    ASSEMBLY("Сборка"),
    HANDED_FOR_DELIVERY("Передан в доставку"),
    DELIVERED_TO_THE_POINT_OF_ISSUANCE("Доставлен в пункт выдачи"),
    WAITING_FOR_ISSUANCE("Ожидает выдачи"),
    COMPLETE("Завершен");

    private final String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
