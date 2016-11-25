package jp.co.fizzbuzz;

public class FizzBuzz {

	public static void main(String[] args) {
		for (int i = 1; i <= 100; i++) {
			System.out.println(fizzBuzz(i));
		}
	}

	//割り切れたときのFizzBuzzメソッド
	public static String fizzBuzz(int i) {
		if (i  % 15 == 0) {
			return "FizzBuzz";
		}
		if (i % 5 == 0) {
			return "Buzz";
		}
		if (i % 3 == 0) {
			return "Fizz";
		}
		return String.valueOf(i);
	}
}
