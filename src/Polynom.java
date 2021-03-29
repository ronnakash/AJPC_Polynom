import java.util.*;
import java.util.List;

public class Polynom {
    int[] exponents;
    double[] coefficients;

    public Polynom(int[] exp, double[] coef) throws ArrayLengthsUnmatchException {
        if (exp.length != coef.length) {
            throw new ArrayLengthsUnmatchException(coef.length, coef.length);
        }
        exponents = exp;
        coefficients = coef;
    }


    // adds polynom to another
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

    // subtract subtractor polynom from subtractee polynom and return new polynom result
    public static Polynom minus(Polynom subtractee, Polynom subtractor) {
        Polynom resultPolynom, tempSubtractorPoly;
        try { //???
            tempSubtractorPoly = new Polynom(subtractor.exponents, subtractor.coefficients);
        } catch (Exception unexpectedException) {
            unexpectedException.printStackTrace();
            return null;
        }
        tempSubtractorPoly.flipCoefficients();
        resultPolynom = plus(subtractee, tempSubtractorPoly);
        return resultPolynom;
    }

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
            newExpArray[i] = exponents[i];
            newCoefArray[i] = coefficients[i];
        }
        return new Polynom(newExpArray, newCoefArray);
    }

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
        String polyString = new String();
        for (int i = 0; i < coefficients.length - 1; i++)
            polyString = polyString.concat(String.valueOf(coefficients[i])).concat("*x^").concat(String.valueOf(exponents[i])).concat("+");
        polyString = polyString.concat(String.valueOf(coefficients[coefficients.length - 1]));
        if (exponents[exponents.length - 1] != 0)
            polyString = polyString.concat("*x^").concat(String.valueOf(exponents[coefficients.length - 1]));
        return polyString;
    }

    public boolean equals(Polynom otherPoly) {
        if (this.exponents.length != otherPoly.exponents.length)
            return false;
        for (int i = 0; i < this.coefficients.length; i++) {
            if (this.exponents[i] != otherPoly.exponents[i] || this.coefficients[i] != otherPoly.coefficients[i])
                return false;
        }
        return true;
    }

    public void flipCoefficients() {
        for (int i = 0; i < coefficients.length; i++)
            coefficients[i] -= 2.0 * coefficients[i];
    }

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

    public static Polynom getPolynomFromInput() {
        Polynom newPoly;
        Pair polyPair;
        List<Pair> polyPairs = new ArrayList<Pair>();
        Scanner scan = new Scanner(System.in);
        //get next pair
        while (true) {
            polyPair = getNextPair(scan);
            if (polyPair == null)
                break;
            //add to list ; check if exponent already defined in this polynom
            addPolyToList(polyPairs, polyPair);
        }
        //check that the polyPairs is not empty
        if (polyPairs.size() == 0) {
            System.out.println("started with 0, retry");
            return getPolynomFromInput();
        }
        //sort by exp
        Collections.sort(polyPairs);
        newPoly = makePolynomFromPolyPairsList(polyPairs);
        newPoly.removeZeroCoefficients();
        return newPoly;
    }

    public static void addPolyToList(List<Pair> pairList, Pair polyPair) {
        boolean contains = false;
        for (Pair pair : pairList) {
            if (pair.exp == polyPair.exp) {
                pair.coef += polyPair.coef;
                contains = true;
            }
        }
        if (!contains)
            pairList.add(polyPair);
    }

    public static Polynom makePolynomFromPolyPairsList(List<Pair> polyPairs) {
        int[] newExpArray = new int[polyPairs.size()];
        double[] newCoefArray = new double[polyPairs.size()];
        int i = 0;
        for (Pair polyPair : polyPairs) {
            newCoefArray[i] = polyPair.coef;
            newExpArray[i++] = polyPair.exp;
        }
        return new Polynom(newExpArray, newCoefArray);
    }

    public static Pair getNextPair(Scanner scan) {
        Pair newPair;
        Integer newExp;
        Double newCoef;
        System.out.println("Enter next coefficient for new polynom. Enter 0 to stop");
        newCoef = scan.nextDouble();
        if (newCoef == 0)
            return null;
        System.out.println("Enter the exponent for coefficient");
        newExp = scan.nextInt();
        newPair = new Pair(newCoef, newExp);
        return newPair;
    }


    public class ArrayLengthsUnmatchException extends RuntimeException {
        int coefLength;
        int expLength;

        public ArrayLengthsUnmatchException(int coefLength, int expLength) {
            super("found coefficient array length " + coefLength + " and exponnent array length " + expLength);
            this.coefLength = coefLength;
            this.expLength = expLength;
        }
    }

    /**/
}

