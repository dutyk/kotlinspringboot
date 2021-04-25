package io.kang.function;

import java.util.Arrays;
import java.util.List;

public class AddDemo {
    public final static List<Integer> nums = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 10);

    public static Integer sum(List<Integer> nums) {
        int result = 0;
        for (Integer num : nums) {
            result += num;
        }

        return result;
    }

    public static void main(String[] args) {
        System.out.println(AddDemo.sum(nums));
    }
}
