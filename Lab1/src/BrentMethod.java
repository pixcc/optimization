import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BrentMethod extends AbstractMethod {

    private static final double INV_GOLDEN_RATIO = (Math.sqrt(5) - 1) / 2;


    BrentMethod(String name, double eps, double start, double end, Function<Double, Double> f) {
        super(name, eps, start, end, f);
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


    Point findMinDot(Point left, Point right) {
        Point x1 = new Point(left.getX() + (1 - INV_GOLDEN_RATIO) * (right.getX() - left.getX()));
        Point x2 = new Point(left.getX() + INV_GOLDEN_RATIO * (right.getX() - left.getX()));
        return x1.compareToY(x2) < 0 ? x1 : x2;
    }

    @Override
    public double findMin() {
        Point left = new Point(start);
        Point right = new Point(end);
        Point x = new Point((end + start) / 2);
        Point w = new Point(x);
        Point v = new Point(x);
        double pred_len = (end - start);
        double len = pred_len;
        writeLogInfAboutMethod();
        int ind = 0;
        while (true) { // критерий сходимости
            boolean isSuccessiveParabolic = false;
            Point u = new Point(x);
            if (pairwiseDifferent(x, w, v)) { // use SuccessiveParabolicMethod
                Point newX = new Point(Math.min(Math.min(x.getX(), w.getX()), v.getX()));
                Point newV = new Point(Math.max(Math.max(x.getX(), w.getX()), v.getX()));
                Point newW = new Point(getMediana(x, w, v, newX, newV));
                u = new Point(SuccessiveParabolicMethod.findMinDot(newX, newV, newW));
                if (u.compareToX(left) > 0 && u.compareToX(right) < 0) {
                    if (pred_len / 2 < lenOX(u, x)) {
                        isSuccessiveParabolic = true;
                    }
                }
            }
            if (!isSuccessiveParabolic) { // use GoldenRatioMethod
                if (lenOX(left, x) > lenOX(x, right))
                    u =  findMinDot(left, x);
                else
                    u = findMinDot(x, right);
            }
            /*writeLog(String.format("%d & [%.4f:%.4f] & %.4f & %.4f & %s & %s & %s", ind++, left.getX(), right.getX(), len, pred_len / len,
            x.toString(), w.toString(), u.toString()));*/
            if (x.compareToX(u) == 0)
                return u.getX();
            SuccessiveParabolicMethod.recountPoint(left, x, right, u);
            pred_len = len;
            len = lenOX(left, right);
            if (isSuccessiveParabolic) {
                v.set(w);
                w.set(x);
                x.set(u);
            }
        }
    }
}
