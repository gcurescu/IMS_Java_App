package model;

/**
 * InHouse parts class.
 *
 * @author Gentillo Curescu
 *
 */
public class InHouse extends Part {

    private int machineId;

    /**
     * This is the Constructor for a new InHouse object.
     *
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param machineId
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * The getters and setters
     */
    public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
