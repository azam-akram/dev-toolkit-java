public class SingletonTester {

	public static void main (String[] args) {
		MySingleton mySingleton1 = MySingleton.getInstance();
		MySingleton mySingleton2 = MySingleton.getInstance();

		System.out.println("mySingleton1: " + mySingleton1.hashCode());
		System.out.println("mySingleton2: " + mySingleton2.hashCode());
	}
}