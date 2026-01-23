package ru.yandex.practicum.filmorate.exception;

public class MethodNotImplementedException extends RuntimeException {
	public MethodNotImplementedException() {
		super("Эта функция еще не реализована.");
	}
}
