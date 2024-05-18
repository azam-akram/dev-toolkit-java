public class MySingletonClass {

    private static MySingletonClass ourInstance;

    public static MySingletonClass getInstance() {

        if (ourInstance == null) {
            synchronized (MySingletonClass.class) {
                ourInstance = new MySingletonClass();
            }
        }
        return ourInstance;
    }

    private MySingletonClass() {

        if (ourInstance != null) {
            throw new RuntimeException("Instance is already created, you get fetch the instance by MySingletonClass.getInstance");
        }
    }
}