/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Vitor Freitas
 */
@Entity
@Table(name = "materia")
@NamedQuery(name = "Materia.getAll",
        query = "select m from Materia m left join fetch m.serie inner join "
                + "fetch m.professor order by m.id asc")
public class Materia implements Serializable
{
    private static final Long serialVersionUID = 1L;
    
    public static final String GET_ALL = "Materia.getAll";
    
    @Id
    @GeneratedValue
    @Column(name = "materia_ID")
    private Long id;
    
    @NotNull
    @Size(max = 30, message = "Deve ter menos que #{max} caracteres!")
    @Column(name = "materia_NOME")
    private String nome;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_ID_materia")
    @Fetch(FetchMode.JOIN)
    private Serie serie;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_ID_materia")
    @Fetch(FetchMode.JOIN)
    private Professor professor;

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.nome);
        hash = 29 * hash + Objects.hashCode(this.serie);
        hash = 29 * hash + Objects.hashCode(this.professor);
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Materia other = (Materia) obj;
        if (!Objects.equals(this.id, other.id))
        {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome))
        {
            return false;
        }
        if (!Objects.equals(this.serie, other.serie))
        {
            return false;
        }
        if (!Objects.equals(this.professor, other.professor))
        {
            return false;
        }
        return true;
    }

    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * @return the nome
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /**
     * @return the serie
     */
    public Serie getSerie()
    {
        return serie;
    }

    /**
     * @param serie the serie to set
     */
    public void setSerie(Serie serie)
    {
        this.serie = serie;
    }

    /**
     * @return the professor
     */
    public Professor getProfessor()
    {
        return professor;
    }

    /**
     * @param professor the professor to set
     */
    public void setProfessor(Professor professor)
    {
        this.professor = professor;
    }
    
    
    
}
