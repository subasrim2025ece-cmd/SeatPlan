package com.example.SeatPlan.hall.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "halls")
public class Hall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hallId;

    private String hallName;

    @Column(name = "row_count")
    private int rowCount;

    @Column(name = "columns")
    private int columns;

    private int capacity;

    public Hall() {
    }

    public Hall(String hallName, int rowCount, int columns, int capacity) {
        this.hallName = hallName;
        this.rowCount = rowCount;
        this.columns = columns;
        this.capacity = capacity;
    }

    public Long getHallId() {
        return hallId;
    }

    public void setHallId(Long hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}