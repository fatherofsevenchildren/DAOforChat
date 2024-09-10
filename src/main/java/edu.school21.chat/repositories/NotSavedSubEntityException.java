package edu.school21.chat.repositories;

public class NotSavedSubEntityException extends Exception {
    public NotSavedSubEntityException(String s) {
        super(s);
    }
}