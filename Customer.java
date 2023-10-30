import java.util.function.Supplier;

//The Customer class that defines the customer served
class Customer {

    //To show which customer it is
    private final int customerID;
    private final double arrivalTimes;
    private final Supplier<Double> serviceTimes;
    private final double startTime;

    //Create a customer object
    Customer(int customerID,
             double arrivalTimes,
             Supplier<Double> serviceTimes,
             double startTime) {
        this.customerID = customerID;
        this.arrivalTimes = arrivalTimes;
        this.serviceTimes = serviceTimes;
        this.startTime = startTime;
    }

    public Customer updateStartTime(double startTime) {
        return new Customer(this.customerID,
                            this.arrivalTimes,
                            this.serviceTimes,
                            startTime);
    }

    public double getStartTime() {
        return this.startTime;
    }

    public double getServiceTime() {
        return this.serviceTimes.get();
    }

    public double getArrivalTime() {
        return this.arrivalTimes;
    }

    // To return the customer ID
    public int getCustomerID() {
        return this.customerID;
    }
}
