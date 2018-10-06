package com.capgemini.customerapp.service.exception;

public class AuthenticationFailedException extends Exception {
public AuthenticationFailedException(String message) {
	super(message);
}
}
