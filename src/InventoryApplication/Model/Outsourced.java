package InventoryApplication.Model;

/**
 * Outsourced class is an extension of Parts class; includes same constructor with added company name info */

/**
 *
 * @author Chelsey Machin
 */
public class Outsourced extends Part{
    private String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return companyName of outsourced part
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName set the Company Name of the outsourced part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
