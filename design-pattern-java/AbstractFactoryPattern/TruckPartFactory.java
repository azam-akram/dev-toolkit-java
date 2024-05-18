public class TruckPartFactory extends AbstractVehiclePartFactory {

    @Override
    public Wheel createWheel() {
        return new TruckWheel();
    }

    @Override
    public Steering createSteering() {
        return new TruckSteering();
    }

}
