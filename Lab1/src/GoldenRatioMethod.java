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
        double x1 = l + (1 - INV_GOLDEN_RATIO) * (r - l);
        double x2 = l + INV_GOLDEN_RATIO * (r - l);
        counter++;
        counter++;
        double fx1 = f.apply(x1);
        double fx2 = f.apply(x2);
        double len = lenOX(r, l);
        double pred_len = len;
        writeLogInfAboutMethod();
        int ind = 0;
        while (compare(r - l, eps) > 0) {
            if (compare(fx1, fx2) <= 0) {
                /*writeLog(String.format("%d & [%.5f : %.5f] & %.5f & %.5f & %s & %s", ind++, l, r, len, pred_len/len,
                        new Point(x1).toString(), new Point(x2).toString()));*/
                r = x2;
                x2 = x1;
                x1 = r - INV_GOLDEN_RATIO * (r - l);
                fx2 = fx1;
                counter++;
                fx1 = f.apply(x1);
            } else {
                /*writeLog(String.format("%d & [%.5f : %.5f] & %.5f & %.5f & %s & %s", ind++, l, r, len, pred_len/len,
                        new Point(x1).toString(), new Point(x2).toString()));*/
                l = x1;
                x1 = x2;
                x2 = l + INV_GOLDEN_RATIO * (r - l);
                fx1 = fx2;
                counter++;
                fx2 = f.apply(x2);
            }
            pred_len = len;
            len = lenOX(r, l);
        }
        /*writeLog(String.format("%d & [%.5f : %.5f] & %.5f & %.5f & %s & %s", ind, l, r, len, pred_len/len,
                new Point(x1).toString(), new Point(x2).toString()));*/
        return (x1 + x2) / 2;
    }
}
