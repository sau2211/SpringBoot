package com.spring.cloud.exceptions;

public class RecordAlreadyPresentException extends RuntimeException{
public RecordAlreadyPresentException(String s)
{
	System.out.println(s);
//super(s);


}
	
}
