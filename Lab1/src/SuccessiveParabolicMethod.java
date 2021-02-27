import java.util.function.Function;

public class SuccessiveParabolicMethod extends AbstractMethod  {

    SuccessiveParabolicMethod(double eps, double start, double end, Function<Double, Double> f) {
        super(eps, start, end, f);
    }

    private Point start_value(Point left, Point right) {
        Point middle = new Point(start + (end - start) / 2);
        while (! (middle.compareToY(left) < 0 && middle.compareToY(right) < 0) ) {
            middle.setX(start + (Math.random() * (end - start)));
        }
        return middle;
    }

    public static double findMinDot(Point left, Point middle, Point right) {
        double a1 = (middle.getY() - left.getY()) / (middle.getX() - left.getX());
        double a2 = ((right.getY() - left.getY()) / (right.getX() - left.getX()) - a1) / (right.getX() - middle.getX());
        return (left.getX() + middle.getX() - a1 / a2) / 2;
    }

    @Override
    public double findMin() {
        Point left = new Point(start);
        Point right = new Point(end);
        Point middle = start_value(left, right);
        Point predMin = new Point(Math.max(start, end));
        while (true) {
            Point minDot = new Point(findMinDot(left, middle, right));
            if (minDot.compareToX(predMin) == 0) {
                return minDot.getX();
            }
            if (minDot.compareToX(left) >= 0 && middle.compareToX(minDot) >= 0) {
                if (minDot.compareToY(middle) <= 0) {
                    right.set(middle);
                    middle.set(minDot);
                } else {
                    left.set(minDot);
                }
            } else {
                if (minDot.compareToY(middle) < 0) {
                    left.set(middle);
                    middle.set(minDot);
                } else {
                    right.set(minDot);
                }
            }
            predMin.set(minDot);
        }
    }
}
