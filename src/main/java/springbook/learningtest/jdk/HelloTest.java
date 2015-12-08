package springbook.learningtest.jdk;

import java.lang.reflect.Proxy;

import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class HelloTest {
	
	Hello proxiedHello = (Hello) Proxy.newProxyInstance(
			getClass().getClassLoader(), 
			new Class[]{Hello.class}, 
			new UppercaseHandler(new HelloTarget()));
	
	@Test
	public void simpleProxy(){
		Hello hello = new HelloTarget();
		assertThat(hello.sayHello("Toby"), is("Hello Toby"));
		assertThat(hello.sayHi("Toby"), is("Hi Toby"));
		assertThat(hello.sayThankYou("Toby"), is("Thank You Toby"));
		
		Hello proxyHello = new HelloUppercase(new HelloTarget());
		assertThat(proxyHello.sayHello("Toby"), is("HELLO TOBY"));
		assertThat(proxyHello.sayHi("TOBY"), is("HI TOBY"));
		assertThat(proxyHello.sayThankYou("Toby"), is("THANK YOU TOBY"));
	}
	
	
}
