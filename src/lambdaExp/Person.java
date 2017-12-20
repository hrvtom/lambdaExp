package lambdaExp;

import java.util.Random;

public class Person {

	private String firstName;
	private String lastName;

	public Person() {
		define(this::someFunction);
	}

	private void someFunction() {
		Random rnd = new Random();
		int randomInt = rnd.nextInt(100000);
		System.out.println("random " + randomInt);
		firstName = String.valueOf(randomInt);
		randomInt = rnd.nextInt(100000);
		System.out.println("random " + randomInt);
		lastName = String.valueOf(randomInt);
	}

	public String getName() {
		return this.firstName;
	}

	private void define(DefineFunction function) {
		function.apply();
	}

	public void changeName(NameFunction nameFunction) {
		this.firstName = nameFunction.apply();
	}

	public void print() {
		System.out.println(String.format("First Name: %s Last Name: %s", firstName, lastName));
	}

	public static void main(String[] args) {

		Person a = new Person();
		Person b = new Person();
		a.print();
		b.print();

		// Reference to a instance method of the particular object, instance method will be executed on that
		// particular object (a in this case), not on object instance method is call from (b in this case)

		System.out.println();
		b.changeName(a::getName);
		a.print();
		b.print();

	}

}
