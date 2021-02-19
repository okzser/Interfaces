package model.services;

import model.entities.Invoice;
import model.entities.CarRental;

public class RentalService {

    private Double pricePerDay;
    private Double pricePerHour;

    private BrazilTaxService taxService;

    public RentalService(Double pricePerDay, Double pricePerHour, BrazilTaxService taxService) {
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
        this.taxService = taxService;
    }

    public void processInvoice(CarRental cr) {
        long t1 = cr.getStart().getTime();
        long t2 = cr.getFinish().getTime();
        double hours = (double)(t2 - t1) / 1000 / 60 / 60;

        double basicPayment;
        if (hours <= 12.0) {
            basicPayment = pricePerHour * Math.ceil(hours);
        }
        else {
            basicPayment = pricePerDay * Math.ceil(hours / 24);
        }

        double tax = taxService.tax(basicPayment);

        cr.setInvoice(new Invoice(basicPayment, tax));

    }
}
