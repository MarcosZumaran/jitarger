package com.vegastore.jitarger.model;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "lote")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoteModel {

    // Atributos de la tabla lote

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del lote")
    private long id;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_producto")
    @Schema(description = "Id del producto")
    private ProductoModel idProducto;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    @Schema(description = "Id del proveedor del producto")
    private ProveedorModel idProveedor;

    @Column(name = "unidad_medida", length = 20, nullable = false)
    @Schema(description = "Unidad de medida del lote")
    private String unidadMedidaBase;

    @Column(name = "unidad_abreviatura", length = 6, nullable = false)
    @Schema(description = "Abreviatura de la unidad de medida del lote")
    private String unidadAbreviatura;

    @Column(name = "costo", precision = 10, scale = 2, nullable = false)
    @Schema(description = "Costo del lote")
    private BigDecimal costo;

    @Column(name = "precio_venta", precision = 10, scale = 2, nullable = false)
    @Schema(description = "Precio de venta del lote")
    private BigDecimal precio;

    @Column(name = "cantidad_inicial", precision = 10, scale = 3, nullable = false)
    @Schema(description = "Cantidad del lote")
    private BigDecimal cantidadInicial;

    @Column(name = "cantidad_disponible", precision = 10, scale = 3, nullable = false)
    @Schema(description = "Disponibles del lote")
    private BigDecimal cantidadDisponible;

    // Listas de relaciones
    @OneToMany(mappedBy = "idLote", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de detalles del pedido")
    private List<DetallePedidoModel> detalles;

    // Constructor personalizado para la creación de objetos de la tabla lote

    public LoteModel(
        ProductoModel idProducto, 
        ProveedorModel idProveedor,
        String unidadMedidaBase, 
        String unidadAbreviatura, 
        BigDecimal costo,
        BigDecimal precio, 
        BigDecimal cantidadInicial, 
        BigDecimal cantidadDisponible
        ) {
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.unidadMedidaBase = unidadMedidaBase;
        this.unidadAbreviatura = unidadAbreviatura;
        this.costo = costo;
        this.precio = precio;
        this.cantidadInicial = cantidadInicial;
        this.cantidadDisponible = cantidadDisponible;
    }

}
