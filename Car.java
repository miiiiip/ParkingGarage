/**
 * Creates a Car object to use in our simulation.
 */
public class Car {
    private int arrivalTime;
    private int carNumber;

    public Car (int aT, int carNumb){
        this.arrivalTime = aT;
        this.carNumber = carNumb;
    }

    public int getArrivalTime(){
        return this.arrivalTime;
    }

    public int getCarNumber()
    {
        return this.carNumber;
    }
}
