package com.example.demo.exceptions;

import net.bytebuddy.implementation.bind.annotation.Super;

public class LoginFailedException extends Exception {

	public LoginFailedException() {
		super("Login Failed! Please check your Email & Password.");
	}
}
