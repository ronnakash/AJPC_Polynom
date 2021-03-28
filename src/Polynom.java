import java.util.*;
import java.util.List;

public class Polynom {
    int[] exponents;
    double[] coefficients;

    public Polynom(int[] exp, double[] coef) throws ArrayLengthsUnmatchException {
        if(exp.length != coef.length){
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
        int idxA = 0, idxB = 0;
        while (idxA < polyA.coefficients.length && idxB < polyB.coefficients.length) {
            if (polyA.exponents[idxA] == resultExponents[idxA + idxB])
                resultcoefficients[idxA + idxB] += polyA.coefficients[(idxA++) + idxB];
            if (polyB.exponents[idxA] == resultExponents[idxA + idxB])
                resultcoefficients[idxA + idxB] += polyB.coefficients[idxA + (idxB++)];
        }
        if (idxA < polyA.coefficients.length) {
            while (idxA < polyA.coefficients.length)
                resultcoefficients[idxA + idxB] += polyA.coefficients[idxA++];
        } else if (idxB < polyB.coefficients.length) {
            while (idxB < polyB.coefficients.length)
                resultcoefficients[idxA + idxB] += polyA.coefficients[idxB++];
        }

        try {
            resultPolynom = new Polynom(resultExponents, resultcoefficients);
            // TODO: remove exponents and coefficients where coefficients are zero
            resultPolynom.removeZeroCoefficients();
        } catch (Exception e) {
            e.printStackTrace();
            resultPolynom = null;
        }
        return resultPolynom;
    }

    // subtract subtractor polynom from subtractee polynom and return new polynom result
    public static Polynom minus(Polynom subtractee, Polynom subtractor) {
        Polynom resultPolynom, tempSubtractorPoly;
        try { //???
            tempSubtractorPoly = new Polynom(subtractor.exponents, subtractor.coefficients);
        } catch (Exception unexpectedException){
            unexpectedException.printStackTrace();
            return null;
        }
        tempSubtractorPoly.flipCoefficients();
        resultPolynom = plus(subtractee, tempSubtractorPoly);
        return resultPolynom;
    }

    // gets two polynoms and combines their exponent arrays
    public static int[] getCombinedExponentArray(Polynom polyA, Polynom polyB) {
        int[] combinedExponentArray, tempExponentsArray = new int[polyA.exponents.length + polyB.exponents.length];
        int idxA = 0, idxB = 0, finalSize = tempExponentsArray.length;
        while (idxA < polyA.exponents.length && idxB < polyB.exponents.length) {
            if (polyA.exponents[idxA] > polyB.exponents[idxB])
                tempExponentsArray[idxA + idxB] = polyA.exponents[idxA++];
            else if (polyB.exponents[idxB] > polyA.exponents[idxA])
                tempExponentsArray[idxA + idxB] = polyA.exponents[idxB++];
            else {
                tempExponentsArray[idxA++ + idxB++] = polyA.exponents[idxA];
                finalSize--;
            }
        }
        if (idxA < polyA.exponents.length) {
            while (idxA < polyA.exponents.length)
                tempExponentsArray[idxA + idxB] = polyA.exponents[idxA++];
        } else if (idxB < polyB.exponents.length) {
            while (idxB < polyB.exponents.length)
                tempExponentsArray[idxA + idxB] = polyA.exponents[idxB++];
        }
        combinedExponentArray = new int[finalSize];
        for (int i = 0; i < finalSize; i++)
            combinedExponentArray[i] = tempExponentsArray[i];
        return combinedExponentArray;
    }

    //remove coefficients and exponents where coefficient is zero
    public void removeZeroCoefficients(){
        int size = exponents.length, j=0;
        double[] newCoefficients, tempCoefficients = new double[size];
        int[] newExponents, tempExponents = new int[size];
        for (int i=0; i<exponents.length; i++){
            if(coefficients[i]==0.0)
                size--;
            else {
                tempExponents[j] = exponents[i];
                tempCoefficients[j++] = coefficients[i];
            }
        }
        newExponents = new int[size];
        newCoefficients = new double[size];
        for (int i=0; i<size; i++) {
            newCoefficients[i]= tempCoefficients[i];
            newExponents[i]=newExponents[i];
        }
        coefficients = newCoefficients;
        exponents = newExponents;
    }

    public String toString() {
        String polyString = new String();
        for (int i = 0; i < coefficients.length; i++) { //TODO: one concat
            polyString.concat(String.valueOf(coefficients[i]));
            polyString.concat("*x^");
            polyString.concat(String.valueOf(exponents[i]));
            polyString.concat("+");
        }
        return polyString.substring(0,polyString.length()-1);
    }

    public boolean equals(Polynom otherPoly){
        if(this.exponents.length != otherPoly.exponents.length)
            return false;
        for (int i = 0; i < this.coefficients.length; i++){
            if(this.exponents[i] != otherPoly.exponents[i] || this.coefficients[i] != otherPoly.coefficients[i])
                return false;
        }
    return true;
    }

    public void flipCoefficients(){
        for(int i=0; i<coefficients.length; i++)
            coefficients[i] -= 2.0*coefficients[i];
    }

    public static void main(String[] args){
        Polynom firstPoly, secondPoly,;
        firstPoly = getPolynomFromInput();
        System.out.println("first polynom: " + firstPoly);
        secondPoly = getPolynomFromInput();
        System.out.println("second polymon: " + secondPoly);
        System.out.println("addition: (" + firstPoly + ") + (" + secondPoly +") = " + plus(firstPoly,secondPoly));
        System.out.println("subtraction: (" + firstPoly + ") - (" + secondPoly +") = " + minus(firstPoly,secondPoly));

    }

    public static Polynom getPolynomFromInput(){
        Polynom newPoly;
        Pair polyPair;
        Double coef;
        Integer exp;
        List<Pair> polyPairs = new LinkedList<Pair>();
        //get next pair
        while (true){
            polyPair = getNextPair();
            if(polyPair == null)
                break;
            //add to list sorted by exp; check if exponent already defined in this polynom

        }
        //check that the polyPairs is not empty

    }


    public static Pair getNextPair(){
        Pair newPair;
        Integer newExp;
        Double newCoef;
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter next coefficient for new polynom. Enter 0 to stop");
        newCoef = scan.nextDouble();
        if(newCoef == 0)
            return null;
        System.out.println("Enter the exponent for coefficient");
        newExp = scan.nextInt();
        newPair = new Pair(newCoef,newExp);
        return newPair;
    }
        
        
    public class ArrayLengthsUnmatchException extends RuntimeException{
        int coefLength;
        int expLength;

        public ArrayLengthsUnmatchException(int coefLength, int expLength){
            super("found coefficient array length "+ coefLength + " and exponnent array length "+ expLength);
            this.coefLength = coefLength;
            this.expLength = expLength;
        }
    }




}

