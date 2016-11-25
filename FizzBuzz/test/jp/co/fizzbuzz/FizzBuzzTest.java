package jp.co.fizzbuzz;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class FizzBuzzTest {

	@Test
	public void fizzBuzzに3の倍数を渡した場合Fizzを返すこと() {
		assertThat(FizzBuzz.fizzBuzz(3), is("Fizz"));
		assertEquals("Fizz", FizzBuzz.fizzBuzz(3));
	}

	@Test
	public void fizzBuzzに5の倍数を渡した場合Buzzを返すこと() {
		assertEquals("Buzz", FizzBuzz.fizzBuzz(5));
	}

	@Test
	public void fizzBuzzに3と5の倍数を渡した場合FizzBuzzを返すこと() {
		assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(15));
	}

	@Test
	public void fizzBuzzを返さず出力() {
		assertEquals("1", FizzBuzz.fizzBuzz(1));
	}

	@Test
	public void fizzBuzzに3の倍数の9を渡した場合Fizzを返すこと() {
		assertThat(FizzBuzz.fizzBuzz(9), is("Fizz"));
		assertEquals("Fizz", FizzBuzz.fizzBuzz(9));
	}

	@Test
	public void fizzBuzzに5の倍数20を渡した場合Buzzを返すこと() {
		assertEquals("Buzz", FizzBuzz.fizzBuzz(20));
	}

	@Test
	public void fizzBuzzに3と5の倍数45を渡した場合FizzBuzzを返すこと() {
		assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(45));
	}

	@Test
	public void fizzBuzzを返さず31を出力() {
		assertEquals("31", FizzBuzz.fizzBuzz(31));
	}

	@Test
	public void fizzBuzzに3の倍数の54を渡した場合Fizzを返すこと() {
		assertThat(FizzBuzz.fizzBuzz(9), is("Fizz"));
		assertEquals("Fizz", FizzBuzz.fizzBuzz(54));
	}

	@Test
	public void fizzBuzzに5の倍数50を渡した場合Buzzを返すこと() {
		assertEquals("Buzz", FizzBuzz.fizzBuzz(50));
	}

	@Test
	public void fizzBuzzに3と5の倍数60を渡した場合FizzBuzzを返すこと() {
		assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(15));
	}

	@Test
	public void fizzBuzzを返さず44を出力() {
		assertEquals("44", FizzBuzz.fizzBuzz(44));
	}

	@Test
	public void fizzBuzzに3の倍数の72を渡した場合Fizzを返すこと() {
		assertThat(FizzBuzz.fizzBuzz(72), is("Fizz"));
		assertEquals("Fizz", FizzBuzz.fizzBuzz(72));
	}

	@Test
	public void fizzBuzzに5の倍数85を渡した場合Buzzを返すこと() {
		assertEquals("Buzz", FizzBuzz.fizzBuzz(85));
	}

	@Test
	public void fizzBuzzに3と5の倍数90を渡した場合FizzBuzzを返すこと() {
		assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(90));
	}

	@Test
	public void fizzBuzzを返さず62を出力() {
		assertEquals("62", FizzBuzz.fizzBuzz(62));
	}
}
