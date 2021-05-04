import java.util.Random;
import java.util.Vector;

/**
 * This class simulates activity inside of a 3-story parking garage at a grocery store.
 */
public class ParkingGarage{
    // array representing the garage itself
    @SuppressWarnings("unchecked")
    private ArrayBag<Car>[] parkingGarage = (ArrayBag<Car>[]) new ArrayBag[3];

    // Queue containing cars waiting to enter the garage
    private QueueInterface<Car> entryLine = new LinkedQueue<Car>();

    // Variables for performance tracking
    private int numberOfCars; // the number of cars that parked during the simulation
    private int totalTimeWaited; // the total number of clock ticks cars waited in queue before parking
    private int totalArrivals; // the number of cars that arrived at the garage, regardless of whether or not they parked

    public ParkingGarage(){
        ArrayBag<Car> f1 = new ArrayBag<Car>(10);
        ArrayBag<Car> f2 = new ArrayBag<Car>(10);
        ArrayBag<Car> f3 = new ArrayBag<Car>(10);
        parkingGarage[0] = f1;
        parkingGarage[1] = f2;
        parkingGarage[2] = f3;
    }

    public void simulate(int duration, double arrivalProb){
        Random rand = new Random();
        for (int time = 0; time <= duration; time++){
            if (rand.nextFloat() < 0.3){ //chance a car leaves given that there's a car in the garage
                int level = randomLevel();
                if (level != -1){
                    Car departingCar = parkingGarage[level].randRemove();
                }
            }
            if (rand.nextFloat() < arrivalProb){
                Car newCar = new Car(time);
                totalArrivals++;
                int nextSpot = nextAvailableLocation();
                if (nextSpot != -1){
                    if (entryLine.isEmpty()){
                        parkingGarage[nextSpot].add(newCar);
                    }
                    else{
                        Car enteringCar = entryLine.dequeue();
                        parkingGarage[nextSpot].add(enteringCar);
                        totalTimeWaited += time - enteringCar.getArrivalTime();
                        entryLine.enqueue(newCar);
                    }
                    numberOfCars++;
                }
                else {
                    entryLine.enqueue(newCar);
                }
            }
        }
        displayResults();
    }

    public int nextAvailableLocation(){
        for (int i = 0; i < parkingGarage.length; i++){
            if (!parkingGarage[i].isArrayFull()){
                return i;
            }
        }
        return -1;
    }

    public int randomLevel(){
        Vector<Integer> occupiedFloors = new Vector<>();
        for (int i = 0; i < parkingGarage.length; i++){
            if (!parkingGarage[i].isEmpty()){
                occupiedFloors.add(i);
            }
        }
        Random rand = new Random();
        if (occupiedFloors.size() > 0){
            return occupiedFloors.get(rand.nextInt(occupiedFloors.size()));
        }
        else {
            return -1;
        }
    }

    /**
     * Prints out relevant statistics about the simulation.
     */
    public void displayResults(){
        System.out.println("Cars parked: " + numberOfCars);
        System.out.println("Cars in garage: " + totalArrivals);
        System.out.println("Average wait time: " + (totalTimeWaited / numberOfCars));
        System.out.println("Cars waiting to get in: " + countCars(entryLine));
        for (int i = 0; i < parkingGarage.length; i++){
            System.out.println("Number of cars on floor " + i + ": " + parkingGarage[i].getSize());
        }
        clearGarage();
    }

    /**
     * retrieves the number of cars in a given queue
     * @param carQueue the queueinterface implementing object whose contents will be counted
     * @return an int equal to the number of cars in the queue
     */
    public int countCars(QueueInterface<Car> carQueue){
        int counter = 0;
        while (!carQueue.isEmpty()){
            counter++;
            carQueue.dequeue();
        }
        return counter;
    }

    /**
     * Completely resets the garage for a new simulation
     */
    public void clearGarage(){
        numberOfCars = 0;
        totalTimeWaited = 0;
        totalArrivals = 0;
        for (int i = 0; i < parkingGarage.length; i++){
            parkingGarage[i].clear();
        }
        entryLine.clear();
    }
}
