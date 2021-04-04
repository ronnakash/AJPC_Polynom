import java.util.*;
import java.util.List;

/**
 * The type Polynom.
 */
public class Polynom {
    /**
     * The exponents array.
     */
    int[] exponents;
    /**
     * The coefficients array.
     */
    double[] coefficients;

    /**
     * Instantiates a new Polynom.
     *
     * @param exp  the exponents array
     * @param coef the coefficients array
     * @throws ArrayLengthsUnmatchException if the array lengths don't match
     */
    public Polynom(int[] exp, double[] coef) throws ArrayLengthsUnmatchException {
        if (exp.length != coef.length) {
            throw new ArrayLengthsUnmatchException(coef.length, coef.length);
        }
        exponents = exp;
        coefficients = coef;
    }


    /**
     * adds one polynom to another and returns the result.
     *
     * @param polyA the first polynom for addition
     * @param polyB the second polynom for addition
     * @return the addition result polynom
     */
    public static Polynom plus(Polynom polyA, Polynom polyB) {
        Polynom resultPolynom;
        int[] resultExponents = getCombinedExponentArray(polyA, polyB);
        double[] resultcoefficients = new double[resultExponents.length];
        int idxA = 0, idxB = 0, i = 0;
        while (idxA < polyA.coefficients.length && idxB < polyB.coefficients.length) {
            if (polyA.exponents[idxA] == resultExponents[i])
                resultcoefficients[i] += polyA.coefficients[idxA++];
            if (polyB.exponents[idxB] == resultExponents[i])
                resultcoefficients[i] += polyB.coefficients[idxB++];
            i++;
        }
        if (idxA < polyA.coefficients.length) {
            while (idxA < polyA.coefficients.length)
                resultcoefficients[i++] += polyA.coefficients[idxA++];
        } else if (idxB < polyB.coefficients.length) {
            while (idxB < polyB.coefficients.length)
                resultcoefficients[i++] += polyB.coefficients[idxB++];
        }
        try {
            resultPolynom = new Polynom(resultExponents, resultcoefficients);
            // remove exponents and coefficients where coefficients are zero
            resultPolynom.removeZeroCoefficients();
        } catch (Exception e) {
            resultPolynom = null;
        }
        return resultPolynom;
    }

    /**
     * subtract subtractor polynom from subtractee polynom and return new polynom result.
     *
     * @param subtractee the subtractee of the subtraction
     * @param subtractor the subtractee of the subtraction
     * @return the subtraction result polynom
     */
    public static Polynom minus(Polynom subtractee, Polynom subtractor) {
        Polynom resultPolynom, tempSubtractorPoly;
        try { //???
            tempSubtractorPoly = new Polynom(subtractor.exponents, subtractor.coefficients.clone());
        } catch (Exception unexpectedException) {
            unexpectedException.printStackTrace();
            return null;
        }
        tempSubtractorPoly.flipCoefficients();
        resultPolynom = plus(subtractee, tempSubtractorPoly);
        return resultPolynom;
    }

    /**
     * Calculates the derivative of a polynom.
     *
     * @return the derivative of a polynom
     */
// returns derivative polynom of given polynom;
    public Polynom derivative() {
        int size = exponents.length, newSize = size;
        int[] newExpArray;
        double[] newCoefArray;
        if (exponents[size - 1] == 0) //exponent zero can only be the last one
            newSize--;
        newCoefArray = new double[newSize];
        newExpArray = new int[newSize];
        for (int i = 0; i < newSize; i++) {
            newExpArray[i] = exponents[i] - 1;
            newCoefArray[i] = coefficients[i] * exponents[i];
        }
        return new Polynom(newExpArray, newCoefArray);
    }

    /**
     * Get combined exponent array int [ ].
     *
     * @param polyA the poly a
     * @param polyB the poly b
     * @return the int [ ]
     */
// gets two polynoms and combines their exponent arrays
    public static int[] getCombinedExponentArray(Polynom polyA, Polynom polyB) {
        int[] combinedExponentArray, tempExponentsArray = new int[polyA.exponents.length + polyB.exponents.length];
        int i = 0, idxA = 0, idxB = 0, finalSize = tempExponentsArray.length;
        while (idxA < polyA.exponents.length && idxB < polyB.exponents.length) {
            if (polyA.exponents[idxA] > polyB.exponents[idxB])
                tempExponentsArray[i++] = polyA.exponents[idxA++];
            else if (polyB.exponents[idxB] > polyA.exponents[idxA])
                tempExponentsArray[i++] = polyB.exponents[idxB++];
            else {
                tempExponentsArray[i++] = polyA.exponents[idxA++];
                idxB++;
                finalSize--;
            }
        }
        if (idxA < polyA.exponents.length) {
            while (idxA < polyA.exponents.length)
                tempExponentsArray[i++] = polyA.exponents[idxA++];
        } else if (idxB < polyB.exponents.length) {
            while (idxB < polyB.exponents.length)
                tempExponentsArray[i++] = polyB.exponents[idxB++];
        }
        combinedExponentArray = new int[finalSize];
        System.arraycopy(tempExponentsArray, 0, combinedExponentArray, 0, finalSize);
        return combinedExponentArray;
    }

