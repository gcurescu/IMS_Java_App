package model;

/**
 * This is the Outsourced class.
 *
 * @author Gentillo Curescu
 *
 */
public class Outsourced extends Part {

    private String companyName;

    /**
     * This is the Constructor for a new Outsourced part.
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * The getter and setter
     */
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
