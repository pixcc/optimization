import java.util.function.Function;

public class DichotomyMethod extends AbstractMethod {
    private final double delta;

    public DichotomyMethod(String name, double eps, double start, double end, Function<Double, Double> f, double delta) {
        super(name, eps, start, end, f);
        this.delta = delta;
    }

    @Override
    public double findMin() {
        Point left = new Point(start);
        Point right = new Point(end);
        while ((right.getX() - left.getX()) / 2.0 > eps) {
            Point x1 = new Point((right.getX() + left.getX() - delta) / 2.0);
            Point x2 = new Point((right.getX() + left.getX() + delta) / 2.0);
            if (x1.getY() <= x2.getY()) {
                right = x2;
            } else {
                left = x1;
            }
        }
        return (left.getX() + right.getX()) / 2.0;
    }
}
