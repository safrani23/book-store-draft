package com.example.bookstore.models;

public enum OrderStatus {

    CREATED("Создан"),
    ACCEPTED("Принят"),
    ASSEMBLY("Сборка"),
    HANDED_FOR_DELIVERY("Передан в доставку"),
    DELIVERED_TO_THE_POINT_OF_ISSUANCE("Доставлен в пункт выдачи"),
    WAITING_FOR_ISSUANCE("Ожидает выдачи"),
    COMPLETE("Завершен");

    private final String title;

    OrderStatus(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
}
