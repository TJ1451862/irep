package cn.edu.whu.irlab.irep.service.retrievalModel.vsmmodel;

import java.util.Comparator;

public class ResultComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        ResultI r1 = (ResultI) o1;
        ResultI r2 = (ResultI) o2;
        if (r1.getSimilarity() < r2.getSimilarity())
            return 1;
        else
            return 0;
    }
}
