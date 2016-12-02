package com.future.sharelibrary.tools;

import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

/**
 * 数值的类型数组排序
 */
public class SortFindUtil {

    /**
     * 查找数组中是否有指定值
     *
     * @param group
     * @param value
     * @return
     */
    public static synchronized Boolean dichotomization(List<Integer> group, double value) {
        int low = 0, high = group.size() - 1, mid;
        while (low <= high) {
            mid = (low + high) / 2;
            if (value == group.get(mid)) {
                return true;
            }
            if (value > group.get(mid)) {
                low = mid + 1;
            }
            if (value < group.get(mid)) {
                high = mid - 1;
            }
        }
        return false;
    }


    /**
     * 去重
     *
     * @param group
     * @return
     */
    public static synchronized  List<Integer> repeatedlyKill(List<Integer> group) {
        if (null == group || group.size() < 0) {
            return new ArrayList<>();
        }
        double temp= group.get(0);
        for (int i = 1; i < group.size(); i++) {
            if(dichotomization(group,temp)){
                group.remove(i);
                i--;
            }
        }
        return group;
    }

    private enum SORT_TYPE {
        ASC(0), DES(1);

        SORT_TYPE(int value) {
            this.value = value;
        }

        public int value;
    }

    /**
     * 排序,
     *
     * @param group
     * @param sortType 排序方式,asc||des,默认为升序
     * @return
     */
    public  static synchronized  List<Integer> sort(List<Integer> group, SORT_TYPE sortType) {
        if (null == group || group.size() <= 0) {
            return new ArrayList<>();
        }
        int temp;
        switch (sortType.value) {
            case 0:
                for (int i = 0; i < group.size(); i++) {
                    for (int j = 0; j < group.size() - i - 1; j++) {
                        if (group.get(j) > group.get(j + 1)) {
                            temp = group.get(j + 1);
                            group.set(j + 1, group.get(j));
                            group.set(j, temp);
                        }
                    }
                }
                break;
            case 1:
                for (int i = 0; i < group.size(); i++) {
                    for (int j = 0; j < group.size() - i - 1; j++) {
                        if (group.get(j) < group.get(j + 1)) {
                            temp = group.get(j + 1);
                            group.set(j + 1, group.get(j));
                            group.set(j, temp);
                        }
                    }
                }
                break;
        }

        return group;
    }

}
