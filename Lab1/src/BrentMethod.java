import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BrentMethod extends AbstractMethod {

    private static final double INV_GOLDEN_RATIO = (Math.sqrt(5) - 1) / 2;


    BrentMethod(double eps, double start, double end, Function<Double, Double> f) {
        super(eps, start, end, f);
    }

    private boolean pairwiseDifferent(Point x, Point w, Point v) {
        return x.compareToX(w) != 0 && x.compareToX(v) != 0 && w.compareToX(v) != 0;
    }

    double getMediana(Point ... points) {
        long ans = 0;
        for (Point point : points) {
            ans = ans ^ Double.doubleToRawLongBits(point.getX());
        }
        return Double.longBitsToDouble(ans);
    }

    double lenOX(Point a, Point b) {
        return Math.abs(a.getX() - b.getX());
    }

    Point findMinDot(Point left, Point right) {
        Point x1 = new Point(left.getX() + (1 - INV_GOLDEN_RATIO) * (right.getX() - left.getX()));
        Point x2 = new Point(left.getX() + INV_GOLDEN_RATIO * (right.getX() - left.getX()));
        return x1.compareToX(x2) < 0 ? x1 : x2;
    }

    @Override
    public double findMin() {
        Point left = new Point(start);
        Point right = new Point(end);
        Point middle = new Point((end + start) / 2);
        Point x = new Point(middle);
        Point w = new Point(middle);
        Point v = new Point(middle);
        double pred_len = (end - start) / 2;
        while (left.compareToX(right) != 0) { // критерий сходимости
            boolean isSuccessiveParabolic = false;
            Point u = new Point(x);
            if (pairwiseDifferent(x, w, v)) { // use SuccessiveParabolicMethod
                Point newX = new Point(Math.min(Math.min(x.getX(), w.getX()), v.getX())); // 1
                Point newV = new Point(Math.max(Math.max(x.getX(), w.getX()), v.getX())); // 3
                Point newW = new Point(getMediana(x, w, v, newX, newV));
                u = new Point(SuccessiveParabolicMethod.findMinDot(newX, newV, newW));
                if (u.compareToX(left) > 0 && u.compareToX(right) < 0) {
                    if (pred_len < lenOX(u, x)) {
                        isSuccessiveParabolic = true;
                    }
                }
            }
            if (!isSuccessiveParabolic) {
                if (lenOX(left, x) > lenOX(x, right)) { // use GoldenRatioMethod
                    u = findMinDot(left, x);
                    if (u.compareToY(x) <= 0) {
                        right.set(x);
                    } else {
                        left.set(u);
                    }
                } else {
                    u = findMinDot(x, right);
                    if (u.compareToY(x) <= 0) {
                        left.set(x);
                    } else {
                        right.set(u);
                    }
                }
            }
            pred_len = lenOX(left, right);
            v.set(w);
            w.set(x);
            x.set(u);
        }
        return x.getX();
    }
}
