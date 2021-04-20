public class ParkingGarage
{
    //ZEKE BRANCH
        private QueueInterface<Car> enteringQueue;
        private QueueInterface<Car> leavingQueue;
        private BagInterface<Car> floorOne;
        private BagInterface<Car> floorTwo;
        private BagInterface<Car> floorThree;
        private int enteredQueueSize; // the amount of cars that have entered the entering queue
        private int departureQueueSize; // the amount of cars that have entered the leaving queue
        private int successfulEntries; // the amount of cars that have successfully entered the parking garage
        private int successfulDepartures; // the amount of cars that have successfully left the parking garage
        private int floorOneEntries;
        private int floorTwoEntries;
        private int floorThreeEntries;

        public ParkingGarage()
        {
            enteringQueue = new LinkedQueue<Car>();
            leavingQueue = new LinkedQueue<Car>();
            floorOne = new ArrayBag<>(5);
            floorTwo = new ArrayBag<>(5);
            floorThree = new ArrayBag<>(5);
            reset();
        } // end default constructor

        /**
         * Simulates a parking garage with two floors, with a fixed number of
         *  available spots.
         * @param duration  The number of simulated minutes.
         * @param spawnProbability    A real number between 0 and 1,
         *                              and the probability that a car
         *                              spawns at a given time.
         * @param maxTransactionTime    The longest amount of time
         *                              that a car can take to
         *                              stay in a spot.
         */
        public void simulate(int duration, double spawnProbability, int maxTransactionTime)
        {
            int fOneTTL = 0;
            int fTwoTTL = 0;
            int fThreeTTL = 0;
            for (int clock = 0; clock < duration; clock++)
            {
                // creates a car
                if ((Math.random() < spawnProbability))
                {
                    enteredQueueSize++;
                    int transactionTime = (int) (Math.random() * maxTransactionTime + 1);
                    Car nextArrival = new Car(enteredQueueSize, clock, transactionTime);
                    enteringQueue.enqueue(nextArrival);
                } // end if

                while(!leavingQueue.isEmpty())
                {
                    Car exitCar = leavingQueue.dequeue();
                    System.out.println("Car " + exitCar.getCarNumber() + " leaves the parking garage at time " + clock);
                    successfulDepartures++;
                } // end while

                // checks to see if any of the floors are full.  If there
                // is an open spot on a given floor, the next car in the
                // entering queue will be added to that floor, otherwise,
                // nothing happens.
                if(!floorOne.isFull() && !enteringQueue.isEmpty())
                {
                    Car nextCar = enteringQueue.dequeue();
                    floorOne.add(nextCar);
                    System.out.println("Car " + nextCar.getCarNumber() + " parks on floorOne.");
                    floorOneEntries++;
                }
                else if(!floorTwo.isFull() && !enteringQueue.isEmpty())
                {
                    Car nextCar = enteringQueue.dequeue();
                    floorTwo.add(nextCar);
                    System.out.println("Car " + nextCar.getCarNumber() + " parks on floorTwo.");
                    floorTwoEntries++;
                }
                else if(!floorThree.isFull() && !enteringQueue.isEmpty())
                {
                    Car nextCar = enteringQueue.dequeue();
                    floorThree.add(nextCar);
                    System.out.println("Car " + nextCar.getCarNumber() + " parks on floorThree.");
                    floorThreeEntries++;
                }
                else
                    continue;
                // end if

                if (fOneTTL > 0)
                    fOneTTL--;
                else if(!floorOne.isEmpty())
                {
                    for(int i = 0; i < floorOne.getCurrentSize(); i++)
                    {
                        // checks to see if any cars should leave based on
                        // their transaction time
                        Car car = floorOne.getObject(i);
                        fOneTTL = car.getTransactionTime() - 1;
                        if(car.getTransactionTime() == (fOneTTL - car.getArrivalTime()))
                        {
                            leavingQueue.enqueue(floorOne.replace(car, null));
                            System.out.println("Car " + car.getCarNumber() + " enters the leavingQueue from floorOne at time " + clock);
                            departureQueueSize++;
                        } // end if
                    } // end for
                }
                if(fTwoTTL > 0)
                    fTwoTTL--;
                else if(!floorTwo.isEmpty())
                {
                    for(int i = 0; i < floorTwo.getCurrentSize(); i++)
                    {
                        // checks to see if any cars should leave based on
                        // their transaction time
                        Car car = floorTwo.getObject(i);
                        fTwoTTL = car.getTransactionTime() - 1;
                        if(car.getTransactionTime() == (fTwoTTL - car.getArrivalTime()))
                        {
                            leavingQueue.enqueue(floorTwo.replace(car, null));
                            System.out.println("Car " + car.getCarNumber() + " enters the leavingQueue from floorTwo at time " + clock);
                            departureQueueSize++;
                        } // end if
                    } // end for
                }
                if(fThreeTTL > 0)
                    fThreeTTL--;
                else if(!floorThree.isEmpty())
                {
                    for(int i = 0; i < floorThree.getCurrentSize(); i++)
                    {
                        // checks to see if any cars should leave based on
                        // their transaction time
                        Car car = floorThree.getObject(i);
                        fThreeTTL = car.getTransactionTime() - 1;
                        if(car.getTransactionTime() == (fThreeTTL - car.getArrivalTime()))
                        {
                            leavingQueue.enqueue(floorThree.replace(car, null));
                            System.out.println("Car " + car.getCarNumber() + " enters the leavingQueue from floorThree at time " + clock);
                            departureQueueSize++;
                        } // end if
                    } // end for
                } // end if
            } // end for
        } // end simulate

        /**
         * Displays summary results of the situation.
         */

        public void displayResults()
        {
            System.out.println();
            System.out.println("Number of arrivals = " + enteredQueueSize);
            System.out.println("Number of departures = " + departureQueueSize);
            int leftToEnter = enteredQueueSize - successfulEntries;
            int leftToLeave = departureQueueSize - successfulDepartures;
            System.out.println("Number of arrivals left = " + leftToEnter);
            System.out.println("Number of departures left = " + leftToLeave);
            System.out.println("There were a total of " + floorOneEntries + " cars that parked on floorOne, " +
                    floorTwoEntries + " cars that parked on floorTwo, and " + floorThreeEntries + " cars that parked on floorThree.");
        } // end displayResults

        /**
         * Initializes the simulation
         */
        public final void reset()
        {
            enteringQueue.clear();
            leavingQueue.clear();
            enteredQueueSize = 0;
            departureQueueSize = 0;
            successfulEntries = 0;
            successfulDepartures = 0;
            floorOneEntries = 0;
            floorTwoEntries = 0;
            floorThreeEntries = 0;
        } // end reset
} // end ParkingGarage
