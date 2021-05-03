import java.util.Random;
import java.util.Vector;

/**
 * This class simulates activity inside of a 3-story parking garage at a grocery store.
 */
public class ParkingGarage{
    // 2D array representing the garage itself
    @SuppressWarnings("unchecked")
    private ArrayBag<Car>[] parkingGarage = (ArrayBag<Car>[]) new ArrayBag[3];

    // Queue containing cars waiting to enter the garage
    private QueueInterface<Car> entryLine = new LinkedQueue<Car>();

    // Variables for performance tracking
    private int numberOfCars; // the number of cars that parked during the simulation
    private int totalTimeWaited; // the total number of clock ticks cars waited in queue before parking
    private int totalArrivals; // the number of cars that arrived at the garage, regardless of whether or not they parked
    private int totalDepartures; // the number of cars that have left the garage
    private int runningTally; // keeps track of the total number of cars total, in the parking garage at any time.

    public ParkingGarage(){
        for (int i = 0; i < parkingGarage.length; i++)
        {
            parkingGarage[i] = new ArrayBag<>(10);
            // creates a new ArrayBag for each index of the parkingGarage
        }
        clearGarage();
    } // end constructor

    public void simulate(int duration, double arrivalProb){
        Random rand = new Random();
        for (int time = 0; time <= duration; time++){
            if (rand.nextFloat() < 0.3){ //chance a car leaves given that there's a car in the garage
                int level = randomLevel();
                if (level != -1){
                    Car departingCar = parkingGarage[level].randRemove();
                    System.out.println("Car number " + departingCar.getCarNumber() + ", has left floor " +
                            level + " of the parking garage.");
                    totalDepartures++;
                    runningTally--;
                }
            }
            if (rand.nextFloat() < arrivalProb){
                Car newCar = new Car(time, totalArrivals);
                totalArrivals++;
                int nextSpot = nextAvailableLocation();
                if (nextSpot != -1){
                    if (entryLine.isEmpty())
                    // if the waiting line to enter the parking garage is empty,
                    // the car does not have to enter the queue, and can just go to
                    // a parking spot
                    {
                        parkingGarage[nextSpot].add(newCar);
                        System.out.println("Car number " + newCar.getCarNumber() + ", has parked on floor "
                                + nextSpot + " of the parking garage.");
                    }
                    else
                    // the waiting line to enter the parking garage is not empty,
                    // cars wishing to enter must first get into the waiting queue
                    // and cars that were already in the queue get to park
                    {
                        Car enteringCar = entryLine.dequeue();
                        parkingGarage[nextSpot].add(enteringCar);
                        System.out.println("Car number " + enteringCar.getCarNumber() + ", has parked on floor "+
                                nextSpot + " of the parking garage after waiting " +
                                (time - enteringCar.getArrivalTime()) + " ticks.");
                        totalTimeWaited += time - enteringCar.getArrivalTime();
                        entryLine.enqueue(newCar);
                        System.out.println("Car number " + newCar.getCarNumber() + ", has entered the waiting queue.");
                    }
                    numberOfCars++;
                    runningTally++;
                }
                else {
                    // if all the parking floors are occupied, the car wishing
                    // to enter the parking garage must get in the waiting queue
                    entryLine.enqueue(newCar);
                    System.out.println("Car number " + newCar.getCarNumber() + ", has entered the waiting queue.");

                }
            }
        }
        displayResults();
    } // end simulate

    // gets the next available floor of the parking garage
    // i.e. returns a floor that is NOT full or -1 if all are full
    public int nextAvailableLocation(){
        for (int i = 0; i < parkingGarage.length; i++){
            if (!parkingGarage[i].isArrayFull()){
                return i;
            }
        }
        return -1;
    }

    // returns a random floor that is NOT full of the parking garage
    public int randomLevel(){
        Vector<Integer> occupiedFloors = new Vector<>();
        for (int i = 0; i < parkingGarage.length; i++)
        {
            if (!parkingGarage[i].isEmpty()){
                occupiedFloors.add(i);
            }
            // finds the floors that are not empty
        }
        Random rand = new Random();
        if (occupiedFloors.size() > 0){
            return occupiedFloors.get(rand.nextInt(occupiedFloors.size()));
        } // returns a random floor of the parkingGarage
        else
        // if all the floors are full
        {
            return -1;
        }
    }

    /**
     * Prints out relevant statistics about the simulation.
     */
    public void displayResults(){
        float averageWait = (float)totalTimeWaited / (float)numberOfCars;
        System.out.println("Total number of cars parked: " + numberOfCars);
        System.out.println("Number of cars currently in the garage: " + runningTally);
        System.out.println("Average wait time: " + averageWait + " ticks");
        System.out.println("Cars waiting to get in: " + countCars(entryLine));
        System.out.println("Cars that have entered and left the garage: " + totalDepartures);
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
        totalDepartures = 0;
        for (int i = 0; i < parkingGarage.length; i++){
            parkingGarage[i].clear();
        }
        entryLine.clear();
    }
}
