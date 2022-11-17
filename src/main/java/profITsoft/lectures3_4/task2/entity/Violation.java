package profITsoft.lectures3_4.task2.entity;

import java.math.BigDecimal;
import java.util.Date;

public record Violation(String firstName, String lastName, String type, BigDecimal fineAmount, Date dateTime){

}
