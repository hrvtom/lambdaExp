package lambdaExp;

import static java.lang.System.out;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

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
		out.println(String.format("First Name: %s Last Name: %s", firstName, lastName));
	}

	public static void main(String[] args) {

		// Method references
		out.println(process("Hello World LowerCase!", str -> str.toLowerCase()));

		// with method reference
		// since.toLowerCase is instance method
		// String::toLowerCase() turns into lambda expression str -> str.toLowerCase()
		// so this can be written liek this too
		out.println("With method reference");
		out.println(process("Hello World LowerCase!", String::toLowerCase));
		out.println();
		out.println(process2("Hello World substring!", 5, (str, i) -> str.substring(i)));
		out.println("With method reference");
		out.println(process2("Hello World substring!", 5, String::substring));
		out.println();

		out.println("reference using local variable");
		final String prefix = "Prefix   ";
		// something like this is possible too, use instance of the local variable
		out.println(process("Hello World with String instance", str -> {
//			prefix = "MR  "; // local variable can't be changed here,it must be final of effectively final
			return prefix.concat(str);
		}));
		out.println("With method reference");
		out.println(process("Hello World with String instance", prefix::concat));
		out.println();

		String[] names = { "g. Ivan", "gsp. Tea", "gsp. Marta" };
		String[] names2 = { "g. Stipo", "gsp. Ana", "g. Ferdo" };
		String[] names3 = { "g. stipo", "gsp. Ana", "g. Ferdo" };
		String[] names4 = { "g. Ivan", "gsp. tea", "gsp. Marta" };
		Arrays.sort(names, new Comparator<String>() {

			@Override
			public int compare(String name1, String name2) {
				return name1.split(" ")[1].compareTo(name2.split(" ")[1]);
			}
		});
		out.println(Arrays.toString(names));
		out.println("With lambda expression");
		Arrays.sort(names2, (name1, name2) -> name1.split(" ")[1].compareTo(name2.split(" ")[1]));
		out.println(Arrays.toString(names2));
		out.println("Wtih JAVA8 there is Comparator");
		Arrays.sort(names3, Comparator.comparing(name -> name.split(" ")[1]));
		out.println(Arrays.toString(names3));
		Arrays.sort(names4, Comparator.comparing(Person::firstName));
		out.println(Arrays.toString(names4));
		out.println("JAVA8 Comparator has a reverse method");
		Arrays.sort(names4, Comparator.comparing(Person::firstName).reversed());
		out.println(Arrays.toString(names4));
		out.println();

		Person a = new Person();
		Person b = new Person();
		a.print();
		b.print();

		// Reference to a instance method of the particular object, instance method will be executed on that
		// particular object (a in this case), not on object instance method is call from (b in this case)

		out.println();
		b.changeName(a::getName);
		a.print();
		b.print();

	}

	public static String firstName(String name) {
		return name.split(" ")[1];
	}

	// BiFunctional interface - accepts two arguments and returns result
	private static String process2(String string, int i, BiFunction<String, Integer, String> processor) {
		return processor.apply(string, i);
	}

	// functional interface - accepts one argument and returns result
	private static String process(String str, Function<String, String> processor) {

		return processor.apply(str);
	}

}
