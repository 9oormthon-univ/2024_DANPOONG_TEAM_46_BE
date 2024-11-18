package com.goormthon.bookduchilseong.domain.book.entity.book.entity;

public enum ReadStatus {
    NOT_STARTED("독서 전"),
    IN_PROGRESS("독서 시작"),
    COMPLETED("독서 완료");

    private final String description;

    ReadStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
