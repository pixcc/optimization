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
        writeLogInfAboutMethod();
        double len = lenOX(start, end);
        double pred_len = len;
        int ind = 0;
        while ((right - left) / 2.0 > eps) {
            double x1 = (right + left - delta) / 2.0;
            double x2 = (right + left + delta) / 2.0;
            writeLog(String.format("%d & [%.5f : %.5f] & %.5f & %.5f & %s & %s", ind++, left, right,
                    len, pred_len / len,
                    new Point(x1).toString(), new Point(x2).toString()));
            if (f.apply(x1) <= f.apply(x2)) {
                right = x2;
            } else {
                left = x1;
            }
            pred_len = len;
            len = lenOX(right, left);

        }
        return (left + right) / 2.0;
    }
}
