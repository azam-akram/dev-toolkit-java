abstract class AbstractVehiclePartFactory {

    private static CarPartFactory carPartFactory;

    private static TruckPartFactory truckPartFactory;

    public static AbstractVehiclePartFactory getFactory(VehicleType type) {

        switch (type) {

            case CAR:
                if (carPartFactory == null) {
                    synchronized (CarPartFactory.class) {
                        carPartFactory = new CarPartFactory();
                    }
                }
                return carPartFactory;

            case TRUCK:
                if (truckPartFactory == null) {
                    synchronized (TruckPartFactory.class) {
                        truckPartFactory = new TruckPartFactory();
                    }
                }
                return truckPartFactory;

            default:
                return null;
        }
    }

    public abstract Wheel createWheel();

    public abstract Steering createSteering();
}
