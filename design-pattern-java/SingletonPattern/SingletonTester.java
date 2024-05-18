public class SingletonTester {

    public static void main (String[] args) {

        MySingletonClass mySingletonClass1 = MySingletonClass.getInstance();
        MySingletonClass mySingletonClass2 = MySingletonClass.getInstance();

        System.out.println("mySingletonClass1: " + mySingletonClass1.hashCode());
        System.out.println("mySingletonClass2: " + mySingletonClass2.hashCode());

    }
}