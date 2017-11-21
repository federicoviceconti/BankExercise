package gestionale_banca.repository;

import gestionale_banca.controller.Logger;
import gestionale_banca.model.Conto;
import gestionale_banca.model.Deposito;
import gestionale_banca.model.Ritiro;
import gestionale_banca.utility.Error;
import gestionale_banca.utility.Fields;
import gestionale_banca.utility.exception.conto.SaldoInsufficienteException;

import java.math.BigDecimal;
import java.time.LocalTime;

public class OperazioneDaoImpl implements OperazioneDao {
    private Conto conto;

    public OperazioneDaoImpl(Conto conto) {
        this.conto = conto;
    }

    @Override
    public boolean deposita(BigDecimal daDepositare) {
        if (!sogliaDeposito()) {
            conto.addToSaldo(daDepositare);
            return true;
        }

        return false;
    }

    @Override
    public boolean ritira(BigDecimal daRitirare) {
        if (!sogliaRitiro()) {
            try {
                conto.ritiroDaSaldo(daRitirare);
                return true;
            } catch (SaldoInsufficienteException saldoInsufficiente) {
                Logger.write(LocalTime.now(), saldoInsufficiente.getMessage(), Error.CLIENTE);
            }
        }

        return false;
    }

    private boolean sogliaDeposito() {
        return conto.getOperazioni()
                .stream()
                .filter(x -> x instanceof Deposito)
                .mapToDouble(x -> x.getCifra().intValue())
                .sum() > Fields.Constant.MAX_DEPOSITO;
    }

    private boolean sogliaRitiro() {
        return conto.getOperazioni()
                .stream()
                .filter(x -> x instanceof Ritiro)
                .mapToDouble(x -> x.getCifra().intValue())
                .sum() > Fields.Constant.MAX_DEPOSITO;
    }
}
