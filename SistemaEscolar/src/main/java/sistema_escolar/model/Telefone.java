/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;


/**
 *
 * @author Vitor Freitas
 */
@Entity
@Table(name = "telefone")
public class Telefone implements Serializable
{
    @Id
    @GeneratedValue
    @Column(name = "telefone_ID")
    private Long id;
    
    @NotNull
    @Size(min = 2, max = 20, message = "O Nome deve ter entre 2 e 20 caracteres!")
    @Column(name = "telefone_NUMERO")
    private String telefone;
    
    @NotNull
    @Size(min = 2, max = 20, message = "O Nome deve ter entre 2 e 20 caracteres!")
    @Column(name = "telefone_DESC")
    private String desc;
    
    @ManyToOne
    @JoinColumn(name = "professor_ID_telefone")
    private Professor professor;
}
