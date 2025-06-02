package com.vegastore.jitarger.model;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "carrito")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CarritoModel {

    // Atributos de la tabla carrito

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del carrito")
    private long id;

    // Foreign key relacion 1 a 1
    @OneToOne
    @JoinColumn(name = "id_usuario")
    @Schema(description = "Id del usuario")
    private UsuarioModel idUsuario;

    @Column(name = "fecha_creacion", nullable = false)
    @Schema(description = "Fecha de creación del carrito")
    private LocalDateTime fechaCreacion;


    // Listas de relaciones
    @OneToMany(mappedBy = "idCarrito", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de items del carrito")
    private List<ItemCarritoModel> items;

    // Constructor personalizado para la creación de objetos de la tabla carrito

    public CarritoModel(
        UsuarioModel idUsuario, 
        LocalDateTime fechaCreacion
        ) {
        this.idUsuario = idUsuario;
        this.fechaCreacion = fechaCreacion;
    }
    
}
