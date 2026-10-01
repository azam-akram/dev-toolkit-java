public class MySingleton {

	private static MySingleton instance;

	public static MySingleton getInstance() {
		if (instance == null) {
			synchronized (MySingleton.class) {
				instance = new MySingleton();
			}
		}
		return instance;
	}

	private MySingleton() {
		if (instance != null) {
			throw new RuntimeException("Instance is already created, you get fetch the instance by MySingletonClass.getInstance");
		}
	}
}