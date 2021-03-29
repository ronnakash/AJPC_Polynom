public class Pair implements Comparable{
    Double coef;
    Integer exp;

    public Pair(Double co, Integer ex){
        coef = co;
        exp = ex;
    }


    @Override
    public int compareTo(Object o) {
        if(!(o instanceof Pair)) return -1;
        if(this.exp == null && ((Pair) o).exp == null) return 0;
        if(this.exp==null) return -1;
        if(((Pair) o).exp==null) return -1;
        return this.exp.compareTo(((Pair) o).exp)*-1;
    }
}
