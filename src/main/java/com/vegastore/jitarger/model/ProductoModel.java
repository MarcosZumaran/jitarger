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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "producto")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProductoModel {

    // Atributos de la tabla producto

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador del producto")
    private long id;

    @Column(name = "nombre", length = 50, nullable = false)
    @Schema(description = "Nombre del producto")
    private String nombre;

    @Column(name = "descripcion", length = 255, nullable = false)
    @Schema(description = "Descripción del producto")
    private String descripcion;

    // Foreign key
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria")
    @Schema(description = "Id de la categoria del producto")
    private CategoriaModel idCategoria;

    // Foreign key
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name =  "id_subcategoria")
    @Schema(description = "Id de la subcategoria del producto")
    private SubCategoriaModel idSubCategoria;

    @Column(name = "estado", length = 20, nullable = false)
    @Schema(description = "Estado del producto")
    private String estado;

    @Column(name = "tipo_prenda", length = 50, nullable = false)
    @Schema(description = "Tipo de prenda del producto", example = "Camiseta")
    private String tipoPrenda;

    @Column(name = "marca", length = 50, nullable = false)
    @Schema(description = "Marca del producto", example = "Nike")
    private String marca;

    @Column(name = "talla", length = 50, nullable = false)
    @Schema(description = "Talla del producto", example = "M")
    private String talla;

    @Column(name = "color", length = 50, nullable = false)
    @Schema(description = "Color del producto", example = "Rojo")
    private String color;

    @Column(name = "genero", length = 50, nullable = false)
    @Schema(description = "Genero del producto", example = "Masculino")
    private String genero;

    @Column(name = "material", length = 50, nullable = false)
    @Schema(description = "Material del producto", example = "Lana")
    private String material;

    @Column(name = "temporada", length = 50, nullable = true)
    @Schema(description = "Temporada del producto", example = "primavera")
    private String temporada;

    @Column(name = "fecha_registro")
    @Schema(description = "Fecha de creación del producto")
    private LocalDateTime fechaRegistro;

    // Listas de relaciones

    @OneToMany(mappedBy = "idProducto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de presentaciones del producto")
    private List<ProductoPresentacionModel> productoPresentacion;

    @OneToMany(mappedBy = "idProducto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de lotes del producto")
    private List<LoteModel> lotes;

    @OneToMany(mappedBy = "idProducto", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Schema(description = "Lista de imagenes del producto")
    private List<ProductoImagenModel> imagenes;

    // Constructor personalizado para la creación de objetos de la tabla producto

    public ProductoModel(
        String nombre, 
        String descripcion, 
        CategoriaModel idCategoria, 
        String estado, 
        String tipoPrenda, 
        String marca, 
        String talla,
        String color, 
        String genero, 
        String material, 
        String temporada, 
        LocalDateTime fechaRegistro
        ) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idCategoria = idCategoria;
        this.estado = estado;
        this.tipoPrenda = tipoPrenda;
        this.marca = marca;
        this.talla = talla;
        this.color = color;
        this.genero = genero;
        this.material = material;
        this.temporada = temporada;
        this.fechaRegistro = fechaRegistro;
    }
    
}
