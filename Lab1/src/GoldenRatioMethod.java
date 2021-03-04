import java.util.function.Function;

public class GoldenRatioMethod extends AbstractMethod {
    private static final double INV_GOLDEN_RATIO = (Math.sqrt(5) - 1) / 2;

    public GoldenRatioMethod(String name, double eps, double start, double end, Function<Double, Double> f) {
        super(name, eps, start, end, f);
    }

    @Override
    public double findMin() {
        double l = start;
        double r = end;
        Point x1 = new Point(l + (1 - INV_GOLDEN_RATIO) * (r - l));
        Point x2 = new Point(l + INV_GOLDEN_RATIO * (r - l));
        while (compare(r - l, eps) > 0) {
            if (compare(x1.getY(), x2.getY()) <= 0) {
                r = x2.getX();
                x2 = x1;
                x1 = new Point(r - INV_GOLDEN_RATIO * (r - l));
            } else {
                l = x1.getX();
                x1 = x2;
                x2 = new Point(l + INV_GOLDEN_RATIO * (r - l));
            }
        }
        return (x1.getX() + x2.getX()) / 2;
    }
}
