public class CarPartFactory extends AbstractVehiclePartFactory {

    @Override
    public Wheel createWheel() {
        return new CarWheel();
    }

    @Override
    public Steering createSteering() {
        return new CarSteering();
    }

}