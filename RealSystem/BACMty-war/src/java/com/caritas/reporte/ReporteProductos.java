package com.caritas.reporte;

import com.caritas.entity.Articulos;
import com.caritas.entity.Donantes;
import com.caritas.entity.Movimientos;
import com.caritas.entity.MovtosDet;
import com.caritas.entity.RegistroProductos;
import com.caritas.entity.RegistroProductos.CantidadMermaValue;
import com.caritas.entity.RegistroProductos.DonanteSucursalKey;
import com.caritas.entity.RelacionRepVar;
import com.caritas.entity.Variedad;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

public class ReporteProductos {

    private static final Logger LOG = Logger.getLogger(ReporteProductos.class.getName());
    protected Map<DonanteSucursalKey, RegistroProductos> tablaReporte;
    private final List<Movimientos> movimientosList;
    private final String tipoReporte;

    /**
     *
     * @param movsByDate Lista de movimientos de la que se hara el reporte
     * @param tipoReporte String ID RelRepVar (Que columnas se usaran para el
     * reporte)
     */
    public ReporteProductos(List<Movimientos> movsByDate, String tipoReporte) {
        this.movimientosList = movsByDate;
        this.tipoReporte = tipoReporte;
    }

    public Collection<RegistroProductos> getRegistros() {
        return getTablaReporte().values();
    }

    private void buildTable() {
        for (Movimientos movimiento : movimientosList) {
            Collection<MovtosDet> movtosDetCollection;
            movtosDetCollection = movimiento.getMovtosDetCollection();
            addToTable(movtosDetCollection, movimiento);
        }
    }

    private void addToTable(Collection<MovtosDet> movtosDetCollection, Movimientos movimiento) {
        for (MovtosDet movtosDet : movtosDetCollection) {
            try {
                addCell(movtosDet);
            } catch (NullPointerException npe) {
                Integer idMovtosDet = movtosDet.getIDMovtosDet();
                String idFolio = movtosDet.getIDFolio();
                LOG.severe("NPE:MovtosDet:[idFolio:" + idFolio + ", idMovtosDet:" + idMovtosDet + "]");
                continue;
            }
        }
    }

    private void addCell(MovtosDet movtosDet) {
        Donantes idDonante = movtosDet.getMovimientos().getIDDonante();
        String idSucursal = movtosDet.getMovimientos().getIDSucursal();
        String clase = getClase(movtosDet);
        Double cantidad = movtosDet.getCantidad();
        Double merma = movtosDet.getMerma();
        DonanteSucursalKey k = new DonanteSucursalKey(idDonante, idSucursal);
        CantidadMermaValue v = new CantidadMermaValue(cantidad, merma);
        addCell(k, clase, v);
    }

    private void addCell(DonanteSucursalKey k, String clase, CantidadMermaValue v) {
        if (!getTablaReporte().containsKey(k)) {
            getTablaReporte().put(k, new RegistroProductos(k, clase, v));
        } else {
            getTablaReporte().get(k).addToCell(clase, v);
        }
    }

    private String getClase(MovtosDet movtosDet) {
        try {
            Articulos idArticulo = movtosDet.getIDArticulo();
            Variedad idVariedad = idArticulo.getIDVariedad();
            RelacionRepVar findRepClase = idVariedad.findRepClase(tipoReporte);
            String clase = findRepClase.getClase();
            return clase;
        } catch (NullPointerException npe) {
            LOG.warning("NPE:MovtosDet:" + movtosDet.getIDFolio() + " "
                    + movtosDet.getIDMovtosDet());
            throw npe;
        }
    }

    /**
     * @return the tablaReporte
     */
    private Map<DonanteSucursalKey, RegistroProductos> getTablaReporte() {
        if (tablaReporte == null) {
            tablaReporte = new TreeMap<DonanteSucursalKey, RegistroProductos>();
            buildTable();
        }
        return tablaReporte;
    }
}
