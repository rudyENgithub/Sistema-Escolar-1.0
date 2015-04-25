/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sistema_escolar.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
@Table(name = "turma")
@NamedQueries({
    @NamedQuery(name = "Turma.getAll", 
        query = "select t from Turma t left join fetch t.alunos left join fetch"
                + " t.serie order by t.id asc")
//    @NamedQuery(name = "Turma.getAlunos",
//            query = "")
})

public class Turma implements Serializable
{
    private static final Long serialVersionUID = 1L;
    
    public static final String GET_ALL = "Turma.getAll";
    
    @Id
    @GeneratedValue
    @Column(name = "turma_ID")
    private Long id;
    
    @NotNull
    @Size(max = 30, message = "Deve ter menos que #{max} caracteres!")
    @Column(name = "turma_NOME")
    private String nome;
    
    @ManyToOne
    @JoinColumn(name = "serie_ID_turma")
    private Serie serie;

    @OneToMany(mappedBy = "turma", fetch = FetchType.EAGER)
    @ElementCollection
    @Fetch(FetchMode.JOIN)
    @OrderBy("nome ASC, sobrenome ASC")
    private List<Aluno> alunos;

    @Override
    public int hashCode()
    {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.nome);
        hash = 89 * hash + Objects.hashCode(this.serie);
        hash = 89 * hash + Objects.hashCode(this.alunos);
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
        final Turma other = (Turma) obj;
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
        if (!Objects.equals(this.alunos, other.alunos))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return this.nome;
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
     * @return the alunos
     */
    public List<Aluno> getAlunos()
    {
        return alunos;
    }

    /**
     * @param alunos the alunos to set
     */
    public void setAlunos(List<Aluno> alunos)
    {
        this.alunos = alunos;
    }
    
    
    
}
