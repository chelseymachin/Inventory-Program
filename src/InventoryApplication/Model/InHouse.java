package InventoryApplication.Model;

/**
 * InHouse class is an extension of the Part class; adds machine ID parameter to constructor
 */
public class InHouse extends Part{
    private int machineId;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @param machineId sets the machine ID of the part to the indicated number
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     *
     * @return machineID as an integer
     */
    public int getMachineId() {
        return machineId;
    }
}
