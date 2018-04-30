package mpi;

import mpi.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Compile an run only through command line with FastMPJ
 */

public class Main {
    public static void main(String args[]) {
        for (int i = 0; i < 100; ++i) {
            args = MPI.Init(args);

            int me = MPI.COMM_WORLD.Rank();

            int[] arr1 = ThreadLocalRandom.current().ints(100000, -10000, 10000000).toArray();
            int[] arr2 = ThreadLocalRandom.current().ints(100000, -10000, 10000000).toArray();
            int[] arr3 = ThreadLocalRandom.current().ints(100000, -10000, 10000000).toArray();

            Sample[] sample1 = new Sample[1];
            Sample[] sample2 = new Sample[1];
            Sample[] sample3 = new Sample[1];

            double start = MPI.Wtime();
            double end;

            if (me == 0) {
                sample1[0] = new Sample(arr1, arr2);
                sample2[0] = new Sample(arr2, arr3);
                sample3[0] = new Sample(arr3, arr1);

                MPI.COMM_WORLD.Send(sample1, 0, 1, MPI.OBJECT, 1, 10);
                MPI.COMM_WORLD.Send(sample2, 0, 1, MPI.OBJECT, 2, 10);
                MPI.COMM_WORLD.Send(sample3, 0, 1, MPI.OBJECT, 3, 10);
            } else if (me == 1) {
                // First and second
                MPI.COMM_WORLD.Recv(sample1, 0, 1, MPI.OBJECT, 0, 10);
                sample1[0].arr1 = sample1[0].isIntersect();
                MPI.COMM_WORLD.Send(sample1, 0, 1, MPI.OBJECT, 4, 10);
            } else if (me == 2) {
                // Second and Third
                MPI.COMM_WORLD.Recv(sample2, 0, 1, MPI.OBJECT, 0, 10);
                sample2[0].arr1 = sample2[0].isIntersect();
                MPI.COMM_WORLD.Send(sample2, 0, 1, MPI.OBJECT, 4, 10);
            } else if (me == 3) {
                // Third and First
                MPI.COMM_WORLD.Recv(sample3, 0, 1, MPI.OBJECT, 0, 10);
                sample3[0].arr1 = sample3[0].isIntersect();
                MPI.COMM_WORLD.Send(sample3, 0, 1, MPI.OBJECT, 5, 10);
            } else if (me == 4) {
                // First and Second and Second and Third
                MPI.COMM_WORLD.Recv(sample1, 0, 1, MPI.OBJECT, 1, 10);
                MPI.COMM_WORLD.Recv(sample2, 0, 1, MPI.OBJECT, 2, 10);

                sample1[0].arr2 = sample2[0].arr1;

                sample2[0].arr2 = sample1[0].isIntersect();

                MPI.COMM_WORLD.Send(sample2, 0, 1, MPI.OBJECT, 5, 10);
            } else if (me == 5) {
                MPI.COMM_WORLD.Recv(sample3, 0, 1, MPI.OBJECT, 3, 10);
                MPI.COMM_WORLD.Recv(sample2, 0, 1, MPI.OBJECT, 4, 10);

                sample3[0].arr2 = sample2[0].arr2;

                end = MPI.Wtime();
                System.out.println(end - start);
            }

            MPI.Finalize();
        }
    }
}

class Sample implements Serializable {

    public int[] arr1;
    public int[] arr2;

    Sample(int[] arr1, int[] arr2) {
        this.arr1 = arr1;
        this.arr2 = arr2;
    }

    public int[] isIntersect() {
        HashSet<Integer> set1 = new HashSet<>();
        for (int i : arr1) {
            set1.add(i);
        }

        HashSet<Integer> set2 = new HashSet<>();
        for (int i : arr2) {
            if (set1.contains(i))
                set2.add(i);
        }

        int[] result = new int[set2.size()];
        int indx = 0;
        for (Integer n : set2) {
            result[indx++] = n;
        }

        return result;
    }

}
