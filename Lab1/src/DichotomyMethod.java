import java.util.function.Function;

public class DichotomyMethod extends AbstractMethod {
    private final double delta;

    public DichotomyMethod(String name, double eps, double start, double end, Function<Double, Double> f, double delta) {
        super(name, eps, start, end, f);
        this.delta = delta;
    }

    @Override
    public double findMin() {
        double left = start;
        double right = end;
        while ((right - left) / 2.0 > eps) {
            double x1 = (right + left - delta) / 2.0;
            double x2 = (right + left + delta) / 2.0;

            if (f.apply(x1) <= f.apply(x2)) {
                right = x2;
            } else {
                left = x1;
            }

        }
        return (left + right) / 2.0;
    }
}
