import java.util.function.Function;

public class FibonacciMethod extends AbstractMethod {


    protected FibonacciMethod(double eps, double start, double end, Function<Double, Double> f) {
        super(eps, start, end, f);
    }

    @Override
    public double findMin() {
        return 0;
    }
}
