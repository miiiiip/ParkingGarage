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

    // main constructor
    public ParkingGarage() {
        ArrayBag<Car> f1 = new ArrayBag<Car>(10);
        ArrayBag<Car> f2 = new ArrayBag<Car>(10);
        ArrayBag<Car> f3 = new ArrayBag<Car>(10);
        parkingGarage[0] = f1;
        parkingGarage[1] = f2;
        parkingGarage[2] = f3;
    }

    /**
     * Simulates the activity of a three-story parking garage
     * @param duration the Integer number of times the main loop should run
     * @param arrivalProb a double representing the probability that a new car arrives during an iteration
     */
    public void simulate(int duration, double arrivalProb){
        Random rand = new Random();
        for (int time = 0; time <= duration; time++){ // runs until time reaches duration
            if (rand.nextFloat() < 0.2){ //chance a car leaves given that there's a car in the garage
                int level = randomLevel();
                if (level != -1){ // if there exists at least one level with cars on it, randomly remove a car from
                                  // one of those levels
                    parkingGarage[level].randRemove();
                } // end if
            } // end if
            if (rand.nextFloat() < arrivalProb){  // if a random float number is less than the specified probability
                                                  // a new car arrives
                Car newCar = new Car(time);
                totalArrivals++;  // track total number of arrivals
                int nextSpot = nextAvailableLocation();
                if (nextSpot != -1){ // if there exists an empty spot
                    if (entryLine.isEmpty()){ // and there are no cars waiting to get in
                        parkingGarage[nextSpot].add(newCar); // add the new car at this empty spot
                    } // end if
                    else{
                        Car enteringCar = entryLine.dequeue(); // otherwise add waiting car to the lot
                        parkingGarage[nextSpot].add(enteringCar);
                        totalTimeWaited += time - enteringCar.getArrivalTime();
                        entryLine.enqueue(newCar); // and enqueue the new arrival
                    } // end else
                    numberOfCars++;
                } // end if
                else {
                    entryLine.enqueue(newCar); // if there are no spots available, the new arrival goes straight
                                               // to the waiting queue
                } // end else
            } // end if
        } // end for loop
        displayResults(); // print results of the simulation
    } // end simulate

    /**
     * It it exists, finds and returns the floor with open space closest to the entrance
     * @return the Integer index of the first floor with availability, -1 if no such floor exists
     */
    public int nextAvailableLocation(){
        for (int i = 0; i < parkingGarage.length; i++){
            if (!parkingGarage[i].isArrayFull()){ // if a floor isn't completely full, return it's index
                return i;
            } // end if
        } // end for
        return -1; // if there's no space, return -1
    } // end nextAvailableLocation

    /**
     * Randomly selects a level of the parking garage with at least one car on it.
     * @return
     */
    public int randomLevel(){
        Vector<Integer> occupiedFloors = new Vector<>(); // creates an empty vector
        for (int i = 0; i < parkingGarage.length; i++){
            if (!parkingGarage[i].isEmpty()){
                occupiedFloors.add(i); // if there's a car on floor i, add it to the vector
            } // end if
        } // end for
        Random rand = new Random();
        if (occupiedFloors.size() > 0){ // if there is at least one occupied floor
            return occupiedFloors.get(rand.nextInt(occupiedFloors.size())); // return a random floor from the vector of occupied floors
        } // end if
        else {
            return -1; // if the garage is completely empty, return -1
        } // end else
    } // end randomLevel

    /**
     * Prints out relevant statistics about the simulation.
     */
    public void displayResults(){
        System.out.println("Cars parked: " + numberOfCars);
        System.out.println("Cars in garage: " + totalArrivals);
        System.out.println("Average wait time: " + (totalTimeWaited / numberOfCars));
        System.out.println("Cars waiting to get in: " + countCars(entryLine) + "\n");
        for (int i = parkingGarage.length - 1; i > -1; i--){
            System.out.print("[");
            int length = parkingGarage[i].getSize();
            for (int ii = 0; ii < length; ii++){
                System.out.print("0");
            }
            for (int iii = 0; iii < 10 - length; iii++){
                System.out.print("-");
            }
            System.out.println("]");
        }
        System.out.println("\n");
        for (int i = 0; i < parkingGarage.length; i++){
            System.out.println("Number of cars on floor " + (i + 1) + ": " + parkingGarage[i].getSize());
        } // end for
        clearGarage();
    } // end displayResults

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
        } // end while loop
        return counter;
    } // end countCars

    /**
     * Completely resets the garage for a new simulation
     */
    public void clearGarage(){
        numberOfCars = 0;
        totalTimeWaited = 0;
        totalArrivals = 0;
        for (int i = 0; i < parkingGarage.length; i++){
            parkingGarage[i].clear();
        } // end for
        entryLine.clear();
    } // end clearGarage
}
