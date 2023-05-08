//package Step2Bonus1;
//
//import Step2Bonus1.enumiration.Scale;
//import Step2Bonus1.utilities.Colors;
//
//import java.util.List;
//
//public class Histogram {
//
//    public static void printHistogram(List<Double> numbers, Scale scale,String text) {
//        System.out.println(Colors.RED + "Ãטסעמדנאללא");
//        System.out.println(Colors.CYAN+text);
//        double max = numbers.stream().mapToDouble(e -> e).max().getAsDouble();
//        max = max + 5;
//        Canvas canvas = new Canvas((numbers.size() * 2) + 3, (int) max);
//
//        canvas.drawBorder("#");
//
//        for (int i = 0; i < canvas.getWidth() - 3; i++) {
//            if (i % 2 == 0) {
//                double height = canvas.getHeight() - numbers.get(i / 2);
//                for (int j = canvas.getHeight() - 1; j >= height; j--) {
//                    canvas.setPixel(i + 2, j - 1, "*");
//                }
//            }
//        }
//        System.out.println(scale.getValue());
//        for (Double number : numbers) {
//            System.out.print(Colors.CYAN + String.format("%.4f",number) + ",");
//        }
//        System.out.println();
//        System.out.println(canvas);
//    }
//
//}
