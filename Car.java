public class Car
{
    private int carNumber;
    private int arrivalTime;
    private int transactionTime;

    public Car()
    {
        carNumber = 0;
        arrivalTime = 0;
        transactionTime = 0;
    } // end default constructor

    public Car(int carNumber, int arrivalTime, int transactionTime)
    {
        this.carNumber = carNumber;
        this.arrivalTime = arrivalTime;
        this.transactionTime = transactionTime;
    } // end constructor

    /**
     * Gets the arrival time of the car
     * @return  The arrival time
     */
    public int getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Gets the car number
     * @return  The car number
     */
    public int getCarNumber() {
        return carNumber;
    }

    /**
     * Gets the transaction time of the car
     * @return  The transaction time
     */
    public int getTransactionTime() {
        return transactionTime;
    }
} // end Car
