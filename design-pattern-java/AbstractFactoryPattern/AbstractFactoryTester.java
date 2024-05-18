public class AbstractFactoryTester {

    public static void main (String[] args) {

        AbstractVehiclePartFactory carFactory = AbstractVehiclePartFactory.getFactory(VehicleType.CAR);
        if (carFactory != null) {
            Wheel carWheel = carFactory.createWheel();
            carWheel.printWheeleModel();

            Steering carSteering = carFactory.createSteering();
            carSteering.printSteeringModel();
        }

        AbstractVehiclePartFactory truckFactory = AbstractVehiclePartFactory.getFactory(VehicleType.TRUCK);
        if (truckFactory != null) {
            Wheel truckWheel = truckFactory.createWheel();
            truckWheel.printWheeleModel();

            Steering truckSteering = truckFactory.createSteering();
            truckSteering.printSteeringModel();
        }
    }
}