package io.kang.function;

public class FibonacciDemo {
    public static int fibonacci(int number) {
        if (number == 1) {
            return 1;
        }
        if(number == 2) {
            return 2;
        }

        int a = 1;
        int b = 2;
        for(int cnt = 3; cnt <= number; cnt++) {
            int c = a + b;
            a = b;
            b = c;
        }
        return b;
    }
    public static void main(String[] args) {
        System.out.println(FibonacciDemo.fibonacci(5));
    }
}
