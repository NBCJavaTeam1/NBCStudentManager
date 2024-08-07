package dummy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utility {
    // 배열의 원소 2개의 위치를 바꿔주는 함수
    public static<T> void swap(T[] elements, int index1, int index2) {
        T temp = elements[index1];

        elements[index1] = elements[index2];
        elements[index2] = temp;
    }

    // elements에서 count만큼의 랜덤한 원소를 뽑아주는 함수
    public static<T> List<T> permutation(T[] elements, Random rand, int count) {
        List<T> result = new ArrayList<>(elements.length);
        int maxRange = 0;

        for(int i = 0; i < count; ++i) {
            // 원소 최대 범위 ( maxRange 이후 = 이미 뽑힌 것들 )
            maxRange = elements.length - result.size();
            int index = rand.nextInt(maxRange);

            result.add(elements[index]);
            // 뽑은 것은 맨 뒤로 보낸다.
            swap(elements, index, elements.length - result.size());
        }

        return result;
    }
}
