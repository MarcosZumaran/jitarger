package com.vegastore.jitarger.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
    private ProductoModel producto;

    // Foreign key
    @ManyToOne
    @JoinColumn(name = "id_proveedor")
    @Schema(description = "Id del proveedor del producto")
    private ProveedorModel proveedor;

    @Column(name = "unidad_medida_base", length = 20, nullable = false)
    @Schema(description = "Unidad de medida del lote")
    private String unidadMedidaBase;

    @Column(name = "unidad_medida_abreviatura", length = 6, nullable = false)
    @Schema(description = "Abreviatura de la unidad de medida del lote")
    private String unidadMedidaAbreviatura;

    @Column(name = "costo", precision = 10, scale = 2, nullable = false)
    @Schema(description = "Costo del lote")
    private BigDecimal costo;

    @Column(name = "precio", precision = 10, scale = 2, nullable = false)
    @Schema(description = "Precio de venta del lote")
    private BigDecimal precio;

    @Column(name = "cantidad_inicial", precision = 10, scale = 3, nullable = false)
    @Schema(description = "Cantidad del lote")
    private BigDecimal cantidadInicial;

    @Column(name = "stock", precision = 10, scale = 3, nullable = false)
    @Schema(description = "Disponibles del lote")
    private BigDecimal stock;

    @Column(name =  "fecha_registro", nullable = false)
    @Schema(description = "Fecha de creación del lote")
    private LocalDateTime fechaRegistro;

    @Column(name = "fecha_actualizacion")
    @Schema(description = "Fecha de actualización del lote")
    private LocalDateTime fechaActualizacion;

    //  --- Listas de relaciones ---

    @OneToMany(mappedBy = "lote", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de detalles del pedido")
    private List<DetallePedidoModel> detalles;

    // Constructor personalizado para la creación de objetos de la tabla lote
    public LoteModel(ProductoModel producto, ProveedorModel proveedor, String unidadMedidaBase, String unidadMedidaAbreviatura, BigDecimal costo, BigDecimal precio, BigDecimal cantidadInicial) {
        this.producto = producto;
        this.proveedor = proveedor;
        this.unidadMedidaBase = unidadMedidaBase;
        this.unidadMedidaAbreviatura = unidadMedidaAbreviatura;
        this.costo = costo;
        this.precio = precio;
        this.cantidadInicial = cantidadInicial;
        this.stock = cantidadInicial; // Inicialmente el stock es igual a la cantidad inicial
        this.fechaRegistro = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.fechaRegistro = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

}
