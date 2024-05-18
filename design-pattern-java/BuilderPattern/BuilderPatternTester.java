public class BuilderPatternTester {

    public static void main (String[] args) {

        DataSource dataSource = new DataSource
            .Builder(1, 2L)
            .optionalInt(3)
            .optionalLong(4L)
            .optionalString("Tester").build();

        
        System.out.println("Mandatory Long Value: " + dataSource.getMandatoryLong());
        System.out.println("Optional Integer Value: " + dataSource.getOptionalInt());
        System.out.println("Optional Long Value: " + dataSource.getOptionalLong());
        System.out.println("Optional String Value: " + dataSource.getOptionalString());
    }
}