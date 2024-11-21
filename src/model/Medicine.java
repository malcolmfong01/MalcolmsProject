package model;

import java.time.LocalDateTime;
import enums.ReplenishStatus;

/**
 * Represents a medicine in the system.
 * Contains information about the medicine's ID, name, manufacturer, expiry date,
 * inventory stock, low stock levels, and replenishment status.
 */

public class Medicine {
    private String medicineID;
    private String name;
    private String manufacturer;
    private LocalDateTime expiryDate;
    private int inventoryStock;
    private int lowStockLevel;
    private int replenishmentStock;
    private ReplenishStatus status;
    private LocalDateTime replenishRequestDate;
    private LocalDateTime approvedDate;

    /**
     * Constructs a new Medicine object with the specified details.
     *
     * @param medicineID the unique ID of the medicine
     * @param name the name of the medicine
     * @param manufacturer the manufacturer of the medicine
     * @param expiryDate the expiry date of the medicine
     * @param inventoryStock the current inventory stock level of the medicine
     * @param lowStockLevel the threshold level for low stock
     * @param status the replenishment status of the medicine
     * @param replenishRequestDate the date when a replenish request was made
     * @param approvedDate the date when the replenish request was approved
     */
    // Constructor
    public Medicine(String medicineID, String name, String manufacturer, LocalDateTime expiryDate, int inventoryStock,
            int lowStockLevel, int replenishmentStock, ReplenishStatus status, LocalDateTime replenishRequestDate, LocalDateTime approvedDate) {
        this.medicineID = medicineID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.inventoryStock = inventoryStock;
        this.lowStockLevel = lowStockLevel;
        this.replenishmentStock = replenishmentStock;
        this.status = status;
        this.replenishRequestDate = replenishRequestDate;
        this.approvedDate = approvedDate;
    }

    /**
     * Gets the unique ID of the medicine.
     *
     * @return the medicine ID
     */
    // Getters and Setters
    public String getMedicineID() {
        return medicineID;
    }

    /**
     * Sets the unique ID for the medicine.
     *
     * @param medicineID the medicine ID to set
     */
    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    /**
     * Gets the name of the medicine.
     *
     * @return the name of the medicine
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the medicine.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the manufacturer of the medicine.
     *
     * @return the manufacturer of the medicine
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * Sets the manufacturer of the medicine.
     *
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * Gets the expiry date of the medicine.
     *
     * @return the expiry date of the medicine
     */
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    /**
     * Sets the expiry date of the medicine.
     *
     * @param expiryDate the expiry date to set
     */
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Gets the low stock level of the medicine.
     *
     * @return the low stock level of the medicine
     */
    public int getLowStockLevel() {
        return this.lowStockLevel;
    }

    /**
     * Sets the low stock level for the medicine.
     *
     * @param lowStockLevel the low stock level to set
     */
    public void setLowStockLevel(int lowStockLevel) {
        this.lowStockLevel = lowStockLevel;
    }

    /**
     * Gets the replenishment status of the medicine.
     *
     * @return the replenishment status of the medicine
     */
    public ReplenishStatus getReplenishStatus() {
        return status;
    }

    /**
     * Sets the replenishment status for the medicine.
     *
     * @param status the replenishment status to set
     */
    public void setReplenishStatus(ReplenishStatus status) {
        this.status = status;
    }

    /**
     * Gets the date when the replenishment request was approved.
     *
     * @return the date the replenish request was approved
     */
    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    /**
     * Sets the approved date for the replenishment request.
     *
     * @param approvedDate the approved date to set
     */
    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    /**
     * Gets the current inventory stock of the medicine.
     *
     * @return the current inventory stock
     */
    public int getInventoryStock() {
        return inventoryStock;
    }

    /**
     * Sets the current inventory stock for the medicine.
     *
     * @param inventoryStock the inventory stock to set
     */
    public void setInventoryStock(int inventoryStock) {
        this.inventoryStock = inventoryStock;
    }

    /**
     * Gets the date when the replenish request was made.
     *
     * @return the date the replenish request was made
     */
    public LocalDateTime getReplenishRequestDate() {
        return replenishRequestDate;
    }

    /**
     * Sets the replenish request date for the medicine.
     *
     * @param replenishRequestDate the replenish request date to set
     */
    public void setReplenishRequestDate(LocalDateTime replenishRequestDate) {
        this.replenishRequestDate = replenishRequestDate;
    }

    /**
     * Gets the replenishment stock level for the medical record.
     *
     * @return the replenishment stock level
     */
    public int getReplenishmentStock() {
        return replenishmentStock;
    }

    /**
     * Sets the replenishment stock level for the medical record.
     *
     * @param replenishmentStock the replenishment stock level to set
     */
    public void setReplenishmentStock(int replenishmentStock) {
        this.replenishmentStock = replenishmentStock;
    }

}