    /**
     * Remove zero coefficients from polynom.
     */
//remove coefficients and exponents where coefficient is zero
    public void removeZeroCoefficients() {
        int size = exponents.length, j = 0;
        double[] newCoefficients, tempCoefficients = new double[size];
        int[] newExponents, tempExponents = new int[size];
        for (int i = 0; i < exponents.length; i++) {
            if (coefficients[i] == 0.0)
                size--;
            else {
                tempExponents[j] = exponents[i];
                tempCoefficients[j++] = coefficients[i];
            }
        }
        newExponents = new int[size];
        newCoefficients = new double[size];
        for (int i = 0; i < size; i++) {
            newCoefficients[i] = tempCoefficients[i];
            newExponents[i] = tempExponents[i];
        }
        coefficients = newCoefficients;
        exponents = newExponents;
    }

    public String toString() {
        String polyString = "";
        for (int i = 0; i < coefficients.length - 1; i++) {
            polyString = polyString.concat(String.valueOf(coefficients[i])).concat("*x^").concat(String.valueOf(exponents[i]));
            if (coefficients[i + 1] >= 0)
                polyString = polyString.concat("+");
        }
        polyString = polyString.concat(String.valueOf(coefficients[coefficients.length - 1]));
        if (exponents[exponents.length - 1] != 0)
            polyString = polyString.concat("*x^").concat(String.valueOf(exponents[coefficients.length - 1]));
        return polyString;
    }

    /**
     * Equals
     *
     * @param otherPoly the other polynom for comparison
     * @return true if the polynoms are equal
     */
    public boolean equals(Polynom otherPoly) {
        if (this.exponents.length != otherPoly.exponents.length)
            return false;
        for (int i = 0; i < this.coefficients.length; i++) {
            if (this.exponents[i] != otherPoly.exponents[i] || this.coefficients[i] != otherPoly.coefficients[i])
                return false;
        }
        return true;
    }

    /**
     * Flip coefficients of a polynom.
     */
    public void flipCoefficients() {
        for (int i = 0; i < coefficients.length; i++)
            coefficients[i] -= 2.0 * coefficients[i];
    }

    /**
     * main.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Polynom firstPoly, secondPoly;
        firstPoly = getPolynomFromInput();
        System.out.println("first polynom: " + firstPoly);
        secondPoly = getPolynomFromInput();
        System.out.println("second polymon: " + secondPoly);
        System.out.println("addition: (" + firstPoly + ") + (" + secondPoly + ") = " + plus(firstPoly, secondPoly));
        System.out.println("subtraction: (" + firstPoly + ") - (" + secondPoly + ") = " + minus(firstPoly, secondPoly));
        System.out.println("derivative of first polynom: " + firstPoly.derivative());
        System.out.println("derivative of second polynom: " + secondPoly.derivative());
    }

    /**
     * Gets polynom from input.
     *
     * @return the polynom from input
     */
    public static Polynom getPolynomFromInput() {
        int[] tempExp = new int[1];
        double[] tempCoef = new double[1];
        Polynom tempPoly, newPoly = new Polynom(tempExp, tempCoef);
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter next coefficient for new polynom. Enter 0 to stop");
        tempCoef = new double[1];
        tempCoef[0] = scan.nextDouble();
        while (tempCoef[0] != 0.0) {
            System.out.println("Enter the exponent for coefficient");
            tempExp = new int[1];
            tempExp[0] = scan.nextInt();
            tempPoly = new Polynom(tempExp, tempCoef);
            newPoly = plus(newPoly, tempPoly);
            System.out.println("Enter next coefficient for new polynom. Enter 0 to stop");
            tempCoef = new double[1];
            tempCoef[0] = scan.nextDouble();
        }
        return newPoly;
    }


    /**
     * Exception for if the type Array lengths don't match .
     */
    public class ArrayLengthsUnmatchException extends RuntimeException {
        /**
         * The coefficients array length.
         */
        int coefLength;
        /**
         * The exponents array length.
         */
        int expLength;

        /**
         * Instantiates a new Array lengths unmatch exception.
         *
         * @param coefLength the coefficients array length
         * @param expLength  the exponents array length
         */
        public ArrayLengthsUnmatchException(int coefLength, int expLength) {
            super("found coefficient array length " + coefLength + " and exponnent array length " + expLength);
            this.coefLength = coefLength;
            this.expLength = expLength;
        }
    }

}

