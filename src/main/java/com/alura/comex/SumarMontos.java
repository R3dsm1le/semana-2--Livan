package com.alura.comex;

import java.math.BigDecimal;
import java.util.function.BinaryOperator;

public class SumarMontos {
    private BinaryOperator<BigDecimal> sumar;
public SumarMontos(){
    this.sumar = BigDecimal::add;
}
    public void SumarMontosDeVentas(BigDecimal monto , BigDecimal montoventa){
        montoventa = sumar.apply(montoventa , monto);
    }

}
