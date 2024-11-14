package model;

import java.time.LocalDateTime;
import enums.ReplenishStatus;

public class Medicine {
    private String medicineID;
    private String name;
    private String manufacturer;
    private LocalDateTime expiryDate;
    private int inventoryStock;
    private int lowStockLevel;
    private ReplenishStatus status;
    private LocalDateTime replenishRequestDate;
    private LocalDateTime approvedDate;

    // Constructor
    public Medicine(String medicineID, String name, String manufacturer, LocalDateTime expiryDate, int inventoryStock,
            int lowStockLevel, ReplenishStatus status, LocalDateTime replenishRequestDate, LocalDateTime approvedDate) {
        this.medicineID = medicineID;
        this.name = name;
        this.manufacturer = manufacturer;
        this.expiryDate = expiryDate;
        this.inventoryStock = inventoryStock;
        this.lowStockLevel = lowStockLevel;
        this.status = status;
        this.replenishRequestDate = replenishRequestDate;
        this.approvedDate = approvedDate;
    }

    // Getters and Setters
    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public int getLowStockLevel() {
        return this.lowStockLevel;
    }

    public void setLowStockLevel(int lowStockLevel) {
        this.lowStockLevel = lowStockLevel;
    }

    public ReplenishStatus getReplenishStatus() {
        return status;
    }

    public void setReplenishStatus(ReplenishStatus status) {
        this.status = status;
    }

    public LocalDateTime getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(LocalDateTime approvedDate) {
        this.approvedDate = approvedDate;
    }

    public int getInventoryStock() {
        return inventoryStock;
    }

    public void setInventoryStock(int inventoryStock) {
        this.inventoryStock = inventoryStock;
    }

    public LocalDateTime getReplenishRequestDate() {
        return replenishRequestDate;
    }

    public void setReplenishRequestDate(LocalDateTime replenishRequestDate) {
        this.replenishRequestDate = replenishRequestDate;
    }

}