package org.example.sorters;

import org.example.utils.Sorter;

public class RatingSorter extends Sorter {
    private int[] ratings;
    private int[] idPrestador;

    public RatingSorter(int[] ratings, int[] idPrestador) {
        this.ratings = ratings;
        this.idPrestador = idPrestador;
    }


}
