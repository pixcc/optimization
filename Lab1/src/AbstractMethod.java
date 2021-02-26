import java.util.function.Function;

public abstract class AbstractMethod implements OptimizationMethod {
    protected final double eps;
    protected final double start;
    protected final double end;
    protected final Function<Double, Double> f;

    protected AbstractMethod(double eps, double start, double end, Function<Double, Double> f) {
        this.eps = eps;
        this.start = start;
        this.end = end;
        this.f = f;
    }

    public abstract double findMin();
}
