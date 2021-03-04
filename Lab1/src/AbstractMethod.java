import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;

public abstract class AbstractMethod implements OptimizationMethod {
    private static final double COMPARE_PRECISION = 1e-10;
    private static final Charset FILE_CHARSET = StandardCharsets.UTF_8;
    private static final Path outputFile = Path.of("outLog");
    protected final double eps;
    protected final double start;
    protected final double end;
    protected final String name;
    protected final Function<Double, Double> f;

    protected AbstractMethod(String name, double eps, double start, double end, Function<Double, Double> f) {
        this.name = name;
        this.eps = eps;
        this.start = start;
        this.end = end;
        this.f = f;
    }

    protected void writeLog(String opts) {
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile, FILE_CHARSET, StandardOpenOption.APPEND)) {
            StringBuilder ans = new StringBuilder();
            ans.append(opts);
            ans.append(" \\\\ \n");
            writer.append(ans);
        } catch (IOException e) {
            // do nothing
        }
    }

    double lenOX(Point a, Point b) {
        return Math.abs(a.getX() - b.getX());
    }

    double lenOX(double a, double b) {
        return Math.abs(a - b);
    }

    protected void writeLogString(String inf) {
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile, FILE_CHARSET, StandardOpenOption.APPEND)) {
            writer.append(inf + "\n");
        } catch (IOException e) {
            // do nothing
        }
    }

    protected void writeLogInfAboutMethod() {
        try (BufferedWriter writer = Files.newBufferedWriter(outputFile, FILE_CHARSET, StandardOpenOption.APPEND)) {
            writer.append(toString() + "\n");
        } catch (IOException e) {
            // do nothing
        }
    }

    public String toString() {
        return name + " at " + "[" + start + ":" + end + "]" + " with exp = " + eps;
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

    public class Point implements Comparable<Point> {
        private double epsY = 1e-9;
        private double x;
        private double y;

        Point(double x) {
            this.x = x;
            this.y = f.apply(x);
        }

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        Point(Point point) {
            this.x = point.getX();
            this.y = point.getY();
        }

        public String toString() {
            return String.format("(%.4f:%.4f)", x, y);
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
            this.y = f.apply(x);
        }

        public void set(Point p) {
            this.x = p.getX();
            this.y = p.getY();
        }


        public double getY() {
            return y;
        }

        public int compareToX(Point o) {
            return Math.abs(o.getX() - this.x) < eps ? 0 : (this.x > o.getX() ? 1 : -1);
        }

        public int compareToY(Point o) {
            return Math.abs(o.getY() - this.y) < epsY ? 0 : (this.y > o.getY() ? 1 : -1);
        }

        @Override
        public int compareTo(Point o) {
            return compareToX(o);
        }

        protected void printLog(double left, double right, double x, double y, int inter) {

        }
    }
}
