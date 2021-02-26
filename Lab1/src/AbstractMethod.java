import java.util.function.Function;

public abstract class AbstractMethod implements OptimizationMethod {
    private static final double COMPARE_PRECISION = 1e-10;
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

    protected int compare(double x, double y) {
        if (x + COMPARE_PRECISION < y) {
            return -1;
        }
        if (x > y + COMPARE_PRECISION) {
            return 1;
        }
        return 0;
    }

    public abstract double findMin();
}
